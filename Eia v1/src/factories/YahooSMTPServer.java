package factories;

import java.util.Properties;

public class YahooSMTPServer implements ISMTPServer{
	private Properties props;
	
	public YahooSMTPServer(){
		props = new Properties();
		
		this.props.put("mail.smtp.host", "smtp.mail.yahoo.com");
		this.props.put("mail.smtp.port", "587");
		this.props.put("mail.smtp.auth", "true");
		this.props.put("mail.smtp.starttls.enable", "true");
	}
	
	public Properties getProperties(){
		return this.props;
	}
	
	public void addAuthentificationProperties(String username, String password){
		this.props.put("mail.stmp.user", username);
		this.props.put("mail.smtp.password", password);
	}
}
