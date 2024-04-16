package utez.edu.mx.backend.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import utez.edu.mx.backend.security.entity.MyUserDetails;
import utez.edu.mx.backend.security.service.CryptService;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;
    private final CryptService crypt;

    public JwtProvider(CryptService crypt) {
        this.crypt = crypt;
    }

    public String generateToken(Authentication authentication, Map<String, Object> claims) throws UnsupportedEncodingException {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return  crypt.encrypt( Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000L))
                .signWith(SignatureAlgorithm.HS512, secret).compact() );
    }

    public String getUsernameFromToken(String crypt_token) throws UnsupportedEncodingException {
        String token = crypt.decrypt(crypt_token);
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Long getUserId(String crypt_token) throws UnsupportedEncodingException {
        String token = crypt.decrypt(crypt_token);
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("user_id", Long.class);
    }

    public boolean validateToken(String crypt_token) {
        try {
            String token = crypt.decrypt(crypt_token);
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | UnsupportedEncodingException e) {
            logger.error("Token mal formado");
        } catch (UnsupportedJwtException e) {
            logger.error("Token no soportado");
        } catch (ExpiredJwtException e) {
            logger.error("Token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("Token vacío");
        } catch (SignatureException e) {
            logger.error("Firma erronea");
        }
        return false;
    }
}
