package com.example.services;

import com.example.commons.dtos.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService{

    private final String secretToken;

    //Estamos cogiendo el secret token del archivo yaml
    public JwtServiceImpl(@Value("${jwt.secret}") String secretToken) {
        this.secretToken = secretToken;
    }

    //Este metodo genera un Token
    @Override
    public TokenResponse generateToken(Long userId) {
        Date expiration= new Date(Long.MAX_VALUE);//Fijamos una fecha de expiracion
        String token = Jwts.builder()//Cremaos un objeto Jwts
                .setSubject(String.valueOf(userId))//Modificamos el id del usuario extraido de la peticion
                .setIssuedAt(new Date(System.currentTimeMillis()))//Fijamos la fecha de creacion
                .setExpiration(expiration)//Seteamos la expiracion
                .signWith(SignatureAlgorithm.HS512, this.secretToken)//Firmamos con el secretToken encriptado en este caso con el algoritmo HS512
                .compact();//Lo compactamos en una cadena

        //A partir de este objeto Jwts retornamos un TokenResponse cuyo accestoken es la cadena creada anteriormente
        return TokenResponse.builder()
                .accessToken(token)
                .build();
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.secretToken).build()
                .parseClaimsJws(token)
                .getBody();
        //Que hace este metodo. ¿Modifica el token anterior?, ¿Que objeto devuleve?
    }

    @Override
    public boolean isExpired(String token) {
        try{
            return getClaims(token).getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Integer extractUserId(String token) {
        try{
            return Integer.parseInt(getClaims(token).getSubject());
        }catch (Exception e){
            return null;
        }
    }
}
