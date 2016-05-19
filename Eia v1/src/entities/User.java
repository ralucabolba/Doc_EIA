package entities;

import java.util.TreeSet;

import dataaccess.UserGateway;

public class User {
	private int idUser;
	private String name;
	private String username;
	private String password;
	
	public User(String name, String username, String password){
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		UserGateway.updateUser(this);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		UserGateway.updateUser(this);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		UserGateway.updateUser(this);
	}
}
