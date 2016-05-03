package webProject.server.myVerticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import webProject.server.myHandler.HtmlHander;

/**
* AnyQuantProject/webProject.server/RouterVerticle.java
* @author cxworks
* 2016年4月29日 上午8:16:56
*/
public class RouterVerticle extends AbstractVerticle {

	@Override
	public void start(){
		HttpServer httpServer=vertx.createHttpServer();
		Router homeRouter=Router.router(vertx);
		homeRouter.route(HttpMethod.GET,"/:loc/:type").handler(new HtmlHander());
		
//		homeRouter.route("*").handler()
		
		httpServer.requestHandler(homeRouter::accept).listen(8080);
		
	}
}
