package kps.ui.formlistener;

import kps.enums.Position;

/**
 * @author hardwiwill
 *
 * contains all info for a user being created
 */
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
