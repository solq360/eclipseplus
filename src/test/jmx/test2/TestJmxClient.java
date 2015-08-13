package test.jmx.test2;

import test.jmx.test2.model.ITestModel1;
import test.jmx.test2.model.Param;
import testplus2.game.jmx.JmxService;

/***
 * ²âÊÔ²»Í¬°ü....
 * */
public class TestJmxClient {

	private static int port = 5555;

	public static void main(String args[]) throws Exception {
		JmxService jmxService = new JmxService("localhost", port);
		jmxService.connect();
		ITestModel1 proxy = jmxService.getRpcProxy("test.jmx.test1.model:type=XXXXXX",ITestModel1.class );
		System.out.println(proxy.test1(new Param(1, "2")));
		System.out.println(proxy.test2());
		System.out.println(proxy.test3());

	}

}