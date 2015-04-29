package kps.users;

import kps.enums.Position;

/**
 * Represents a physical user of the KPSmartSystem.
 * 
 * @author David
 *
 */
public class KPSUser {
	
	// fields
	private String username;
	private int passwordHash;
	private Position position;
	
	/**
	 * Constructor:
	 * Constructs a KPSUser based on the specified parameters.
	 * 
	 * @param username
	 * 		-- the name of the user
	 * @param passwordHash
	 * 		-- a hash value generated from the user's password
	 * @param position
	 * 		-- the position held by the user at KPS
	 */
	public KPSUser(String username, int passwordHash, Position position){
		this.username = username;
		this.passwordHash = passwordHash;
		this.position = position;
	}
	
	/**
	 * Returns the username of this user.
	 * 
	 * @return username
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * Set the username to the specified name.
	 * 
	 * @param username
	 * 		-- the new username
	 */
	public void setUsername(String username){
		this.username = username;
	}
	
	/**
	 * Returns the hash value for the password of this user.
	 * 
	 * @return passwordHash
	 */
	public int getPasswordHash(){
		return passwordHash;
	}
	
	/**
	 * Set the password hash to the specified hash value.
	 * 
	 * @param passwordHash
	 * 		--- the new password hash
	 */
	public void setPasswordHash(int passwordHash){
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Returns the position held by this user at KPS.
	 * 
	 * @return position
	 */
	public Position getPosition(){
		return position;
	}
	
	/**
	 * Set the position of this user at KPS to the specified position.
	 * 
	 * @param position
	 * 		-- the new position
	 */
	public void setPositon(Position position){
		this.position = position;
	}
}
