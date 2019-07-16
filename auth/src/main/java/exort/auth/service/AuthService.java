package exort.auth.service;

public interface AuthService {

	public String login(String usr,String pwd);
	public String register(String usr,String pwd);
}
