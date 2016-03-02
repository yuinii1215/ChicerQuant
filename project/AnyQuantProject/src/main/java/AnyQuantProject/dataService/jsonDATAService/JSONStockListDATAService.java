/**
 * 
 */
package AnyQuantProject.dataService.jsonDATAService;

import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;

import org.json.JSONObject;

import AnyQuantProject.dataStructure.Exchange;

/**
 * @author G
 *
 */
public interface JSONStockListDATAService {
	public JSONArray getAllStocks(Calendar date, Exchange exchange);
}
