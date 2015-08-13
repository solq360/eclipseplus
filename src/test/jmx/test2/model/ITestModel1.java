package test.jmx.test2.model;

 
import java.util.List;

import javax.management.MXBean;

@MXBean
public interface ITestModel1 {
	//返回对象构造方法必须声明 @ConstructorProperties

	public Result test1(Param param);
	
	public Result test2();	
	
	public List<Result> test3();
	
	//出错
	//public Result2 test4();

}
