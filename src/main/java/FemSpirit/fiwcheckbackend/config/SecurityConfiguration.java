package FemSpirit.fiwcheckbackend.config;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import FemSpirit.fiwcheckbackend.auth.filter.JwtAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

      private final JwtAuthenticationFilter jwtAuthenticationFilter;

      public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
          this.jwtAuthenticationFilter = jwtAuthenticationFilter;
      }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JwtAuthenticationFilter> registration =
                new FilterRegistrationBean<>(jwtAuthenticationFilter);

        registration.setEnabled(false);

        return registration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/user/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/user/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/user/**").authenticated()
                        .requestMatchers("/user/**", "/api/auth/**", "/role/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/studentprofil").permitAll()

                        .requestMatchers(HttpMethod.PUT, "/studentprofil/studieninformationen").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())
                .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) ->
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)))
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}