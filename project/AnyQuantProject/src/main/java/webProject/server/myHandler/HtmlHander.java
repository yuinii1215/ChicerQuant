package webProject.server.myHandler;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.mchange.io.FileUtils;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;

/**
* AnyQuantProject/webProject.server.myHandler/HtmlHander.java
* @author cxworks
* 2016年4月29日 上午8:25:12
*/
public class HtmlHander implements Handler<RoutingContext>{
//	public static void main(String[] args) {
//		System.out.println(Resources.class.getResource("index.html").getPath());
//		System.out.println(new File(Resources.class.getResource("index.html").getPath()).exists());
//	}
	

	@Override
	public void handle(RoutingContext event) {
		
		String index=event.request().getParam("type");
		
		try {
			File file=new File(Resources.class.getResource(index+".html").getPath());
			String html=FileUtils.getContentsAsString(file);
			event.response().setChunked(true);
			event.response().putHeader("content-type", "text/html").write(html).end();
		} catch (IOException|NullPointerException e) {
			event.response().setChunked(true);
			event.response().end("resources unavaliable");
		}
	}

}
