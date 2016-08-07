package AnyQuantProject.util.method;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import AnyQuantProject.dataStructure.OperationResult;
import AnyQuantProject.util.constant.R;

/** 
*AnyQuantProject//AnyQuantProject.util.method//IOHelper.java
* @author  cxworks 
* @date 创建时间：2016年3月3日 下午7:59:29 
*/

public class IOHelper {
	
	public static OperationResult save(String path,String name,Serializable obj){
		String filePath=path+name+R.tail;
		File file=new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return new OperationResult(false, "cannot create file");
			}
		}
		try {
			ObjectOutputStream writer=new ObjectOutputStream(new FileOutputStream(file));
			writer.writeObject(obj);
			writer.close();
			return new OperationResult();
		} catch (IOException e) {
			e.printStackTrace();
			return new OperationResult(false, "write error");
		}
	}
	
	public static Object read(String path,String name){
		String filePath=path+name+R.tail;
		File file=new File(filePath);
		if (!file.exists()) {
			return null;
		}
		try {
			ObjectInputStream reader=new ObjectInputStream(new FileInputStream(file));
			Object ans=reader.readObject();
			return ans;
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
	
	public static OperationResult deleteFile(String path,String name){
		String filePath=path+name+R.tail;
		File file=new File(filePath);
		try {
			if (file.exists()) {
				file.delete();
			}
			return new OperationResult();
		} catch (Exception e) {
			e.printStackTrace();
			return new OperationResult(false, e.getMessage());
		}
		
	}
}
