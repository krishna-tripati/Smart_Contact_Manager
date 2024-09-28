package com.scm.config;

import com.scm.Services.impl.SecurityCustomUserDetailService;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Configuration
public class SecurityConfig {

//    // only testing purpose
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//       UserDetails user1= User.withDefaultPasswordEncoder()
//               .username("admin").password("admin").roles("ADMIN").build();
//
//        UserDetails user2= User.withDefaultPasswordEncoder()
//                .username("user").password("user").roles("USER").build();
//
//        var inMemoryUserDetailsManager=new InMemoryUserDetailsManager(user1,user2);
//       return inMemoryUserDetailsManager;
//    }

    @Autowired
    private SecurityCustomUserDetailService userDetailService ;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private OAuthAuthenicationSuccessHandler handler; //OAuthHandler

    //configuration of authentication provider for spring security
    @Bean
     public DaoAuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        //DaoAuthenticationProvider configuration UserDetailsService and passwordEncoder

       //user detail service object
      daoAuthenticationProvider.setUserDetailsService(userDetailService);
      //password encoder object
      daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
       return daoAuthenticationProvider;
     }

     //
     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

         httpSecurity.authorizeHttpRequests(authorize -> {
             // public page  //authorize.requestMatchers("/home","/register","/about").permitAll();

             //configuration for which page is private or public
             authorize.requestMatchers("/user/**").authenticated();  // private only /user/**
             authorize.anyRequest().permitAll();  // other page are public
         });


       // httpSecurity.formLogin(Customizer.withDefaults()); //form default login

         //any changes to related login to reach here
         httpSecurity.formLogin(formLogin->{
          formLogin.loginPage("/login");
          formLogin.loginProcessingUrl("/authenticate"); // login page processing are apply in authenticated pages
          formLogin.defaultSuccessUrl("/user/dashboard"); // when login are successful then redirect in user-dashboard
      //    formLogin.failureUrl("/login?error=true"); //when login are failure
          formLogin.usernameParameter("email");
          formLogin.passwordParameter("password");


          //when user disabled
             formLogin.failureHandler(new AuthenticationFailureHandler()
             {
                 @Override
                 public void onAuthenticationFailure(HttpServletRequest request,
                   HttpServletResponse response, AuthenticationException exception)
                         throws IOException, ServletException
                 {
                     if(exception instanceof DisabledException){
                         //user Disabled messing sending
                         HttpSession session=request.getSession();
                         Message message= Message.builder().content("User Disabled").type(MessageType.red).build();
                         session.setAttribute("message",message);
                         response.sendRedirect("/login");
                     }  else {
                         // Handle other authentication failures (e.g., bad credentials)
                         // You can add additional logic here if needed
                         response.sendRedirect("/login?error=true");  // Redirect with an error parameter
                     }

                 }
             });

         });

         //logout form
        httpSecurity.csrf(AbstractHttpConfigurer::disable); //disable the csrf
         httpSecurity.logout(logoutform->{
             logoutform.logoutUrl("/do-logout");
             logoutform.logoutSuccessUrl("/login?logout=true");
         });

//         //Oauth2 configuration
//         httpSecurity.oauth2Login(Customizer.withDefaults()); //for default Oauth

         httpSecurity.oauth2Login(oauth-> {
             oauth.loginPage("/login");

           oauth.successHandler(handler); // handling Oauth
         });

         return httpSecurity.build();
     }
}
