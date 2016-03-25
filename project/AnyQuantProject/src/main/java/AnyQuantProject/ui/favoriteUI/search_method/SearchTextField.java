package AnyQuantProject.ui.favoriteUI.search_method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Window;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 自定提示控件
 * <p>
 * 根据输入的内容和提示列表来匹配提示信息<br/>
 * </p>
 * @author QiHan
 *
 */
public class SearchTextField{
	private TextField textField;
	private final static int LIST_MAX_SIZE = 6;
	private final static int LIST_CELL_HEIGHT = 20;
	
	/** pinyin4j 工具类 用来匹配输入内容 */
	private HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat();

	/** 用来存储显示 出来的信息列表 */
	private ObservableList<String> showCacheDataList = FXCollections.<String> observableArrayList();

	/** 用来存储缓存的信息列表 重写indexOf方法来获取匹配到的数据 */
	private volatile List<String> cacheDataList = new ArrayList<String>(){
		private static final long serialVersionUID = 281687373227150590L;

		@Override
		public int indexOf(Object searchContext){
			showCacheDataList.clear();
			if(null == searchContext || searchContext.toString().equals("")) {
				return -1;
			}
			int size = cacheDataList.size();
			for (int i = 0; i < size; i++){
				char[] charArry = cacheDataList.get(i).toCharArray();
				StringBuilder sbPinyin = new StringBuilder();
				String indexPinyin = new String();
				for (char ch : charArry){
					// 将搜索内容转换为拼音 方便搜索
					try{
						String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(ch, pinyinFormat);
						sbPinyin.append(null != pinyin ? pinyin[0] : ch);
						if(null != pinyin) {
							indexPinyin = indexPinyin + pinyin[0].charAt(0);
						}
					} catch (BadHanyuPinyinOutputFormatCombination e){
						sbPinyin.append(ch);
					}
				}
				if (cacheDataList.get(i).contains(searchContext.toString())|| sbPinyin.toString().contains(searchContext.toString()) || indexPinyin.contains(searchContext.toString())){
					showCacheDataList.add(cacheDataList.get(i));
				}
			}
			return -1;
		};
	};

	/** 监听输入框的内容 */
	private SimpleStringProperty inputContent = new SimpleStringProperty();

	/** 输入内容后显示的pop */
	private Popup popShowList = new Popup();

	/** 输入内容后显示的提示信息列表 */
	private ListView<String> autoTipList = new ListView<String>();

	SearchTextField(TextField textField, List<String> cacheDataList){
		if (null == textField){
			throw new RuntimeException("textField 不能为空");
		}
		this.textField = textField;
		if (null != cacheDataList){
			this.cacheDataList.addAll(cacheDataList);
		}
		configure();
		confListnenr();
	}

	SearchTextField(TextField textField){
		this(textField, null);
	}

	public void setCacheDataList(List<String> cacheDataList){
		this.cacheDataList=cacheDataList;
	}

	/**
	 * 
	 * <p>
	 * 添加监听事件
	 * </p>
	 * 
	 */
	private void confListnenr(){
		textField.textProperty().bindBidirectional(inputContent);

		textField.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				cacheDataList.add(inputContent.get()); // setOnAction事件后才会生效，此处是点击按钮时将文本框中数据存入到cacheDataList中
				removeDuplicate(cacheDataList);
			}
		});

		inputContent.addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue){
				configureListContext(newValue);    //当文本框中内容发生变化时会触发此事件，对文本框中内容进行匹配
			}
		});

		autoTipList.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event){
				selectedItem();
			}
		});

		autoTipList.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event){
				if(event.getCode() == KeyCode.ENTER) {
					selectedItem();
				}
			}
		});
	}
	
	/**
	 * 
	 * <p>
	 * 获取选中的list内容到输入框
	 * </p>
	 */
	private void selectedItem() {
		inputContent.set(autoTipList.getSelectionModel().getSelectedItem());
		textField.end();
		popShowList.hide();
	}

	/**
	 * 
	 * <p>
	 * 根据输入的内容来配置提示信息
	 * </p>
	 * 
	 */
	private void configureListContext(String tipContent){
		cacheDataList.indexOf(tipContent);
		if(!showCacheDataList.isEmpty()) {
			showTipPop();
		} else {
			popShowList.hide();
		}
	}
	

	/**
	 * 
	 * <p>
	 * 配置组建
	 * </p>
	 * 
	 */
	private void configure(){
		pinyinFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pinyinFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

		popShowList.setAutoHide(true);
		popShowList.getContent().add(autoTipList);
  		autoTipList.setItems(showCacheDataList);
	}
	
	public void removeDuplicate(List<String> cacheDataList2) {
		HashSet<String> h  =   new  HashSet<String>(cacheDataList2); 
	    cacheDataList2.clear(); 
	    cacheDataList2.addAll(h); 
	}
	

	/**
	 * 
	 * <p>
	 * 获取pop显示的窗口
	 * </p>
	 * 
	 */
	public final Window getWindow(){
		return getScene().getWindow();
	}

	/**
	 * 
	 * <p>
	 * 获取textField Scene
	 * </p>
	 * 
	 */
	public final Scene getScene(){
		return textField.getScene();
	}

	/**
	 * 
	 * <p>
	 * 显示pop
	 * </p>
	 */
	public final void showTipPop(){
		autoTipList.setPrefWidth(textField.getWidth() - 3);
		if(showCacheDataList.size() < LIST_MAX_SIZE) {
			autoTipList.setPrefHeight(showCacheDataList.size() * LIST_CELL_HEIGHT + 3);
		} else {
			autoTipList.setPrefHeight(LIST_MAX_SIZE * LIST_CELL_HEIGHT + 3);
		}
		Window window = getWindow();
		Scene scene = getScene();
		Point2D fieldPosition = textField.localToScene(0, 0);
		popShowList.show(window, window.getX() + fieldPosition.getX() + scene.getX(), window.getY() + fieldPosition.getY() + scene.getY() + textField.getHeight());
		autoTipList.getSelectionModel().clearSelection();
		autoTipList.getFocusModel().focus(-1);
	}
}
