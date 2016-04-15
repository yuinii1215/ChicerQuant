package AnyQuantProject.bl.favoriteBL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.InitialContext;

import org.omg.IOP.IORHelper;

import AnyQuantProject.blService.favoriteBLService.FavoriteBLService;
import AnyQuantProject.data.factoryDATA.FactoryDATA;
import AnyQuantProject.dataService.factoryDATAService.FactoryDATAService;
import AnyQuantProject.dataService.realDATAService.singleStockDATAService.SingleStockDATAService;
import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.dataStructure.Stock;
import AnyQuantProject.util.constant.R;
import AnyQuantProject.util.method.CalendarHelper;
import AnyQuantProject.util.method.Checker;
import AnyQuantProject.util.method.IOHelper;

/** 
*AnyQuantProject//AnyQuantProject.bl.favoriteBL//FavoriteBLController.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:47:48 
*/

public class FavoriteBLController implements FavoriteBLService {
	private ArrayList<String> favor;
	
	public FavoriteBLController() {
		init();
	}
	
	private void init(){
		//try to read
				Object ans=IOHelper.read(R.FavorFilePath, R.FavorFileName);
				if (ans==null) {
					favor=new ArrayList<>();
				}
				else {
					
					favor=(ArrayList<String>) ans;
				}
	}

	@Override
	public List<Stock> getMyFavor() {
		//
		if (favor.isEmpty()) {
			return new ArrayList<>(0);
		}
		//get data service
		FactoryDATAService factoryDATAService=FactoryDATA.getInstance();
		SingleStockDATAService singleStockDATAService=factoryDATAService.getSingleStockDATAService();
		//

		List<Stock> ans=favor.stream()
			.map(name->
			singleStockDATAService.getOperation(name, CalendarHelper.getPreviousDay(Calendar.getInstance())))
			.collect(Collectors.toList());
		return ans;
	}

	@Override
	public OperationResult favorStock(String name) {
		//check if have
		if(!Checker.checkStringNotNull(name)||favor.contains(name)){
			return new OperationResult(false, "已加入关注列表");
		}
		//
		favor.add(name);
		return IOHelper.save(R.FavorFilePath, R.FavorFileName, favor);
		
	}

	@Override
	public OperationResult unFavorStock(String name) {
		if(!Checker.checkStringNotNull(name)||!favor.contains(name)){
			return new OperationResult(false, "未加入关注列表");
		}
		//
		favor.remove(name);
		return IOHelper.save(R.FavorFilePath, R.FavorFileName, favor);
	}

	@Override
	public boolean checkIsFavored(String name) {
		//check
		if (!Checker.checkStringNotNull(name)) {
			return false;
		}
		if (!Checker.checkListNotNull(favor)) {
			init();
		}
		return favor.contains(name);
	}
}
