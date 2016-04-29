package webProject.starter;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.Deployment;
import io.vertx.core.impl.VertxFactoryImpl;
import io.vertx.core.spi.VertxFactory;

/**
* AnyQuantProject/webProject.starter/Main.java
* @author cxworks
* 2016年4月29日 上午7:54:23
*/
public class Main {
	
	public static void main(String[] args) {
		VertxOptions options=new VertxOptions();
		VertxFactory factory=new VertxFactoryImpl();
		Vertx vertx=factory.vertx(options);
		DeploymentOptions deploymentOptions=new DeploymentOptions();
		vertx.deployVerticle("webProject.server.myVerticle.RouterVerticle", deploymentOptions);
	}

}
