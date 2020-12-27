package pl.lodz.p.it.thesis.scm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfiguration(MyUserDetailsService myUserDetailsService,
                                 JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                 JwtRequestFilter jwtRequestFilter) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.cors();
        httpSecurity
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/refresh").permitAll()

                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/users/{id}").access("hasRole('ADMIN') or @userSecurity.hasUserId(authentication,#id)")
                .antMatchers("/users/{id}/availability").hasRole("ADMIN")
                .antMatchers("/users/{id}/password").hasRole("ADMIN")
                .antMatchers("/users/{id}/own-password").access("@userSecurity.hasUserId(authentication,#id)")
                .antMatchers("/users/{id}/roles").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/workplaces").hasRole("EMPLOYER")
                .antMatchers("/users/{id}/workplaces").access("hasRole('ADMIN') or (@userSecurity.hasUserId(authentication,#id) and hasRole('EMPLOYER'))")
                .antMatchers(HttpMethod.PUT, "/workplaces/{id}").access("hasRole('ADMIN') or (@userSecurity.isUserWorkplaceOwner(authentication,#id) and hasRole('EMPLOYER'))")
                .antMatchers(HttpMethod.PUT, "/workplaces/{id}/disability").access("hasRole('ADMIN') or (@userSecurity.isUserWorkplaceOwner(authentication,#id) and hasRole('EMPLOYER'))")

                .antMatchers("rates").hasRole("EMPLOYEE")

                .antMatchers(HttpMethod.POST, "/jobs").hasRole("EMPLOYER")
                .antMatchers(HttpMethod.PUT, "/jobs/{id}").access("hasRole('ADMIN') or (@userSecurity.isJobInUserWorkplace(authentication,#id) and hasRole('EMPLOYER'))")
                .antMatchers(HttpMethod.GET, "/jobs/{id}/contracts").access("(@userSecurity.isJobInUserWorkplace(authentication,#id) and hasRole('EMPLOYER') or hasRole('ADMIN'))")
                .antMatchers(HttpMethod.PUT, "/jobs/{id}/disability").access("hasRole('ADMIN') or (@userSecurity.isJobInUserWorkplace(authentication,#id) and hasRole('EMPLOYER'))")
                .antMatchers("/users/{id}/contracts").access("hasRole('EMPLOYEE') and @userSecurity.hasUserId(authentication,#id)")


                .antMatchers(HttpMethod.POST, "/contracts").hasRole("EMPLOYEE")
                .antMatchers(HttpMethod.PUT, "/contracts/presence").hasRole("EMPLOYEE")
                    .antMatchers(HttpMethod.DELETE, "/contracts/{id}").hasAnyRole("EMPLOYER", "EMPLOYEE")

                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(java.util.List.of("*"));
        configuration.setAllowedMethods(java.util.List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(java.util.List.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }





}
