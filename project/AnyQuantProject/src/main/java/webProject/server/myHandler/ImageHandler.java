package webProject.server.myHandler;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;

import com.mchange.io.FileUtils;

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
		String fileName=event.request().getParam("file");
		FileImageInputStream inputStream = null;
		ByteArrayOutputStream outputStream=null;
		try {
			File file=new File(Resources.class.getResource("img/"+fileName).getPath());
			inputStream=new FileImageInputStream(file);
			outputStream=new ByteArrayOutputStream();
			
			byte[] buf = new byte[1024];
		      int numBytesRead = 0;
		      while ((numBytesRead = inputStream.read(buf)) != -1) {
		      outputStream.write(buf, 0, numBytesRead);
		      }
		      byte[] data=outputStream.toByteArray();
		      
			event.response().setChunked(true);
			
			event.response().putHeader("content-type", "image/png").write(Buffer.buffer(data)).end();
		} catch (IOException|NullPointerException e) {
			
			event.response().setChunked(true);
			event.response().end("resources unavaliable");
		}
		finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException | NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		}
		
	}

}
