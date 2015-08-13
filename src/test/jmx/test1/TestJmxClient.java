package test.jmx.test1;

import java.util.Set;

import javax.management.ObjectName;

import test.jmx.test1.model.ITestModel1;
import test.jmx.test1.model.Param;
import test.jmx.test1.model.TestModel1;
import testplus2.game.jmx.JmxService;
import testplus2.game.model.GamePlug;
import testplus2.game.model.GamePlugMBean;

public class TestJmxClient {

	private static int port = 5555;

	public static void main(String args[]) throws Exception {
		JmxService jmxService = new JmxService("localhost", port);
		jmxService.connect();
		ITestModel1 proxy = jmxService.getRpcProxy(ITestModel1.class,
				TestModel1.class);
		System.out.println(proxy.test1(new Param(1, "2")));
		System.out.println(proxy.test2());
		System.out.println(proxy.test3());

	}

	static void test0() throws Exception {

		JmxService jmxService = new JmxService("localhost", port);
		jmxService.connect();
		GamePlugMBean proxy = jmxService.getRpcProxy(GamePlugMBean.class,
				GamePlug.class);
		System.out.println(proxy.getAllConsoleMessage("abc"));

		Set<?> objectNames = jmxService.getAllRegisterMBean();

		for (Object name : objectNames) {
			ObjectName objName = (ObjectName) name;
			System.out
					.println("    Object Name: " + objName.getCanonicalName());
		}

		jmxService.close();
	}

	// private static void test1() throws Exception {
	// final JMXServiceURL jmxServiceUrl = new JMXServiceURL(
	// "service:jmx:rmi://localhost/jndi/rmi://localhost:" + port
	// + "/jmxrmi");
	// RMIConnector connector = new RMIConnector(jmxServiceUrl, null);
	// connector.connect();
	//
	// MBeanServerConnection connection = connector.getMBeanServerConnection();
	//
	// ObjectName hwName = new ObjectName(":name=helloWorld"
	// + System.nanoTime());
	// connection.createMBean(GamePlus.class.getName(), hwName);
	// connection.invoke(hwName, "printHello", null, null);
	//
	// System.out.println(connection.invoke(hwName, "worldName", null, null));
	//
	// connector.close();
	// }
}