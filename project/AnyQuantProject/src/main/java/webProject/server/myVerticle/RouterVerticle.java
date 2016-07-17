package webProject.server.myVerticle;

import java.io.BufferedInputStream;
import java.io.IOException;

import io.vertx.core.buffer.Buffer;
import org.apache.commons.io.IOUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import webProject.server.myHandler.*;

/**
* AnyQuantProject/webProject.server/RouterVerticle.java
* @author cxworks
* 2016年4月29日 上午8:16:56
*/
public class RouterVerticle extends AbstractVerticle {
	public RouterVerticle() {
		super();
	}

	@Override
	public void start(){
		HttpServer httpServer=vertx.createHttpServer();
		Router homeRouter=Router.router(vertx);
		homeRouter.route().pathRegex("/news").blockingHandler(new NewsHandler());
        homeRouter.route("/strategy/:func").blockingHandler(new StrategyHandler());
		homeRouter.route().handler(rt->{
			rt.fail(404);
			
		});
		httpServer.requestHandler(homeRouter::accept).listen(8020);

		
	}
}
