package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.User;


public class UserGateway {
	private static Connection connection = DBConnection.connect();
	
	public static boolean deleteUser(int idUser){
		PreparedStatement pst;

		try{
			pst = connection.prepareStatement("DELETE FROM EiaUser WHERE idUser = " + idUser);
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
			pst = connection.prepareStatement("SELECT MAX(idUser) AS maxid FROM EiaUser;");
			rs = pst.executeQuery();

			if(rs.next()){
				id = rs.getInt("maxid");
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}

	public static boolean insertUser(User user){
		String username = user.getUsername();
		String password = user.getPassword();
		String name = user.getName();

		if(existsUser(username)){
			return false;
		}
		
		PreparedStatement pst;
		int idUser = getMaxUserId() + 1;
		user.setIdUser(idUser);

		try{
			pst = connection.prepareStatement("INSERT INTO EiaUser VALUES (" + idUser + ", '" + name + "', '"+ username + "', '" + password + "');");
			pst.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return true;
	}

	
	public static User findUser(String username, String password){
		PreparedStatement pst;
		ResultSet rs;
		
		try{
			pst = connection.prepareStatement("SELECT * FROM EiaUser WHERE username = '" + username +"' AND passwrd = '" + password + "';");
			rs = pst.executeQuery();
			
			while(rs.next()){
				int idUser = rs.getInt("idUser");
				String name = rs.getString("nameUser");
				
				User user = new User(name, username, password);
				user.setIdUser(idUser);
				
				return user;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean existsUser(String username){
		PreparedStatement pst;
		ResultSet rs;
		
		try{
			pst = connection.prepareStatement("SELECT * FROM EiaUser WHERE username = '" + username + "';");
			rs = pst.executeQuery();
			
			if(rs.next()){
				return true;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void updateUser(User user){
		int idUser = user.getIdUser();
		String name = user.getName();
		String username = user.getUsername();
		String password = user.getPassword();


		PreparedStatement pst;

		try{
			pst = connection.prepareStatement("UPDATE EiaUser SET "
					+ "								nameUser = '" + name + "', "
					+ "								username = '" + username + "', "
					+ "								passwrd = '" + password + "' WHERE idUser = " + idUser); 
			pst.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
}
