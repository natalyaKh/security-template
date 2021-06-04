package security.template.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import security.template.constants.SecurityConstants;
import security.template.models.User;
import security.template.repo.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    final UserRepository userRepository;
    public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

//    Каждый раз, когда происходит запрос к любой конечной точке –
//    вызывается этот фильтр. В нем есть доступ
//    к headers, то есть мы можем достать токен и проверить его.


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    //    Этот метод проверяем верный ли токен
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
            String user = Jwts.parser()
                .setSigningKey(SecurityConstants.getTokenSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
            if (user != null) {
                User userEntity = userRepository.findByUserEmail(user);
                UserPrincipal userPrincipal = new UserPrincipal(userEntity);
                return new UsernamePasswordAuthenticationToken(userPrincipal,
                    null, userPrincipal.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
