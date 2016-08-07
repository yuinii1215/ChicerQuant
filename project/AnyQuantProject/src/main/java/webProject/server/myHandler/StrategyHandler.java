package webProject.server.myHandler;


import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by cxworks on 16-6-11.
 */
public class StrategyHandler implements Handler<RoutingContext>{


    @Override
    public void handle(RoutingContext routingContext) {
        try {
            String method=routingContext.request().getParam("func");
            String Q=routingContext.request().query();
            String[] arr=Q.split("&|=");
            String id=arr[1];
            String start=arr[3];
            String end=arr[5];
            HttpServerResponse response=routingContext.response();
            response.setChunked(true);
            response.putHeader("Content-type","application/json");
            response.putHeader("Access-Control-Allow-Headers","X-Requested-With");
            response.putHeader("Access-Control-Allow-Origin","*");
            switch (method){
                case "getER":
                    JsonObject ansE=Adapter.getER(id,start,end);
                    response.write(ansE.encode()).end();
                    break;
                case "getNormalTest":
                    JsonObject ansN=Adapter.getNormalTest(id,start,end);
                    response.write(ansN.encode()).end();
                    break;
                case "getRisk":
                    JsonObject ansR=Adapter.getRisk(id,start,end);
                    response.write(ansR.encode()).end();
                    break;
                case "getQQ":
                    JsonObject ansQ=Adapter.getQQ(id,start,end);
                    response.write(ansQ.encode()).end();
                    break;
                case "getRelate":
                    JsonObject ansRa=Adapter.getRelate(id,start,end);
                    response.write(ansRa.encode()).end();
                    break;
                case "getBalanceTest":
                    JsonObject ansB=Adapter.getBalance(id,start,end);
                    response.write(ansB.encode()).end();
                    break;
                case "getARMA":
                    JsonObject ansAR=Adapter.getARMA(id,start,end);
                    response.write(ansAR.encode()).end();
                    break;
                case "getCRUM":
                    JsonObject ansCR=Adapter.getCRUM(id,start,end);
                    response.write(ansCR.encode()).end();
                    break;
                default:
                    routingContext.fail(400);
            }
        }catch (Exception e){
            e.printStackTrace();
            routingContext.fail(400);
        }
    }
}
