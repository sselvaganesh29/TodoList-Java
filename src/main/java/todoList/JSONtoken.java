package todoList;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


public class JSONtoken
{

    private static final String SEC_KEY = "neverEverGiveUp";

    private static final Key key = Keys.hmacShaKeyFor(SEC_KEY.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(String userName)
    {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }



    public static String getUsername(String token) throws Exception{

            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
    }




}
