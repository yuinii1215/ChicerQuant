package webProject.server.strategy.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
* AnyQuantProject/webProject.server.strategy.java/Strategy.java
* @author cxworks
* 2016年6月5日 上午11:49:24
*/

public class Strategy {


	public static void main(String[] args) {
			String para="sh600315 2016-04-01 2016-05-01";
			String[] cmd=new String[5];
			cmd[0]="python";
			cmd[1]="shorttime/x2test.py";
			cmd[2]="sh600315";
			cmd[3]="2016-04-01";
			cmd[4]="2016-05-01";
			try {
				System.out.println("Working Directory = " + System.getProperty("user.dir")); 
				Process process=Runtime.getRuntime().exec(cmd);
				Scanner key=new Scanner(process.getInputStream());
				System.out.println(key.nextLine());
				
	            process.waitFor();
				
				key.close();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	/**
	 * 
	 * @param id
	 * @param start
	 * @param end
	 * @return >0 normal p-value
	 * -1-error -2-timeout
	 */
	public static double x2Test(String id,String start,String end){
		String[] cmd={FileIndex.python,FileIndex.x2test,id,start,end};
		try {
			Process process=Runtime.getRuntime().exec(cmd);
			Scanner key=new Scanner(process.getInputStream());
			double d=key.nextDouble();
          process.waitFor(10,TimeUnit.SECONDS);
          key.close();
          if (process.exitValue()!=0) {
        	  return -1;
          	}
			
			return d;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return -2;
		}
	}
	
}
