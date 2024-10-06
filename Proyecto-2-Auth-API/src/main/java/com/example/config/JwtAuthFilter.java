package com.example.config;

import com.example.commons.entities.UserModel;
import com.example.repositories.UserRepository;
import com.example.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Comment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    //Este método es parte de un filtro de Spring Security que procesa cada solicitud HTTP antes de que llegue a los
    // controladores de la aplicación. El propósito del filtro es autenticar al usuario en función del JWT que se encuentra
    // en el encabezado Authorization.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader("Authorization"))//Buscamos el valor Authorization en el ecabezado HTTP de la solicitud request y la encapsulamos en un Optional
                //Filter es una metodo que pertenece a la clase optional que evalua si la condicion proporcionada se cumple, si no se cumple el Optinal pasara a un Optional.empty(). isBlank() disponible desde java 11 comprueba si la cadena esta vacia.
                .filter(header-> !header.isBlank())//En este caso si no se cumple la condicion se devolvera un Optional.empty(); Esto nos permite evitar errores por el manejo de nulos.
                //map() es un método de la clase `Optional` que se utiliza para aplicar una función a un valor si está presente. Si el `Optional` está vacío (`Optional.empty()`), el método no hace nada y devuelve un nuevo `Optional.empty()`. Si el `Opcional` contiene un valor (en este caso, el encabezado `Autorización`), se aplica la función proporcionada.
                .map(header-> header.substring(7))//Extraemos del encabezado HTTP el Token puro.
                .map(jwtService::extractUserId)//Extraemos el id del usuario
                //Similar a map, pero se utiliza cuando la función aplicada devuelve otro Optional.
                .flatMap(userId-> userRepository.findById(Long.valueOf(userId)))//Buscamos al usuario por su id
                .ifPresent(userDetails->{
                    request.setAttribute("X-User-Id", userDetails.getId());
                    processAuthentication(request, userDetails);
                });
        filterChain.doFilter(request, response);
    }

    private void processAuthentication(HttpServletRequest request, UserDetails userDetails) {
        String jwtToken = request.getHeader("Authorization").substring(7);
        Optional.of(jwtToken)
                .filter(token-> !jwtService.isExpired(token))
                .ifPresent(token -> {
                    UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });
    }
}
