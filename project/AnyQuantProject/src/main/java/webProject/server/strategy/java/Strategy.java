package webProject.server.strategy.java;

import io.vertx.core.json.JsonObject;

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
	public static double[] x2Test(String id,String start,String end){
		String[] cmd={FileIndex.python,FileIndex.x2test,id,start,end};
		try {
			Process process=Runtime.getRuntime().exec(cmd);
			Scanner key=new Scanner(process.getInputStream());

          process.waitFor(10,TimeUnit.SECONDS);

          if (process.exitValue()!=0) {
        	  return new  double[1];
          }
            double aver=Double.parseDouble(key.nextLine());
            double var=Double.parseDouble(key.nextLine());
            double p=Double.parseDouble(key.nextLine());
            double[] ans={aver,var,p};
            key.close();
			return ans;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return new double[2];
		}
	}

    /**
     * 风险评估
     * @param id
     * @param start
     * @param end
     * @return len=2-success 1-error 3-fatal error
     */
	public static double[] risk(String id,String start,String end){
        String[] cmd={FileIndex.python,FileIndex.risk,id,start,end};
        try {
            Process process=Runtime.getRuntime().exec(cmd);
            Scanner key=new Scanner(process.getInputStream());
            double d1=key.nextDouble();
            double d2=key.nextDouble();
            process.waitFor(10,TimeUnit.SECONDS);
            key.close();
            if (process.exitValue()!=0) {
                return new double[1];
            }
			double[] ans={d1,d2};
            return ans;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new double[3];
        }
    }

    /**
     * 获得一段时间的收益率
     * @param id
     * @param start
     * @param end
     * @return
     */
    public static JsonObject getTimeER(String id,String start,String end){
        String[] cmd={FileIndex.python,FileIndex.GetData,id,start,end};
        try {
            Process process=Runtime.getRuntime().exec(cmd);
            Scanner key=new Scanner(process.getInputStream());
            process.waitFor(10,TimeUnit.SECONDS);
            if (process.exitValue()!=0){
                return null;
            }
            //
            JsonObject ans=new JsonObject();
            int len=0;
            while (key.hasNext()){
                JsonObject temp=new JsonObject();
                String date=key.nextLine();
                double d=Double.parseDouble(key.nextLine())*100;
//                System.out.println(d);
                temp.put("date",date);
                temp.put("er",d);
                ans.put(Integer.toString(len),temp);
                len++;
            }

            return ans;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonObject getQ_Q(String id,String start,String end){
        String[] cmd={FileIndex.python,FileIndex.QQ,id,start,end};
        try {
            Process process=Runtime.getRuntime().exec(cmd);
            Scanner key=new Scanner(process.getInputStream());
            process.waitFor(10,TimeUnit.SECONDS);
            if (process.exitValue()!=0){
                return null;
            }
            //
            JsonObject ans=new JsonObject();
            int len=0;
            while (key.hasNext()){
                JsonObject temp=new JsonObject();
                double x=Double.parseDouble(key.nextLine());
                double y=Double.parseDouble(key.nextLine());
//                System.out.println(d);
                temp.put("x",x);
                temp.put("y",y);
                ans.put(Integer.toString(len),temp);
                len++;
            }

            return ans;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonObject getRelate(String id,String start,String end){
        String[] cmd={FileIndex.python,FileIndex.relate,id,start,end};
        try {
            Process process=Runtime.getRuntime().exec(cmd);
            Scanner key=new Scanner(process.getInputStream());
            process.waitFor(10,TimeUnit.SECONDS);
            if (process.exitValue()!=0){
                return null;
            }
            //
            JsonObject ans=new JsonObject();
            double p=Double.parseDouble(key.nextLine());
            ans.put("p_value",p);
            JsonObject acf=new JsonObject();
            int count=0;
            while (key.hasNext()){
                String line=key.nextLine();
                if(line.equalsIgnoreCase("pacf"))
                    break;
                acf.put(Integer.toString(count),Double.parseDouble(line));
                count++;
            }
            ans.put("acf",acf);
            JsonObject pacf=new JsonObject();
            count=0;
            while (key.hasNext()){
                String line=key.nextLine();
                pacf.put(Integer.toString(count),Double.parseDouble(line));
                count++;
            }
            ans.put("pacf",pacf);
            return ans;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double[] getBalance(String id,String start,String end){
        String[] cmd={FileIndex.python,FileIndex.balanceTest,id,start,end};
        try {
            Process process=Runtime.getRuntime().exec(cmd);
            Scanner key=new Scanner(process.getInputStream());

            process.waitFor(10,TimeUnit.SECONDS);

            if (process.exitValue()!=0) {
                return new  double[1];
            }
            double value=Double.parseDouble(key.nextLine());
            double ten=Double.parseDouble(key.nextLine());
            double five=Double.parseDouble(key.nextLine());
            double one=Double.parseDouble(key.nextLine());
            double[] ans={value,ten,five,one};
            key.close();
            return ans;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new double[2];
        }
    }

    public static JsonObject getARMA(String id,String start,String end){
        String[] cmd={FileIndex.python,FileIndex.ARMA,id,start,end};
        try {
            Process process=Runtime.getRuntime().exec(cmd);
            Scanner key=new Scanner(process.getInputStream());

            process.waitFor(2,TimeUnit.MINUTES);

            if (process.exitValue()!=0) {
                return null;
            }
            JsonObject ans=new JsonObject();
            while (!key.nextLine().equalsIgnoreCase("begin")){

            }
            //
            JsonObject e=new JsonObject();
            double wason=Double.parseDouble(key.nextLine());
            double normal=Double.parseDouble(key.nextLine());
            e.put("watson",wason);
            e.put("normal",normal);
            while (!key.nextLine().equalsIgnoreCase("e")){}
            JsonObject evalue=new JsonObject();
            int count=0;
            while (!key.nextLine().equalsIgnoreCase("ljungbox")){
                double x=Double.parseDouble(key.nextLine());
                double y=Double.parseDouble(key.nextLine());
                JsonObject cell=new JsonObject();
                cell.put("x",x);
                cell.put("y",y);
                evalue.put(Integer.toString(count),cell);
                count++;
            }
            e.put("evalue",evalue);
            ans.put("e",e);
            //end e
            JsonObject ljungbox=new JsonObject();
            count=0;
            while (!key.nextLine().equalsIgnoreCase("predict")){
                double te=Double.parseDouble(key.nextLine());
                ljungbox.put(Integer.toString(count),te);
                count++;
            }
            ans.put("ljungbox",ljungbox);
            //end ljungbox
            JsonObject predict=new JsonObject();
            count=0;
            while (key.hasNext()){
                String date=key.nextLine();
                if (date.length()==0)
                    break;
                double er=Double.parseDouble(key.nextLine());
                JsonObject cell=new JsonObject();
                cell.put("date",date);
                cell.put("er",er);
                predict.put(Integer.toString(count),cell);
                count++;
            }
            ans.put("predict",predict);
            return ans;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
