package com.example.service;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {
    //Lista de los endpoints de la aplicacion que estaran abiertos a todo el mundo
    public static final List<String> openEndpoints = List.of(
            "/auth"
    );

    //Este metodo sirve para comprueba si la peticion requiere de autenticacion
    public Predicate<ServerHttpRequest> isSecured= serverHttpRequest ->
            openEndpoints.stream().noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));

}
