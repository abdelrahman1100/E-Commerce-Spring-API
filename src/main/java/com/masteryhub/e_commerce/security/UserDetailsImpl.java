package com.masteryhub.e_commerce.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.masteryhub.e_commerce.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

  private final Long id;
  private final String username;
  private final String email;
  private final String password;
  private final List<GrantedAuthority> authorities;
  private final Integer __v;

  public UserDetailsImpl(
      Long id,
      String username,
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities,
      Integer __v) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities == null ? Collections.emptyList() : List.copyOf(authorities);
    this.__v = __v;
  }

  public static UserDetailsImpl build(User user) {
    return new UserDetailsImpl(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        Collections.emptyList(),
        user.getTokenVersion());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.unmodifiableList(authorities);
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
}
