package webProject.server.myHandler;

import java.io.File;
import java.io.IOException;

import com.mchange.io.FileUtils;

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
			File file=new File(Resources.class.getResource("css/"+fileName).getPath());
			String css=FileUtils.getContentsAsString(file);
			System.out.println(file.exists());
			event.response().setChunked(true);
			event.response().putHeader("content-type", "text/css").write(css).end();
		} catch (IOException|NullPointerException e) {
			event.response().setChunked(true);
			event.response().end("resources unavaliable");
		}
	}

}
