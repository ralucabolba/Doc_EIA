package factories;

public interface IProvider {
	public String getType();
	
	public ISMTPServer getSMTPServer();
	public IIMAPServer getIMAPServer();
}
