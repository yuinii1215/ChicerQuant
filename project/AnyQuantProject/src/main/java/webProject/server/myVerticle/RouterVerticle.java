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
import webProject.server.myHandler.JPGHandler;
import webProject.server.myHandler.JSHandler;
import webProject.server.myHandler.WoffHandler;

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
		homeRouter.route().pathRegex(".*html").handler(new HtmlHandler());
		homeRouter.route().pathRegex(".*css").handler(new CSSHandler());
		homeRouter.route().pathRegex(".*png").handler(new ImageHandler());
		homeRouter.route().pathRegex(".*js").handler(new JSHandler());
		homeRouter.route().path(".*jpg").handler(new JPGHandler());
		homeRouter.route().path(".*jpeg").handler(new JPGHandler());
		//
		homeRouter.route().pathRegex(".*svg").handler(new CSSHandler());
		homeRouter.route().pathRegex(".*otf").handler(new CSSHandler());
		homeRouter.route().pathRegex(".*ttf").handler(new CSSHandler());
		homeRouter.route().pathRegex(".*woff").handler(new WoffHandler());
		homeRouter.route().pathRegex(".*woff2").handler(new WoffHandler());
		
		homeRouter.route().handler(rt->{
			try {
				String html=IOUtils.toString(new BufferedInputStream(Resources.class.getResourceAsStream("welcome.html")));
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
