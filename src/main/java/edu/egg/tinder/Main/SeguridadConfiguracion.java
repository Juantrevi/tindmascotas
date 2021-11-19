package edu.egg.tinder.Main;

import edu.egg.tinder.Main.Errores.ErrorServicio;
import edu.egg.tinder.Main.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter{

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws ErrorServicio {
        try {
            auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/register", "/index", "/loginn")//para permitir que todos los usuarios ingresen a los recursos estaticos como fotos etc etc ponemos "/css/", "/js/" etc etc dependiendo de la carpeta que se encuentren
                .permitAll()
                .and().formLogin().loginPage("/login")
                .loginProcessingUrl("/loginchecj")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/").permitAll()
        ;
    }
}

