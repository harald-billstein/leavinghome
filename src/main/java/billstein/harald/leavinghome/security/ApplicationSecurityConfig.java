package billstein.harald.leavinghome.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${password}")
  private String password;
  @Value("${username}")
  private String user;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.headers().frameOptions().sameOrigin();

    http.authorizeRequests()
        .anyRequest().authenticated()
        .and().httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    System.out.println("user: " + user + " password: " + password);
    String encodedPassword = new BCryptPasswordEncoder().encode(password);
    auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
        .withUser(user).password(encodedPassword).roles("role");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

