package webProject.server.myHandler;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;

/**
* AnyQuantProject/webProject.server.myHandler/ImageHandler.java
* @author cxworks
* 2016年5月3日 下午11:47:07
*/
public class ImageHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext event) {
		String fileName=event.request().path();
		fileName=fileName.substring(1);
		InputStream inputStream = null;
		try {
			inputStream=Resources.class.getResourceAsStream(fileName);
			byte[] data=IOUtils.toByteArray(inputStream);
		      
			event.response().setChunked(true);
			
			event.response().putHeader("content-type", "image/png").write(Buffer.buffer(data)).end();
		} catch (IOException|NullPointerException e) {
			
			event.response().setChunked(true);
			event.response().end("resources unavaliable");
		}
		finally {
			try {
				inputStream.close();
			} catch (IOException | NullPointerException e) {
				e.printStackTrace();
			}
		      
		}
		
	}

}
