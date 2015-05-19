package kps.ui.test;

import org.junit.Test;

import kps.ui.LogonBox;
import kps.ui.listener.AuthDetailsListener;

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
