package webProject.server.myHandler;

import AnyQuantProject.data.util.News;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by G on 16/5/25.
 */
public class NewsHandler implements Handler<RoutingContext>{


    @Override
    public void handle(RoutingContext routingContext) {
        String id=routingContext.request().query().substring(3,11);
        try {
            int ans=News.getDegree(id);
            JsonObject jsonObject=new JsonObject();
            jsonObject.put("retmsg","success");
            jsonObject.put("degree",ans);
            routingContext.response().setChunked(true);
            routingContext.response().putHeader("Content-type","application/json").write(jsonObject.encode()).end();

        }catch (Exception e){
            routingContext.fail(404);
        }


    }
}
