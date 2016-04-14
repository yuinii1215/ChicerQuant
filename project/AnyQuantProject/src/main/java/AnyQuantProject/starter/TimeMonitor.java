/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnyQuantProject.starter;

import AnyQuantProject.ui.singleStockInfoUI.SingleStockInfoUIController;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author GraceHan
 */
public class TimeMonitor extends Timer{
    
     private static TimeMonitor timer = null;
     
     public static void start(SingleStockInfoUIController controller) {
        if(null != timer){
            return;
        }
 
        timer = new TimeMonitor();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {  }
        }, 1000, 1000 * 60 * 2);// 启动项目一秒后执行，然后每次间隔2分钟         
          controller.endLoad();
    }  
     
     public static void stop(){
        if(null != timer){        
            //SUCCESS QUIT
            timer.cancel();        
        }else{
           
        }
    }
}
