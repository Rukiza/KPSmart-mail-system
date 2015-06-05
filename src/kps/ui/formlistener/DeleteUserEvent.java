package kps.ui.formlistener;

public class DeleteUserEvent {

	private String username;

	public DeleteUserEvent(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}
