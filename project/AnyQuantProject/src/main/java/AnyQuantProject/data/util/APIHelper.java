/**
 * 
 */
package AnyQuantProject.data.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * @author G
 *
 */
public class APIHelper {
	
	public JSONArray getAPI(String key){
		JSONArray jsonArray = null;
		try {
			URL url = new URL( "http://121.41.106.89:8010/api/"+key);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("X-Auth-Code", "2069376c8bdb54297e71a564833e2770");
			conn.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String str = br.readLine();
			JSONObject jsonObject = JSONObject.fromObject(str);
			jsonArray = JSONArray.fromObject(jsonObject.get("data".toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
}
