package app.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.entities.UserEntity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SecurityService securityService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        logger.info(String.format("auth by %s", login));
        if (login != null) {
            UserEntity user = securityService.findUserByLogin(login);
            if (user != null) {
                logger.info(user.getLogin() + " " + user.getPassword());
                return new org.springframework.security.core.userdetails.User(
                    user.getLogin(),
                    user.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER")
                );
            }
        }
        throw new UsernameNotFoundException(String.format("could not found user %s", login));
    }
}
