package AnyQuantProject.ui.favoriteUI.search_method;

import java.util.List;

import javafx.scene.control.TextField;

/**
 * <p>
 * 自动提示组件构造器,调用这个方法去创建提示控件
 * </p>
 * @author QiHan
 *
 */
public class SeachTextFieldBuilder{
	/**
	 * 
	 * <p>
	 * 将textField控件转换为可提示
	 * </p>
	 */
	public static final SearchTextField build(TextField textField, List<String> cacheData){
		return new SearchTextField(textField, cacheData);
	}
	
	public static final SearchTextField build(TextField textField){
		return new SearchTextField(textField);
	}

}
