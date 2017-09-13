package bsn.project.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/chat", true)
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {    
        auth.userDetailsService(userDetailsService); //.passwordEncoder(passwordencoder());
    } 
     
    @Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/assets/**");
    }

//    @Bean(name="passwordEncoder")
//    public PasswordEncoder passwordencoder(){
//     return new BCryptPasswordEncoder();
//    }
    
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
}
