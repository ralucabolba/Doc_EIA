package factories;


public class HotmailProvider implements IProvider{
	private IIMAPServer IMAPServer;
	private ISMTPServer SMTPServer;
	
	public HotmailProvider(){
		this.IMAPServer = IMAPFactory.getIMAPServer("HOTMAIL");
		this.SMTPServer = SMTPFactory.getSMTPServer("HOTMAIL");
	}
	
	@Override
	public String getType() {
		return "HOTMAIL";
	}

	
	public IIMAPServer getIMAPServer() {
		return IMAPServer;
	}

	public ISMTPServer getSMTPServer() {
		return SMTPServer;
	}
}
