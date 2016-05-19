package factories;

public class IMAPFactory {
	public static IIMAPServer getIMAPServer(String type){
		if(type == null){
			return null;
		}
		if(type.equalsIgnoreCase("GMAIL")){
			return new GmailIMAPServer();
		}
		else if(type.equalsIgnoreCase("YAHOO")){
			return new YahooIMAPServer();
		}
		else if(type.equalsIgnoreCase("HOTMAIL")){
			return new HotmailIMAPServer();
		}
		else{
			return new NullIMAPServer();
		}
	}
}
