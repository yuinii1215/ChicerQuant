package webProject.server.myVerticle;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import webProject.resources.Resources;
import webProject.server.myHandler.CSSHandler;
import webProject.server.myHandler.HtmlHandler;
import webProject.server.myHandler.ImageHandler;
import webProject.server.myHandler.JSHandler;

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
		homeRouter.route("/:html").handler(new HtmlHandler());
		homeRouter.route(HttpMethod.GET,"/css/:file").handler(new CSSHandler());
		homeRouter.route(HttpMethod.GET,"/img/:file").handler(new ImageHandler());
		homeRouter.route(HttpMethod.GET,"/js/:file").handler(new JSHandler());
		
		homeRouter.route().handler(rt->{
			try {
				String html=IOUtils.toString(new BufferedInputStream(Resources.class.getResourceAsStream("home.html")));
				rt.response().setChunked(true);
				rt.response().putHeader("content-type", "text/html").write(html).end();
			} catch (IOException|NullPointerException e) {
				rt.response().setChunked(true);
				rt.response().end("resources unavaliable");
			}
			
		});
		httpServer.requestHandler(homeRouter::accept).listen(8020);

		
	}
}
