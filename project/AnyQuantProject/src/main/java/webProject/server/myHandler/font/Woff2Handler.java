package webProject.server.myHandler.font;

import org.apache.commons.io.IOUtils;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;

/**
* AnyQuantProject/webProject.server.myHandler/Woff2Handler.java
* @author cxworks
* 2016年5月9日 下午9:09:00
*/

public class Woff2Handler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext event) {
		String path=event.request().path();
		path=path.substring(1);
		try {
			String woff2=IOUtils.toString(Resources.class.getResourceAsStream(path));
			event.response().setChunked(true);
			event.response().putHeader("content-type", "application/x-font-woff2").write(woff2).end();

			
		} catch (Exception e) {
			e.printStackTrace();
			event.fail(404);
		}
	}
	
	

}
