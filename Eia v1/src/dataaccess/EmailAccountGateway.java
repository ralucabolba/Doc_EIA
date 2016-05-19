package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import entities.EmailAccount;
import entities.User;
import factories.IProvider;
import factories.ProviderFactory;

public class EmailAccountGateway {
private static Connection connection = DBConnection.connect();
	
	public static boolean deleteEmailAccount(int idAccount){
		PreparedStatement pst;

		try{
			pst = connection.prepareStatement("DELETE FROM EmailAccount WHERE idAccount = " + idAccount);
			pst.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private static int getMaxUserId(){
		PreparedStatement pst;
		ResultSet rs;

		int id = -1;
		try{
			pst = connection.prepareStatement("SELECT MAX(idAccount) AS maxid FROM EmailAccount;");
			rs = pst.executeQuery();

			if(rs.next()){
				id = rs.getInt("maxid");
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public static boolean insertEmailAccount(EmailAccount account){
		String address = account.getAddress();
		String password = account.getPassword();
		String provider = account.getProviderType();
		int idUser = account.getUser().getIdUser();

		
		PreparedStatement pst;
		int idAccount = getMaxUserId() + 1;
		account.setIdAccount(idAccount);

		try{
			pst = connection.prepareStatement("INSERT INTO EmailAccount VALUES (" + idAccount + ", '" + address + "', '" + password + "', '" + provider + "', " + idUser + ");");
			pst.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static ArrayList<EmailAccount> getAccountForUser(User user){
		int idUser = user.getIdUser();
		ArrayList<EmailAccount> accounts = new ArrayList<>(); 
		
		PreparedStatement pst;
		ResultSet rs;
		
		
		try{
			pst = connection.prepareStatement("SELECT * FROM EmailAccount WHERE idUser = " + idUser);
			rs = pst.executeQuery();
			
			while(rs.next()){
				int idAccount = rs.getInt("idAccount");
				String address = rs.getString("address");
				String password = rs.getString("passwrd");
				String provider = rs.getString("provider");
				
				IProvider p = ProviderFactory.getProvider(provider);
				EmailAccount account = new EmailAccount(address, password, p, user);
				account.setIdAccount(idAccount);
				accounts.add(account);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return accounts;
	}
}
