package com.example.service;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AutheticationFilter extends AbstractGatewayFilterFactory<AutheticationFilter.Config> {

    private final RouterValidator routerValidator;
    private final JwtUtils jwtUtils;

    public AutheticationFilter(RouterValidator routerValidator, JwtUtils jwtUtils){
        super(Config.class);
        this.routerValidator = routerValidator;
        this.jwtUtils = jwtUtils;
    }

    //Este metodo se encargará de realizar el filtrado de las peticiones, Comprobara que los clientes tengan un accesToken que les de permiso para realizar la peticion.
    @Override
    public GatewayFilter apply(Config config) {
        //Exchange son las peticiones que llegan a la app
        return ((exchange, chain) -> {
            var request= exchange.getRequest();
            ServerHttpRequest serverHttpRequest= null;//Esta variable la utilizaremos para modificar la request del cliente para después mandarselas a las APIS de nuestros microservicios
            //Comprobamos si la peticion que se intenta realizar requiere de autenticacion
            if(routerValidator.isSecured.test(request)){
                //Comprobamos si la peticion tiene cabecera, ya que si no tiene no hay autorizacion
                if(autMissing(request)){
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                //Recogemos la cabecera
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                //Si la cabecera contiene un Bearer es que trae el token y lo podemos extraer
                if(authHeader!= null && authHeader.startsWith("Bearer ")){
                    authHeader= authHeader.substring(7);
                }else {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                //Comprobamos si el token ha expirado
                if(jwtUtils.isExpired(authHeader)){
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                serverHttpRequest = exchange.getRequest()
                        .mutate()
                        .header("userIdRequest", jwtUtils.extractUserId(authHeader).toString())
                        .build();


            }
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        });
    }

    //Este metodo modificará la respuesta que se le da al cliente cuando haya algún error
    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return null;
    }
    //Comprueba si la peticion viene con la cabecera de autorizacion
    private boolean autMissing(ServerHttpRequest request){
        return request.getHeaders().containsKey("Authorization");
    }

    public static class Config{}
}
