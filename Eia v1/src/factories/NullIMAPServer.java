package factories;

import java.util.Properties;

public class NullIMAPServer implements IIMAPServer {
	public NullIMAPServer(){
		throw new RuntimeException("Invalid POP server type");
	}

	@Override
	public Properties getProperties() {
		return null;
	}
}
