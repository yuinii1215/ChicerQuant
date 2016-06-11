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
                default:
                    routingContext.fail(400);
            }
        }catch (Exception e){
            e.printStackTrace();
            routingContext.fail(400);
        }
    }
}
