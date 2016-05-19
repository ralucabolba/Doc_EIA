package factories;

import java.util.Properties;

public class HotmailIMAPServer  implements IIMAPServer{
private Properties props;
	
	public HotmailIMAPServer(){
		this.props = new Properties();
		
		props.put("mail.imap.host",  "imap-mail.outlook.com");
		props.put("mail.store.protocol", "imaps");
		props.put("mail.imap.port", "993");
		props.put("mail.imap.starttls.enable", "true");
	}
	
	public Properties getProperties(){
		return this.props;
	}
}
