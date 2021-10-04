package security

import com.cusp.app.security.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig() : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var tokenService: TokenService

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/",
            "/swagger-ui/**",
            "/webjars/**",
            "/api/v1/auth/**",
            "/api/v1/workers/signup",
            "/api/v1/offices/signup",
            "/api/v1/public/**",
            "/messages",
            "/index.html"
        )
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(JwtFilter(tokenService), UsernamePasswordAuthenticationFilter::class.java)


    }

}