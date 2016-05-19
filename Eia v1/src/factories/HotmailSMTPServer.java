package factories;

import java.util.Properties;

public class HotmailSMTPServer implements ISMTPServer{
	Properties props;
	
	public HotmailSMTPServer(){
		props = new Properties();
		
		this.props.setProperty("mail.transport.protocol", "smtp");
	    this.props.setProperty("mail.host", "smtp.live.com");
	    this.props.put("mail.smtp.starttls.enable", "true");
	    this.props.put("mail.smtp.auth", "true");
	    this.props.put("mail.smtp.port", "587");
	}
	
	public Properties getProperties(){
		return this.props;
	}
}
