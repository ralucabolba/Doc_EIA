package factories;


public class ProviderFactory {
	public static IProvider getProvider(String type){
		if(type == null){
			return null;
		}
		if(type.equalsIgnoreCase("GMAIL")){
			return new GmailProvider();
		}
		else if(type.equalsIgnoreCase("YAHOO")){
			return new YahooProvider();
		}
		else if(type.equalsIgnoreCase("HOTMAIL")){
			return new HotmailProvider();
		}
		else{
			return new NullProvider();
		}
	}
}
