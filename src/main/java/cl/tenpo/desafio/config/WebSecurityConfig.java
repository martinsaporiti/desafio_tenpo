package cl.tenpo.desafio.config;

import cl.tenpo.desafio.security.JwtAuthenticationEntryPoint;
import cl.tenpo.desafio.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Esta clase es responsable de la configuración de la seguridad de la aplicación.
 *
 * Created by martin.saporiti
 * on 24/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Setter
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }

    // Este Bean se define para poder inyectarlo en el Servicio de negocio que realiza
    // la autenticación.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Configuración de seguridad para Swagger.
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Configuración de Swagger:
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public");
    }

    // Configuración de seguridad para la API Rest de la aplicación.
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Configuración de seguridad de la API.
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/api/v1/user/signup", "/api/v1/user/login").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(
                        jwtTokenFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
