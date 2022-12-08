package com.library.library.config;

import com.library.library.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final AccountDetailsService accountDetailsService;

  @Autowired
  public SecurityConfiguration(AccountDetailsService accountDetailsService) {
    this.accountDetailsService = accountDetailsService;
  }

  public DaoAuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(accountDetailsService);
    provider.setPasswordEncoder(new BCryptPasswordEncoder(4));
    return provider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()

      // Paths restricted to authenticated users
      .antMatchers("/account/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
      .antMatchers("/lists/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")

      // Paths restricted to admin users
      .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
      .antMatchers("/api/**").hasAuthority("ROLE_ADMIN")
      .antMatchers("/files/**").hasAuthority("ROLE_ADMIN")

    // Logging in
    .and()
      .formLogin()
      .loginPage("/login")
      .usernameParameter("email")
      .passwordParameter("password")
      .loginProcessingUrl("/login/authenticate")
      .defaultSuccessUrl("/")
      .failureUrl("/login?error=true")
      .permitAll()

    // Logging out
    .and()
      .logout()
      .invalidateHttpSession(true)
      .clearAuthentication(true)
      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
      .logoutSuccessUrl("/")
      .permitAll();
  }

  @Override
  public void configure(final WebSecurity web) throws Exception {
    web.ignoring().antMatchers(
      "/bibi/**", "/fonts/**", "/images/**", "/scripts/**", "/styles/**"
    );
  }

}
