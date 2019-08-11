package exort.apiserver.component;

import java.util.Date;
import java.util.UUID;

import exort.apiserver.service.AuthService.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtResolver {

	private static final String jwtSecret = UUID.randomUUID().toString().substring(0,5);

	private JwtResolver(){}

	public static String generateToken(int id,String usr){
		return Jwts.builder()
			.setSubject(usr)
			.claim("id", id)
			.setIssuedAt(new Date())
			.signWith(SignatureAlgorithm.HS256, jwtSecret)
			.compact();
	}

	public static AuthResponse parseToken(String token){
		final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		AuthResponse response = new AuthResponse();
		response.setUsername(claims.getSubject());
		response.setId(claims.get("id",Integer.class));
		return response;
	}

}
