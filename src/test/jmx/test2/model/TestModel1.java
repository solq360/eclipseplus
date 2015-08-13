package test.jmx.test2.model;

import java.util.ArrayList;
import java.util.List;

public class TestModel1 implements ITestModel1 {

	@Override
	public Result test1(Param param) {
		System.out.println(" call " +param.getPar2());
		return Result.valueOf(1, new String[] { "1" });
	}

	@Override
	public Result test2() {
		return Result.valueOf(1, new String[] { "1" });

	}

	@Override
	public List<Result> test3() {
		List<Result> result =new ArrayList<Result>();
		result.add(test2());
		return result;
	}

//	@Override
//	public Result2 test4() {
// 		return Result2.valueOf(1, new String[] { "1" });
//	}

}
