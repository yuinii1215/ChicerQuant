package AnyQuantProject.util.method;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
* AnyQuantProject/AnyQuantProject.util.method/NetCheck.java
* @author cxworks
* 2016年4月15日 下午9:28:36
*/
public class NetCheck {

	
	public static boolean checkNet() {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				System.exit(0);
			}
			URL url = null;
			try {
				url = new URL("http://baidu.com/");
				InputStream in = url.openStream();
				System.out.println("连接正常");
				in.close();
				return true;
			} catch (Exception e) {
				//do nothing
			}
		}
		
	}
	
}
