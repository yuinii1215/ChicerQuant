package webProject.server.myHandler;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;


import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;

/**
* AnyQuantProject/webProject.server.myHandler/JSHandler.java
* @author cxworks
* 2016年5月4日 上午9:30:34
*/
public class JSHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext event) {
		String fileName=event.request().getParam("file");
		
		try {
			String js=IOUtils.toString(new BufferedInputStream(Resources.class.getResourceAsStream("js/"+fileName)));
			event.response().setChunked(true);
			event.response().putHeader("content-type", "text/javascript").write(js).end();
		} catch (IOException|NullPointerException e) {
			event.response().setChunked(true);
			event.response().end("resources unavaliable");
		}
	}

}
