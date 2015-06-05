package kps.ui.formlistener;

import kps.enums.Position;

public class CreateUserEvent {

	private String username;
	private int passwordHash;
	private Position position;

	public CreateUserEvent(String username, int passwordHash, Position position){
		this.username = username;
		this.passwordHash = passwordHash;
	}

	public String getUsername(){
		return username;
	}

	public int getPasswordHash(){
		return passwordHash;
	}

	public Position getPosition(){
		return position;
	}

}
