package pe.com.avivel.sistemas.guiaselectronicas.restapi.auth;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pe.com.avivel.sistemas.guiaselectronicas.entities.session.CustomUsernamePasswordAuthenticationToken;
import pe.com.avivel.sistemas.guiaselectronicas.entities.session.DatosSesion;
import pe.com.avivel.sistemas.guiaselectronicas.service.spec.security.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.CorsHeadersConstants.*;
import static pe.com.avivel.sistemas.guiaselectronicas.restapi.auth.UrlConfig.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "AUTHORIZATION";
    public static final String NO_AUTENTICADO = "PETICION NO AUTENTICADA";
    private static final String SEPARATOR = ", ";

    private final JwtService jwtService;

    private final CorsProperties corsProperties;

    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, StringUtils.join(corsProperties.getAllowedMethods(), SEPARATOR));
        response.setHeader(ACCESS_CONTROL_MAX_AGE, String.valueOf(corsProperties.getMaxAge()) );
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, StringUtils.join(corsProperties.getAllowedHeaders(), SEPARATOR));

        String authHeader = request.getHeader(AUTHORIZATION);

        if (!jwtService.isBearer(authHeader)) {
            AuthenticationExceptionHandler.commenceUnauthorizedResponse(response, NO_AUTENTICADO);
            return;
        }

        DatosSesion datosSesion;

        try {
            datosSesion = jwtService.validarSession(authHeader);
        } catch (JwtException ex){
            AuthenticationExceptionHandler.commenceUnauthorizedResponse(response, ex.getMessage());
            return;
        }

        List<GrantedAuthority> authorities = jwtService.getAuthorities(authHeader);
        CustomUsernamePasswordAuthenticationToken authenticationToken =
                new CustomUsernamePasswordAuthenticationToken(datosSesion, authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        var  requestURL = request.getServletPath();
        return URL_PREFIX_WITHOUT_SECURITY.stream().anyMatch(requestURL::startsWith) ||
               URL_SWAGGER_WITHOUT_SECURITY.stream().anyMatch(u -> Pattern.matches(u, requestURL));
    }
}
