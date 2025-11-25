package thuha.com.jwt.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration.ms}")
    private int expiration;

    public String generateToken(Authentication auth) {
        CustomUserDetails userPrincipal = (CustomUserDetails) auth.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(String.valueOf(userPrincipal.getUserId()))
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+expiration))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Extract name từ token
    public String getUserNameFromToken(String token) {
        return getClaimsFromToken(token).get("name", String.class);
    }

    // Extract userId từ sub
    public Integer getUserIdFromToken(String token) {
        return Integer.valueOf(getClaimsFromToken(token).getSubject());
    }

    // Extract roles từ token
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        return (List<String>) getClaimsFromToken(token).get("roles");
    }

    // Helper: Get claims sau verify
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token){
        try{
            getClaimsFromToken(token);
            return true;
        }
        catch(ExpiredJwtException | SignatureException | MalformedJwtException | IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
