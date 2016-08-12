package webProject.server.myHandler;

import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class GitHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext arg0) {
		String[] cmd={"git","pull"};
		HttpServerResponse response=arg0.response();
        response.setChunked(true);
        response.putHeader("Content-type","text/plain");
        response.putHeader("Access-Control-Allow-Headers","X-Requested-With");
        response.putHeader("Access-Control-Allow-Origin","*");
		try {
			Process process=Runtime.getRuntime().exec(cmd);
			process.waitFor(); 
			if (process.exitValue()==0) {
				byte[] bs=IOUtils.toByteArray(process.getInputStream());
				
	            response.write(Buffer.buffer(bs)).end();
			}else {
				byte[] bs=IOUtils.toByteArray(process.getErrorStream());
				
	            response.write(Buffer.buffer(bs)).end();
			}
			
		} catch (Exception e) {
			response.write(Buffer.buffer(e.getMessage())).end();
		}
	}
	

}
