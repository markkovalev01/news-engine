package app.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.model.entities.UserEntity;
import app.model.mapper.UserMapper;

@Component
public class RESTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    SecurityService securityService;
    @Autowired
    UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
        throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("application/json;charset=UTF8");

        ObjectMapper objectMapper = new ObjectMapper();

        UserEntity user = securityService.findUserByLogin(authentication.getName());
        logger.info(objectMapper.writeValueAsString(userMapper.toDto(user)));
        httpServletResponse.getWriter().print(objectMapper.writeValueAsString(userMapper.toDto(user)));
        httpServletResponse.getWriter().flush();
        logger.info("accessed");
        clearAuthenticationAttributes(httpServletRequest);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        }
    }
}
