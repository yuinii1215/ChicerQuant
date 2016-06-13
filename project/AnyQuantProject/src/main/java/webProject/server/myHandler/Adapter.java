package webProject.server.myHandler;

import io.vertx.core.json.JsonObject;
import webProject.server.strategy.java.Strategy;

/**
 * Created by cxworks on 16-6-11.
 */
public class Adapter {
    public static JsonObject getER(String id,String start,String end){
        JsonObject ans= Strategy.getTimeER(id, start, end);
        return ans;

    }

    public static JsonObject getBalance(String id,String start,String end){
        double[] value=Strategy.getBalance(id, start, end);
        JsonObject ans=new JsonObject();
        ans.put("p",value[0]);
        ans.put("10%",value[1]);
        ans.put("5%",value[2]);
        ans.put("1%",value[3]);
        return ans;
    }

    public static JsonObject getNormalTest(String id,String start,String end){
        double[] value=Strategy.x2Test(id, start, end);
        JsonObject ans=new JsonObject();
        ans.put("average",value[0]);

        ans.put("var",value[1]);

        ans.put("p",value[2]);
        return ans;
    }

    public static JsonObject getRisk(String id,String start,String end){
        double[] value=Strategy.risk(id, start, end);
        JsonObject ans=new JsonObject();
        ans.put("downside",value[0]);
        ans.put("VaR",value[1]);
        return ans;
    }
    public static JsonObject getQQ(String id,String start,String end){
        JsonObject ans=Strategy.getQ_Q(id, start, end);
        return ans;
    }
    public static JsonObject getRelate(String id,String start,String end){
        JsonObject ans=Strategy.getRelate(id, start, end);
        return ans;
    }
    public static JsonObject getARMA(String id,String start,String end){
        JsonObject ans=Strategy.getARMA(id, start, end);
        return ans;
    }
}
