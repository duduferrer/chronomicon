package bh.app.chronomicon.security;

import bh.app.chronomicon.dto.UserDTO;
import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.repository.SystemUserRepository;
import bh.app.chronomicon.service.OperatorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(OperatorService.class);

    @Value("${api.jwt.secret}")
    private String JWTsecret;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(JWTsecret.getBytes());
    }

    private final long expiration = 1000*60*60*2; //2h expiraçao

    public String generateToken(String saram){
        return Jwts.builder ()
                .setSubject (saram)
                .setIssuer ("APP-BH")
                .setIssuedAt (new Date ())
                .setExpiration (new Date (System.currentTimeMillis ()+expiration))
                .signWith (key, SignatureAlgorithm.HS256)
                .compact ();
    }

    private Claims getClaims(String token){
        return Jwts.parserBuilder ()
                .setSigningKey (key)
                .build ()
                .parseClaimsJws (token)
                .getBody ();
    }

    public String getUsername(String token){
        return getClaims (token).getSubject ();
    }

    public boolean isTokenValid(String token){
        try{
            getClaims (token);
            return true;
        }catch (JwtException e){
            log.warn ("Token JWT invalido. {}", e.getMessage ());
            return false;
        }
    }


}
