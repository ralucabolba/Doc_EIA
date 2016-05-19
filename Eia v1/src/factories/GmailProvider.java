package factories;


public class GmailProvider implements IProvider{
	private IIMAPServer IMAPServer;
	private ISMTPServer SMTPServer;

	public GmailProvider(){	
		this.IMAPServer = IMAPFactory.getIMAPServer("GMAIL");
		this.SMTPServer = SMTPFactory.getSMTPServer("GMAIL");
	}

	@Override
	public String getType() {
		return "GMAIL";
	}

	public IIMAPServer getIMAPServer() {
		return IMAPServer;
	}

	public ISMTPServer getSMTPServer() {
		return SMTPServer;
	}
	
}
