package webProject.server.myHandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
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
		String fileName=event.request().path();
		fileName=fileName.substring(1);
		try {
			byte[] css=IOUtils.toByteArray(Resources.class.getResourceAsStream(fileName));
			event.response().setChunked(true);
			event.response().putHeader("Cache-Control", "max-age=86400").putHeader("content-type", "text/css").write(Buffer.buffer(css)).end();
//			System.out.println(fileName);
		} catch (IOException|NullPointerException e) {
			event.response().setChunked(true);
			event.response().end("resources unavaliable");
		}
	}

}
