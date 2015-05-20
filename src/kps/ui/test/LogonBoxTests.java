package kps.ui.test;

import org.junit.Test;

import kps.ui.listener.AuthDetailsListener;
import kps.ui.window.LogonBox;

public class LogonBoxTests {

	@Test
	public void basicTest(){
		new LogonBox(new AuthDetailsListener(){
			public void onReceivedAuthDetails(String un, String pw){
				System.out.println(un + ", " + pw);
			}
			public void onCancel(){
				System.out.println("Cancelled");
			}
		}, "Will", "pw");
	}

}
