package kps.ui.test;

import org.junit.Test;

import kps.ui.AuthDetailsListener;
import kps.ui.LogonBox;

public class LogonBoxTests {

	@Test
	public void basicTest(){
		new LogonBox(new AuthDetailsListener(){
			public void onReceivedAuthDetails(String un, String pw){
				System.out.println(un + ", " + pw);
			}
			public void onReceivedCancel(){
				System.out.println("Cancelled");
			}
		}, "Will", "pw");
	}

}
