package webProject.server.myHandler;

import java.io.File;
import java.io.IOException;

import com.mchange.io.FileUtils;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;

/**
* AnyQuantProject/webProject.server.myHandler/RootHandler.java
* @author cxworks
* 2016年5月3日 下午11:30:53
*/
public class HtmlHandler implements Handler<RoutingContext>{

	@Override
	public void handle(RoutingContext event) {
		String loc=event.request().getParam("html");

		try {
			File file=new File(Resources.class.getResource(loc).getPath());
			String html=FileUtils.getContentsAsString(file);
			event.response().setChunked(true);
			event.response().putHeader("content-type", "text/html").write(html).end();
		} catch (IOException|NullPointerException e) {
			event.response().setChunked(true);
			event.response().end("resources unavaliable");
		}
	}

}
