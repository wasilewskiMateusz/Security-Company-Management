package pl.lodz.p.it.thesis.scm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String PROTECTED_RESOURCE = "Exception.authentication.required";

    private final MessageSource messageSource;

    @Autowired
    public JwtAuthenticationEntryPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Exception exception = (Exception) httpServletRequest.getAttribute("exception");

        if (exception != null) {
            byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("message", exception.getLocalizedMessage()));
            httpServletResponse.getOutputStream().write(body);
        } else {
            String errorMessage = messageSource.getMessage(PROTECTED_RESOURCE, null, LocaleContextHolder.getLocale());
            byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("message", errorMessage));
            httpServletResponse.getOutputStream().write(body);
        }

    }
}
