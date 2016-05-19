package factories;

import java.util.Properties;

public class GmailSMTPServer implements ISMTPServer{
	Properties props;
	
	public GmailSMTPServer(){
		props = new Properties();
		
		this.props.put("mail.smtp.starttls.enable", "true");
        this.props.put("mail.smtp.auth", "true");
        this.props.put("mail.smtp.host", "smtp.gmail.com");
        this.props.put("mail.smtp.port", "587");
	}
	
	public Properties getProperties(){
		return this.props;
	}
}
