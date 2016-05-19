package factories;


public class YahooProvider implements IProvider {
	private IIMAPServer IMAPServer;
	private ISMTPServer SMTPServer;
	
	public YahooProvider(){
		this.IMAPServer = IMAPFactory.getIMAPServer("YAHOO");
		this.SMTPServer = SMTPFactory.getSMTPServer("YAHOO");
	}
	
	@Override
	public String getType() {
		return "YAHOO";
	}

	public IIMAPServer getIMAPServer() {
		return IMAPServer;
	}

	public ISMTPServer getSMTPServer() {
		return SMTPServer;
	}
	
}
