package factories;

import java.util.Properties;

public class NullSMTPServer implements ISMTPServer{
	public NullSMTPServer(){
		throw new RuntimeException("Invalid SMTP server type");
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}
}
