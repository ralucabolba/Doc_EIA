package factories;

import java.util.Properties;

public class GmailIMAPServer implements IIMAPServer {
	private Properties props;
	
	public GmailIMAPServer(){
		this.props = new Properties();
		
		props.put("mail.imap.host",  "imap.gmail.com");
		props.put("mail.store.protocol", "imaps");
		props.put("mail.imap.port", "993");
		props.put("mail.imap.starttls.enable", "true");
	}
	
	public Properties getProperties(){
		return this.props;
	}
}
