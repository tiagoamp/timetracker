package br.com.tiagoamp.timetracker.security;

import br.com.tiagoamp.timetracker.model.Role;
import br.com.tiagoamp.timetracker.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class UserAuth implements UserDetails {

    private String email;

    private String password;

    private Role role;


    public UserAuth() { }

    public UserAuth(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public static UserAuth fromModel(User user) {
        return new UserAuth(user.getEmail(), user.getPassword(), user.getRole());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public Role getRole() {
        return role;
    }

}
