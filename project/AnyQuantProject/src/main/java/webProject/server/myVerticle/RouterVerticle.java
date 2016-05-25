package webProject.server.myVerticle;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import webProject.resources.Resources;
import webProject.server.myHandler.*;
import webProject.server.myHandler.font.OTFHandler;
import webProject.server.myHandler.font.SFNTHandler;
import webProject.server.myHandler.font.SVGHandler;
import webProject.server.myHandler.font.TTFHandler;
import webProject.server.myHandler.font.Woff2Handler;
import webProject.server.myHandler.font.WoffHandler;

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
		homeRouter.route().pathRegex(".*html").handler(new HtmlHandler());
		homeRouter.route().pathRegex(".*css").handler(new CSSHandler());
		homeRouter.route().pathRegex(".*png").handler(new ImageHandler());
		homeRouter.route().pathRegex(".*js").handler(new JSHandler());
		homeRouter.route().pathRegex(".*jpg").handler(new JPGHandler());
		homeRouter.route().pathRegex(".*jpeg").handler(new JPGHandler());
		//
		homeRouter.route().pathRegex(".*svg").handler(new SVGHandler());
		homeRouter.route().pathRegex(".*otf").handler(new OTFHandler());
		homeRouter.route().pathRegex(".*ttf").handler(new TTFHandler());
		homeRouter.route().pathRegex(".*woff").handler(new WoffHandler());
		homeRouter.route().pathRegex(".*woff2").handler(new Woff2Handler());
		homeRouter.route().pathRegex(".*sfnt").handler(new SFNTHandler());
		homeRouter.route().pathRegex("/news").blockingHandler(new NewsHandler());
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
