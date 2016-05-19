package entities;

import java.io.File;
import java.util.ArrayList;

import javax.mail.Folder;
import javax.mail.Message;

import factories.IProvider;
import services.MailOperations;

public class EmailAccount implements Comparable<EmailAccount>{
	private int idAccount;
	private String address;
	private String password;
	private IProvider provider;
	private User user;
	
	private ArrayList<Message> inbox;
	private ArrayList<Message> sent;
	private ArrayList<Message> spam;
	
	
	public EmailAccount(String address, String password, IProvider provider, User user){
		this.address = address;
		this.password = password;
		this.provider = provider;
		this.user = user;
		
		this.inbox = new ArrayList<>();
		this.sent = new ArrayList<>();
		this.spam = new ArrayList<>();
	}

	public int getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IProvider getProvider() {
		return provider;
	}

	public void setProvider(IProvider provider) {
		this.provider = provider;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProviderType(){
		return this.provider.getType();
	}
	
	public ArrayList<Message> getInbox(){
		return this.inbox;
	}
	
	public ArrayList<Message> getSent(){
		return this.sent;
	}

	public ArrayList<Message> getSpam(){
		return this.spam;
	}

	public boolean sendMail(String to, String subject, String content, File file){
		return MailOperations.sendMail(this.provider.getSMTPServer(), this.address, this.password, to, subject, content, file);
	}
	
	public Folder getInboxMails(){
		return MailOperations.getInboxMails(this.provider.getIMAPServer(), this.address, this.password);
	}
	
	public Folder getSentMails(){
		return MailOperations.getSentMails(this.provider.getType(), this.provider.getIMAPServer(), this.address, this.password);
	}
	
	public Folder getSpamMails(){
		return MailOperations.getSpamMails(this.provider.getType(), this.provider.getIMAPServer(), this.address, this.password);
	}
	
	
	
	public void readInbox(){
		Folder folder = getInboxMails();
		Message[] m = MailOperations.readMails(folder);
		
		inbox.clear();
		for(int i = 0; i<m.length; i++){
			inbox.add(m[i]);
		}
	}
	
	public void readSentMails(){
		Folder folder = getSentMails();
		Message[] m = MailOperations.readMails(folder);
		
		sent.clear();
		for(int i = 0; i<m.length; i++){
			sent.add(m[i]);
		}
	}
	
	public void readSpamMails(){
		Folder folder = getSpamMails();
		Message[] m = MailOperations.readMails(folder);
		
		spam.clear();
		for(int i = 0; i<m.length; i++){
			spam.add(m[i]);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailAccount other = (EmailAccount) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "EmailAccount [address=" + address + "]";
	}

	@Override
	public int compareTo(EmailAccount ea) {
		return this.address.compareTo(ea.getAddress());
	}
	
	
}
