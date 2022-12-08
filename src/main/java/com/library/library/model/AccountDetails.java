package com.library.library.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class AccountDetails implements UserDetails {

  private final Account account;
  private final List<AuthGroup> authGroups;

  public AccountDetails(Account account, List<AuthGroup> authGroups) {
    this.account = account;
    this.authGroups = authGroups;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if(authGroups == null) {
      return Collections.emptySet();
    }

    Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
    authGroups.forEach(authGroup -> grantedAuthorities.add(new SimpleGrantedAuthority(authGroup.getName())));
    return grantedAuthorities;
  }

  public Integer getId() {
    return account.getId();
  }

  @Override
  public String getPassword() {
    return account.getPassword();
  }

  @Override
  public String getUsername() {
    return account.getEmail();
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
