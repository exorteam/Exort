package exort.auth.component;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class JwtKeyUtil {

	private String secretKey;
	private Date createTime;

	public JwtKeyUtil(){
		this.recreateKey();
	}

	public String getKey(){
		// Maybe an interval for secret key to change
		// ...
		return secretKey;
	}

	public void refresh(){
		this.recreateKey();
	}

	private void recreateKey(){
		secretKey = UUID.randomUUID().toString().substring(0,4);
		createTime = new Date();
	}
}
