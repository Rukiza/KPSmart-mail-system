package kps.ui.formlistener;

/**
 * @author hardwiwill
 *
 * contains all info for a user being deleted
 */
public class DeleteUserEvent {

	private String username;

	public DeleteUserEvent(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}
