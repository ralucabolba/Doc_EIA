package services;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.mail.*;
import javax.mail.internet.*;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPFolder.*;

import factories.*;

public class MailOperations {
	public static Message[] readMails(Folder folder) {
		Message[] messages = null;
		
		try{
			folder.open(Folder.READ_WRITE);
			
			messages = folder.getMessages();
			
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			fp.add(FetchProfileItem.FLAGS);
			fp.add(FetchProfileItem.CONTENT_INFO);
			
			fp.add("X-mailer");
			folder.fetch(messages, fp);

	      } catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      
	      return messages;
	      
	}
	
	
	public static Session authentificateOnSMTP(ISMTPServer SMTPServer, String username, String password) {
		Session session = null;
		
		session = Session.getInstance(SMTPServer.getProperties(),
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });
	
		return session;
	}
	
	public static Session authentificateOnIMAP(IIMAPServer IMAPServer, String username, String password) {
		Session session = null;
		
		session = Session.getInstance(IMAPServer.getProperties(),
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });
		
		return session;
	}
	
//	public static boolean replyMail(ISMTPServer SMTPServer, String from, String password, Message message, String content) {
//		try {
//			Session session = authentificateOnSMTP(SMTPServer, from, password);
//			
//			Address[] to = message.getFrom();
//			
//			Message replyMessage = new MimeMessage(session);
//            replyMessage = (MimeMessage) message.reply(true);
//            replyMessage.setFrom(new InternetAddress(from));
//            replyMessage.setReplyTo(to);
//	        replyMessage.setText(content);
//	        
//	        Transport.send(replyMessage);
//		} 
//		catch (MessagingException e) {
//			e.printStackTrace();
//			return false;
//		}
//		
//		return true;
//	}
	
	
	public static boolean sendMail(ISMTPServer SMTPServer, String from, String password, String to, String subject, String content, File file) {
		try{ 
			Session session = authentificateOnSMTP(SMTPServer, from, password);
			
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setSubject(subject);
			message.setSentDate(new Date());
			
	 
	        // adds attachments
	        if (file != null ) {
	        	MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(content, "text/html");
				 
		        // creates multi-part
		        Multipart multipart = new MimeMultipart();
		        multipart.addBodyPart(messageBodyPart);
		        
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
                message.setContent(multipart);
	        }
	 
	        // sets the multi-part as e-mail's content
	        
	       
			message.setText(content);
			
			Transport.send(message);
		}
		catch(MessagingException mex){
			//mex.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Folder getInboxMails(IIMAPServer IMAPServer, String username, String password){
		Folder folder = null;
		
		try{
			Session session = authentificateOnIMAP(IMAPServer, username, password);
			
			Store store = session.getStore();
			String host = IMAPServer.getProperties().getProperty("mail.imap.host");
			
			store.connect(host, username, password);
	
			folder = store.getFolder("INBOX");
			
			store.close();
		} catch (NoSuchProviderException e) {
	        e.printStackTrace();
	     } catch (MessagingException e) {
	        e.printStackTrace();
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
		return folder;
	}
	
	public static Folder getSentMails(String type, IIMAPServer IMAPServer, String username, String password){
		IMAPFolder folder = null;
		
		String nameFolder = "";
		
		if(type.equals("GMAIL")){
			nameFolder = "[Gmail]/Mesaje trimise";
		}
		else if(type.equals("YAHOO")){
			nameFolder = "Sent";
		}
		else if(type.equals("HOTMAIL")){
			nameFolder = "Sent";
		}
		
		try{
			Session session = authentificateOnIMAP(IMAPServer, username, password);
			Store store = session.getStore();
			String host = IMAPServer.getProperties().getProperty("mail.imap.host");
			
			store.connect(host, username, password);
	
			folder = (IMAPFolder) store.getFolder(nameFolder);
			
			store.close();
		} catch (NoSuchProviderException e) {
	        e.printStackTrace();
	     } catch (MessagingException e) {
	        e.printStackTrace();
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
		return folder;
	}
	
	public static Folder getSpamMails(String type, IIMAPServer IMAPServer, String username, String password){
		IMAPFolder folder = null;
		
		String nameFolder = "";
		
		if(type.equals("GMAIL")){
			nameFolder = "[Gmail]/Spam";
		}
		else if(type.equals("YAHOO")){
			nameFolder = "Bulk Mail";
		}
		else if(type.equals("HOTMAIL")){
			nameFolder = "Junk";
		}
		
		try{
			Session session = authentificateOnIMAP(IMAPServer, username, password);
			Store store = session.getStore();
			String host = IMAPServer.getProperties().getProperty("mail.imap.host");
			
			store.connect(host, username, password);
	
			folder = (IMAPFolder) store.getFolder(nameFolder);
			
			store.close();
		} catch (NoSuchProviderException e) {
	        e.printStackTrace();
	     } catch (MessagingException e) {
	        e.printStackTrace();
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
		return folder;
	}
}
