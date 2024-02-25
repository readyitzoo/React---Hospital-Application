package com.project.MedicalRegister.Config;

import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Repository.UserRepository;
import com.project.MedicalRegister.Util.Encryption;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    UserAuthenticationProvider userAuthenticationProvider;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    String cnp = userAuthenticationProvider.validateToken(authElements[1]);
                    var user = userRepository.findByCnp(cnp).orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                } catch (Exception e) {
                    throw new AppException("Invalid token", HttpStatus.BAD_REQUEST);
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}