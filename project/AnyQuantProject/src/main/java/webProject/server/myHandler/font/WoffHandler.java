package webProject.server.myHandler.font;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;

/**
* AnyQuantProject/webProject.server.myHandler/WoffHandler.java
* @author cxworks
* 2016年5月8日 下午8:00:38
*/

public class WoffHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext event) {
		String path=event.request().path();
		path=path.substring(1);
		System.out.println(path);
		try {
			
			InputStream inputStream=Resources.class.getResourceAsStream(path);
			byte[] woff=IOUtils.toByteArray(inputStream);
			event.response().setChunked(true);
			event.response().putHeader("Cache-Control", "max-age=86400").putHeader("content-type", "application/x-font-woff").write(Buffer.buffer(woff)).end();
			inputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			event.fail(404);
		}
	}

}
