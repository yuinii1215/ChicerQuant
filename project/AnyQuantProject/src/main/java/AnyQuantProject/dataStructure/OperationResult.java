package AnyQuantProject.dataStructure;

/** 
*AnyQuantProject//AnyQuantProject.dataStructure//OperationResult.java
* @author  cxworks 
* @date 创建时间：2016年3月2日 下午2:24:05 
*/
/**
 * 用于描述操作成功或失败
 * 以及失败原因
 * @author cxworks
 *
 */
public class OperationResult {
	public boolean result;
	private String reason;
	public OperationResult(){
		result=true;
	}
	public OperationResult(boolean res,String reason){
		this.reason=reason;
		this.result=res;
	}
}
