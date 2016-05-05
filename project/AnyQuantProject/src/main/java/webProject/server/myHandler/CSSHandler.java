package webProject.server.myHandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;

/**
* AnyQuantProject/webProject.server.myHandler/CSSHandler.java
* @author cxworks
* 2016年5月3日 下午11:44:26
*/
public class CSSHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext event) {
		String fileName=event.request().getParam("file");
		try {
			String css=IOUtils.toString(new BufferedInputStream(Resources.class.getResourceAsStream("css/"+fileName)));
			event.response().setChunked(true);
			event.response().putHeader("content-type", "text/css").write(css).end();
		} catch (IOException|NullPointerException e) {
			event.response().setChunked(true);
			event.response().end("resources unavaliable");
		}
	}

}
