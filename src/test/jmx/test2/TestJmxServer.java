package test.jmx.test2;

import test.jmx.test1.model.TestModel1;
import testplus2.game.jmx.JmxService;

public class TestJmxServer {
	private static int port = 5555;

	
	public static void main(String[] args) {
		JmxService jmxService = new JmxService("localhost", port);
		// ×¢²ámbean ÊµÀý
		TestModel1 mbean = new TestModel1();
		jmxService.register("test.jmx.test1.model:type=XXXXXX",mbean);
		jmxService.start();

	}

}
