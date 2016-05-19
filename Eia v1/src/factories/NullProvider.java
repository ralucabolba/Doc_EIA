package factories;


public class NullProvider implements IProvider{
	public NullProvider(){
		throw new RuntimeException("Invalid provider type");
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public ISMTPServer getSMTPServer() {
		return null;
	}

	@Override
	public IIMAPServer getIMAPServer() {
		return null;
	}

}
