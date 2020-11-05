package pl.lodz.p.it.thesis.scm.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationTimeInS}")
    private int expirationTimeInS;

    @Value("${jwt.refreshExpirationDateInS}")
    private int refreshExpirationDateInS;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        List<String> roleList = claims.get("roles", List.class);
        if (roleList != null) {
            roleList.forEach(role -> roles.add(new SimpleGrantedAuthority(role)));
        }
        return roles;
    }

    public boolean isAccessToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("isAccessToken", Boolean.class);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        List<String> roleList = new ArrayList<>();
        roles.forEach(r -> roleList.add(r.toString()));
        claims.put("roles", roleList);
        claims.put("isAccessToken", true);
        return doGenerateAccessToken(claims, userDetails.getUsername());
    }
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("isAccessToken", false);
        return doGenerateRefreshToken(claims, userDetails.getUsername());
    }

    private String doGenerateAccessToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInS * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInS * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();

    }
}
