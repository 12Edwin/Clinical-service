package utez.edu.mx.backend.security.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import utez.edu.mx.backend.security.jwt.JwtEntryPoint;
import utez.edu.mx.backend.security.jwt.JwtTokenFilter;
import utez.edu.mx.backend.security.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(MainSecurity.class);
    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JwtEntryPoint entryPoint;

    private static final String[] AUTH_WHITELIST = {
            "/api/twilio/**"
    };

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeHttpRequests(req ->{
            req.antMatchers("/api/auth/**").permitAll();
            req.antMatchers("/api/ratting/**").permitAll();
            req.antMatchers("/api/captcha/**").permitAll();
            req.antMatchers("/api/sms/**").permitAll();
            req.antMatchers("/api/user/profile/**").authenticated();
            req.antMatchers("/api/user/upload/**").authenticated();
            req.antMatchers("/api/user/**").permitAll();
            req.antMatchers("/api/pathology/**").authenticated();
            req.antMatchers("/api/service/**").permitAll();
            req.antMatchers("/api/space/**").permitAll();
            req.antMatchers("/api/speciality/**").permitAll();
            req.antMatchers("/api/appointment/**").authenticated();
            req.antMatchers("/api/doctor/**").permitAll();
            req.antMatchers("/api/expedient/**").authenticated();
            req.antMatchers("/api/ratting/**").permitAll();
            req.antMatchers("/api/treatment/**").authenticated();
            req.antMatchers(AUTH_WHITELIST).permitAll();
            req.anyRequest().permitAll();
        });
        http.logout(logout ->{
            logout.clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll();
        }).exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);;

        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
