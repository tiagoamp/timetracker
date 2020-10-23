package br.com.tiagoamp.timetracker.security;

import br.com.tiagoamp.timetracker.error.AuthorizationException;
import br.com.tiagoamp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static br.com.tiagoamp.timetracker.model.Role.ROLE_ADMIN;

@Service
public class AuthorizationRules {

    private UserService userService;

    @Autowired
    public AuthorizationRules(UserService userService) {
        this.userService = userService;
    }

    public void authorizeOnlyAdmin() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var authorities = authentication.getAuthorities();
        if (!authorities.contains(ROLE_ADMIN)) throw new AuthorizationException();
    }

    public void authorizeOnlyIfRequesterUserIsTheAuthenticatedUser(Long userId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var authUser = (UserAuth) authentication.getPrincipal();
        var user = userService.findUserById(userId);
        if (!user.getEmail().equals(authUser.getEmail())) throw new AuthorizationException();
    }

    public void authorizeIfRequesterUserIsAdminOrTheAuthenticatedUser(Long userId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var authUser = (UserAuth) authentication.getPrincipal();
        var authorities = authentication.getAuthorities();
        if (authorities.contains(ROLE_ADMIN)) return;
        var user = userService.findUserById(userId);
        if (!user.getEmail().equals(authUser.getEmail())) throw new AuthorizationException();
    }

}
