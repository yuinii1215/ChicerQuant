package AnyQuantProject.ui.moduleUI;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author QiHan
 *
 */

public class MyBalls{
    //PathTransition ptr = new PathTransition();
    Timeline loop ;
    
    int num=29;
    String[] moduleTypes = new String[num];
//    @Override
//    public void start(Stage stage) {
//
//        initUI(stage);
//    }
    public MyBalls(Pane pane){
    	 initUI(pane);
    }
    private void initUI(Pane pane) {
  //  private void initUI(Stage stage) {
    	Circle[] circles = new Circle[num];
    	Group group = new Group();
    	Color[] color = {Color.GRAY, Color.YELLOW};
    //	Pane root = new Pane();
    	
  
    for(int i=0;i<num;i++){ 
    	double controlY1 = ((int)(Math.random() * (900-350)))+20;
    	double controlX2 = ((int)(Math.random() * (600-350)))+20;
    	final Circle circle = new Circle(30);
    	circle.relocate(controlY1 , controlX2);
    	circle.setFill(color[(int) (Math.random() * 2)]);
        loop = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

            double deltaX = 3;
            double deltaY = 3;

           @Override
           public void handle(final ActionEvent t) {
        	   doHandle();
//           circle.setOnMouseEntered(new CircleEnterHandler());
//           circle.setOnMouseExited(new CircleExitedHandler());
//           circle.setOnMouseClicked(new CircleClickedHandler());    
           	}
          
           private void doHandle(){
                	circle.setLayoutX(circle.getLayoutX() + deltaX);
                    circle.setLayoutY(circle.getLayoutY() + deltaY);

                    final Bounds bounds = pane.getBoundsInLocal();
                    final boolean atRightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
                    final boolean atLeftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
                    final boolean atBottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
                    final boolean atTopBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());

                    if (atRightBorder || atLeftBorder) {
                        deltaX *= -1;
                    }
                    if (atBottomBorder || atTopBorder) {
                        deltaY *= -1;
                    }
           }
           }
        ));
     
        circles[i]=circle;
        circles[i].setOnMouseEntered(new CircleEnterHandler());
        circles[i].setOnMouseExited(new CircleExitedHandler());
        circles[i].setOnMouseClicked(new CircleClickedHandler());    
        
        group.getChildren().addAll(circles[i]);
        
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }      
    	pane.getChildren().addAll(group);
//    	AnchorPane a = new AnchorPane();
//    	a.setPrefSize(900, 600);
//    	a.getChildren().addAll(group);
//    	
//    	root.getChildren().addAll(a);
//        Scene scene = new Scene(root,900, 600);
//
//        stage.setTitle("PathTransition");
//        stage.setScene(scene);
//        stage.show();  
//       
//        
    
	}
 
    private class CircleEnterHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
        	String command=event.getPickResult().toString();
        	System.out.println("command:::"+command);
//    		for(int i=0;i<b.length;i++){
//    			if(command==b[i].getActionCommand()){//判断点击的按钮，出现显示命令的界面
//    				new OrderDisplay(command,num);
//    			}
//    		}
        	doHandle();
        }
        
        private void doHandle() {
        
        	loop.pause();
         
        }

    }
    
    private class CircleExitedHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {   
            doHandle();
        }
        private void doHandle() {
        loop.play();
        }
    }
    
    private class CircleClickedHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            doHandle();
        }  
        private void doHandle() {
        	loop.stop();
        }

    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
}
  