package webProject.server.myHandler.font;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;

/**
* AnyQuantProject/webProject.server.myHandler/EOTHandler.java
* @author cxworks
* 2016年5月9日 下午9:12:53
*/

public class EOTHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext event) {
		String path=event.request().path();
		path=path.substring(1);
		
		try {
			InputStream inputStream=Resources.class.getResourceAsStream(path);
			byte[] eot=IOUtils.toByteArray(inputStream);
			event.response().setChunked(true);
			event.response().putHeader("content-type", "application/vnd.ms-fontobject").write(Buffer.buffer(eot)).end();
			inputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			event.fail(404);
		}
	}

}
