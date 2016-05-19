package factories;

public class SMTPFactory {
	public static ISMTPServer getSMTPServer(String type){
		if(type == null){
			return null;
		}
		if(type.equalsIgnoreCase("GMAIL")){
			return new GmailSMTPServer();
		}
		else if(type.equalsIgnoreCase("YAHOO")){
			return new YahooSMTPServer();
		}
		else if(type.equalsIgnoreCase("HOTMAIL")){
			return new HotmailSMTPServer();
		}
		else{
			return new NullSMTPServer();
		}
	}
}
