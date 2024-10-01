package com.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtils {
    private final String secretKey="dlkwfvblksfvblsdkfvblskdvlkdsbfvklbsdfvbsdfvlbkdsfkjsdfhkjfvkjasbvkjbasdvkjasdbkppoirutiuewruifgjvacsdcbvacsmhvasdc";

    //Este metodo nos devuelve un objeto que contendra toda la información del token
    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Comprueba si la fecha de expiracion del token es anterior al momento en que se está haciendo la solicitud
    public boolean isExpired(String token){
        try{
            return getClaims(token).getExpiration().before(new Date());
        }catch (Exception e){
            return true;
        }
    }

    public Integer extractUserId(String token){
        try{
            return Integer.parseInt(getClaims(token).getSubject());
        }catch (Exception e){
            return null;
        }
    }
}
