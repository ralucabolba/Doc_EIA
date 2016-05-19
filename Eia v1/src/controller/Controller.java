package controller;

import java.io.*;
import java.util.*;

import javax.mail.*;

import dataaccess.EmailAccountGateway;
import dataaccess.UserGateway;
import entities.EmailAccount;
import entities.User;
import factories.IProvider;
import factories.ProviderFactory;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.Gui;

public class Controller {
	private Gui gui;
	private User user;
	private Message readMessage;
	private ArrayList<EmailAccount> accounts;
	
	public Controller(Gui gui){
		this.gui = gui;
		
		gui.addListenerLoginButton(new LoginListener());
		gui.addListenerSendMailButton(new SendMailListener());
		gui.addListenerComposeButton(new ComposeListener());
		gui.addListenerInboxButton(new InboxListener());
		gui.addListenerSentButton(new SentListener());
		gui.addListenerSpamButton(new SpamListener());
		gui.addListenerMenuAddAccount(new MenuAddAccountListener());
		gui.addListenerMyAccount(new MyAccountListener());
		gui.addListenerLogout(new LogoutListener());
		gui.addListenerSignup(new SignupListener());
		gui.addListenerAddUser(new AddUserListener());
		gui.addListenerAddAccount(new AddAccountListener());
		gui.addListenerReadMail(new ReadMailListener());
		gui.addListenerDeleteMail(new DeleteMailListener());
		gui.addListenerReplyMail(new ReplyListener());
		gui.addListenerRefresh(new RefreshListener());
		gui.addListenerUpdateMyAccount(new UpdateMyAccountListener());
		gui.addListenerDeleteEmailAccount(new DeleteEmailAccountListener());
	}
	
	public void fetchInboxEmails(){
		ArrayList<Message> list = new ArrayList<>();
		
		for(EmailAccount ea : accounts){
			ArrayList<Message> m = ea.getInbox();
			list.addAll(m);
		}

		
		list.sort(new Comparator<Message>(){
			@Override
			public int compare(Message m1, Message m2) {
				int op = 0;
				
				try {
					op = m2.getSentDate().compareTo(m1.getSentDate());
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				
				return op;
			}
			
		});
		gui.setInboxTable(list);
	}
	
	public void fetchSentEmails(){
		ArrayList<Message> list = new ArrayList<>();
		
		for(EmailAccount ea : accounts){
			ArrayList<Message> m = ea.getSent();
			list.addAll(m);
		}

		list.sort(new Comparator<Message>(){
		@Override
		public int compare(Message m1, Message m2) {
			int op = 0;
			
			try {
				op = m2.getSentDate().compareTo(m1.getSentDate());
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
			return op;
		}
		
	});
		gui.setInboxTable(list);
	}
	
	public void fetchSpamEmails(){
		ArrayList<Message> list = new ArrayList<>();
		
		for(EmailAccount ea : accounts){
			ArrayList<Message> m = ea.getSpam();
			list.addAll(m);
		}

		
		/*list.sort(new Comparator<Message>(){
			@Override
			public int compare(Message m1, Message m2) {
				int op = 0;
				
				try {
					op = m2.getSentDate().compareTo(m1.getSentDate());
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				
				return op;
			}
			
		});*/
		gui.setInboxTable(list);
	}
	
	public void readAllEmails(){
		Task<Void> task = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				gui.setWaitMessage("Reading emails...");
				for(EmailAccount ea : accounts){
					ea.readInbox();
					ea.readSentMails();
					ea.readSpamMails();
				}
				
				gui.setWaitMessage("");
				this.cancel();
				return null;
			}
			
		};
		
		Thread th = new Thread(task);
		th.start();
		
	}
	class LoginListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			String username = gui.getLoginUsername();
			String password = gui.getLoginPassword();
			
			user = UserGateway.findUser(username, password);
			
			if(user == null){
				gui.ErrorMessage("Error", "Failed loging in", "Username or password incorrect.");
			}
			else{
				accounts = EmailAccountGateway.getAccountForUser(user);
				addAccountsForUser();
				gui.userWindow();
				readAllEmails();
			}
			
		}
		
	}
	
	public void addAccountsForUser(){
		ArrayList<String> value = new ArrayList<>();
		
		for(EmailAccount ea : accounts){
			value.add(ea.getAddress());
		}
		
		gui.addAccounts(value);
	}
	
	public EmailAccount searchAccount(String address){
		for(EmailAccount ea : accounts){
			if(ea.getAddress().equals(address)){
				return ea;
			}
		}
		
		return null;
	}
	class SendMailListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			String from = gui.getAccount();
			
			EmailAccount ea = searchAccount(from);
			
			String to = gui.getTo();
			String subject = gui.getSubject();
			String content = gui.getMailContent();
			File file = gui.getAttachment();
			
			boolean result = ea.sendMail(to, subject, content, file);
			if(result){
				gui.SuccesMessage("Success", "Mail sent", "Your mail was sent succesfully");
			}else{
				gui.ErrorMessage("Error", "Failed sending mail", "");
			}
			gui.clearSendMailWindow();
			gui.userWindow();
		}
		
	}
	
	class ReplyListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			String from = gui.getReplyTo();
			String to = gui.getReplyFrom();
			String subject = gui.getReplySubject();
			
			gui.setFromField(from);
			gui.setToField(to);
			gui.setSubject(subject);
			
			gui.sendMailWindow();
			
			
//			EmailAccount ea = searchAccount(from);
//			System.out.println(ea.toString());
//			String content = gui.getReplyContent();
//			
//			if(MailOperations.replyMail(ea.getProvider().getSMTPServer(), from, ea.getPassword(), readMessage, content)){
//				gui.SuccesMessage("Success", "Mail sent", "Your mail was sent succesfully");
//			}
//			else{
//				gui.ErrorMessage("Error", "Failed sending mail", "");
//			}
			//readMessage = null;
		}
		
	}
	
	class ComposeListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			gui.sendMailWindow();
		}
		
	}
	
	class InboxListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			readMessage = null;
			gui.clearTables();
			fetchInboxEmails();
			String scene = gui.getScene();
			//if(scene.equals("mail")){
				gui.userWindow();
			//}
		}
		
	}
	
	class SentListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			readMessage = null;
			gui.clearTables();
			fetchSentEmails();
			String scene = gui.getScene();
			gui.userWindow();
		}
		
	}
	
	class SpamListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			readMessage = null;
			gui.clearTables();
			fetchSpamEmails();
			gui.userWindow();
		}
		
	}
	
	class MenuAddAccountListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			gui.addAccountWindow();
		}
		
	}
	
	class LogoutListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			user = null;
			accounts = null;
			gui.clearTables();
			gui.loginWindow();
		}
		
	}
	
	class SignupListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			gui.signupWindow();
		}
		
	}
	
	class AddUserListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			String name = gui.getSignupName();
			String username = gui.getSignupUsername();
			String password = gui.getSignupPassword();
			
			User u = new User(name, username, password);
			
			boolean result = UserGateway.insertUser(u);
			
			if(result){
				gui.SuccesMessage("Success", "Account created succesfully", "");
				user = u;
				gui.userWindow();
			}else{
				gui.ErrorMessage("Error", "Failed creating account", "The username already exists in database");
				gui.clearSignupWindow();
			}
		}
		
	}
	
	class AddAccountListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			String provider = gui.getProvider();
			String address = gui.getAddress();
			String password = gui.getPassword();
			
			
			if(searchAccount(address) != null){
				gui.ErrorMessage("Error", "Failed adding account", "The account was already added.");
			}
			else{
				IProvider p = ProviderFactory.getProvider(provider.toLowerCase());
				EmailAccount ea = new EmailAccount(address, password, p, user);
				
				accounts.add(ea);
				EmailAccountGateway.insertEmailAccount(ea);
				
				gui.SuccesMessage("Success", "Email account added succesfully", "");
				addAccountsForUser();
				gui.userWindow();
			}
		}
		
	}
	
	public String getMailContent(Part m){
		String content = "";
		
		try{
			if(m.isMimeType("text/plain")){
				content = (String)m.getContent();
			}
			else if(m.isMimeType("multipart/*")){ //mail has attachment
				System.out.println("multipart");
				Multipart mp = (Multipart) m.getContent();
		        int count = mp.getCount();
		        for (int i = 0; i < count; i++)
		           content = content + getMailContent(mp.getBodyPart(i));
			}
			else if (m.isMimeType("message/rfc822")) {
				content = getMailContent((Part) m.getContent());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return content;
	}
	
	class ReadMailListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			Message message = gui.getSelectedMail();
			
			if(message == null){
				gui.ErrorMessage("Error", "Failed reading mail", "You must select a mail in order to read it.");
			}
			else{
				
				try {
		            readMessage = message;
		            
					String sender = "";
					
					Address[] address = message.getFrom();
					int n = address.length;
					for(int i = 0;i<n;i++){
						sender = sender + address[i].toString();
					}
					String to = "";
					Address[] toA = message.getRecipients(Message.RecipientType.TO);
					int m = address.length;
					for(int i = 0;i<m;i++){
						to = to + toA[i].toString();
					}
					
					String subject = message.getSubject();
					String date = message.getSentDate().toString();
					String content = getMailContent(message);
					
					gui.setReadMail(sender, to, subject, date, content);
					gui.mailWindow();
					
				} 
				catch (MessagingException e) {
					e.printStackTrace();
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	class DeleteMailListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			Message message = gui.getSelectedMail();
			
			if(message == null){
				gui.ErrorMessage("Error", "Failed deleting mail", "You must select a mail in order to delete it.");
			}
			else{
				try {
					message.setFlag(Flags.Flag.DELETED, true);
					message.getFolder().close(true);
					gui.SuccesMessage("Success", "Email deleted succesfully", "");
					
				} catch (MessagingException e) {
					gui.ErrorMessage("Error", "Failed deleting mail", "");
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	class RefreshListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			gui.clearTables();
			gui.userWindow();
			readAllEmails();
		}
		
	}
	
	class MyAccountListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			gui.setMyName(user.getName());
			gui.setMyUsername(user.getUsername());
			gui.myAccountWindow();
		}
		
	}
	
	class UpdateMyAccountListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			String name = gui.getMyName();
			String username = gui.getMyUsername();
			String password= gui.getMyPassword();
			
			if(!user.getUsername().equals(username) && UserGateway.existsUser(username)){
				gui.ErrorMessage("Error", "Failed updating account", "Username already exists in database.");
			}
			else{
				user.setName(name);
				user.setUsername(username);
				user.setPassword(password);
				
				gui.SuccesMessage("Success", "Account updated succesfully", "");
				gui.userWindow();
			}
			
		}
		
	}
	
	class DeleteEmailAccountListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			String account = gui.getMySelectedAccount();
			boolean found = false;
			EmailAccount e = null;
			
			for(EmailAccount ea : accounts){
				if(!found && ea.getAddress().equals(account)){
					e = ea;
					found = true;
					break;
				}
			}
			
			if(!found || !EmailAccountGateway.deleteEmailAccount(e.getIdAccount())){
				gui.ErrorMessage("Error", "Failed deleting email account", "");
			}
			else{
				accounts.remove(e);
				addAccountsForUser();
				gui.SuccesMessage("Success", "Email account deleted succesfully", "");
				gui.userWindow();
			}
		}
		
	}
}
