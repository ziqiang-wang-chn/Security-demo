package cn.ohbug.authcenter.config.auth;

import static org.springframework.security.config.Customizer.withDefaults;

import cn.ohbug.authcenter.filter.GlobalAuthenticationExceptionChainFilter;
import cn.ohbug.authcenter.filter.JwtAuthenticationFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Resource
  private UserDetailsService userDetailsService;
  @Resource
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Resource
  private GlobalAuthenticationExceptionChainFilter exceptionChainFilter;

  @Resource
  private SecurityIgnoreRequestPaths ignoreRequestPaths;
  @Bean
  SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers(ignoreRequestPaths.getUrls()).permitAll()
              .anyRequest().authenticated();
        }).httpBasic(withDefaults());
    http.userDetailsService(userDetailsService);
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(exceptionChainFilter, LogoutFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class).build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
