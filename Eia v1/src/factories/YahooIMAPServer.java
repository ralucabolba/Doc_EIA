package factories;

import java.util.Properties;

public class YahooIMAPServer implements IIMAPServer {
	private Properties props;
	
	public YahooIMAPServer(){
		this.props = new Properties();
		
//		this.props.put("mail.pop3.host", "pop.mail.yahoo.com");
//		this.props.put("mail.pop3.port", "995");
//		this.props.put("mail.pop3.starttls.enable", "true");
		
		props.put("mail.store.protocol", "imaps");
		props.put("mail.imap.host", "imap.mail.yahoo.com");
		props.put("mail.imap.port", "993");
		props.put("mail.imap.starttls.enable", "true");
	}
	
	public Properties getProperties(){
		return this.props;
	}
}
