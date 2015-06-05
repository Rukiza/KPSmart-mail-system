package kps.ui.test;

import org.junit.Test;

import kps.ui.formlistener.AuthDetailsListener;
import kps.ui.window.LogonBox;

public class LogonBoxTests {

	@Test
	public void basicTest(){
		new LogonBox(new AuthDetailsListener(){
			public boolean onReceivedAuthDetails(String un, String pw){
				System.out.println(un + ", " + pw);
				return true;
			}
			public void onCancel(){
				System.out.println("Cancelled");
			}
		}, "Will", "pw");
	}

}
