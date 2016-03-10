/**
 * 
 */
package AnyQuantProject.dataStructure;

import AnyQuantProject.dataStructure.enumInterface.EnumCount;
import AnyQuantProject.dataStructure.enumInterface.EnumObservable;

/**
 * @author G
 *
 */
public enum Exchange  implements EnumObservable<Exchange>,EnumCount<Exchange>{
	SH("上交所","sh",1), //上交所
	SZ("深交所","sz",2);
	private String chinese;
	private String english;
	private int count;
	private Exchange(String chinese,String english,int count) {
		this.chinese=chinese;
		this.count=count;
		this.english=english;
	}

	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	public String getChinese() {
		return chinese;
	}

	@Override
	public String getEnglish() {
		return english;
	} 
	@Override
	public String toString(){
		return english;
	}
}
