package test.jmx.test1;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import test.jmx.test1.model.TestModel1;
import testplus2.game.jmx.JmxService;
import testplus2.game.jmx.JxmUtli;
import testplus2.game.model.GamePlug;
import testplus2.game.model.GamePlugMBean;

public class TestJmxServer {
	private static int port = 5555;

	public static void main(String[] args) {
		JmxService jmxService = new JmxService("localhost", port);
		// ×¢²ámbean ÊµÀý
		TestModel1 mbean = new TestModel1();
		jmxService.register(mbean);
		jmxService.start();

	}

	public static void test2() throws IOException,
			MalformedObjectNameException, InstanceAlreadyExistsException,
			MBeanRegistrationException, NotCompliantMBeanException,
			InterruptedException {

		MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();

		// ×¢²ámbean ÊµÀý
		GamePlug gamePlus = new GamePlug();
		ObjectName connectorName = new ObjectName(
				JxmUtli.getObjectName(GamePlugMBean.class));
		mbeanServer.registerMBean(gamePlus, connectorName);

		// Æô¶¯server
		LocateRegistry.createRegistry(port);
		final JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi://localhost/jndi/rmi://localhost:" + port
						+ "/jmxrmi");
		JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory
				.newJMXConnectorServer(url, null, mbeanServer);
		jmxConnectorServer.start();
	}

	static void test() {
		JmxService jmxService = new JmxService("localhost", port);
		// ×¢²ámbean ÊµÀý
		GamePlug gamePlus = new GamePlug();
		jmxService.register(gamePlus);
		jmxService.start();
	}
}
