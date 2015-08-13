package testplus2.game.jmx;

import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.registry.LocateRegistry;
import java.util.Set;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
   
/**
 * jmx 服务实现 <br>
 * 资料参考 :
 * http://javaarm.com/faces/display.xhtml;jsessionid=2A14sKZXzJSnnsASHiAJdhNl
 * ?tid=3626&page=1&print=true
 * 
 * @author solq
 * */
public class JmxService implements IJmxService {

	private boolean startServer;

	private String host;
	private int port;
	private int httpPort = 9797;
	private boolean runHttp = true;

	// server
	private MBeanServer mbeanServer;
	private JMXConnectorServer jmxConnectorServer;

	// client
	private JMXConnector jxmConnector;
	private MBeanServerConnection beanServerConnection;

	public JmxService(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void register(Object mbean) {
		register(JxmUtli.getObjectName(mbean.getClass()), mbean);
	}

	@Override
	public void register(String name, Object mbean) {
		initServer();
		try {
			ObjectName connectorName = new ObjectName(name);
			mbeanServer.registerMBean(mbean, connectorName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initServer() {
		if (mbeanServer == null) {
			mbeanServer = MBeanServerFactory.createMBeanServer();
		}
	}

	@Override
	public void start() {
		if (startServer) {
			System.out.println("已启动JMX :" + port);
			return;
		}
		this.startServer = true;
		initServer();
		try {

			// 启动server
			LocateRegistry.createRegistry(port);
			final JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi://localhost/jndi/rmi://" + host + ":"
							+ port + "/jmxrmi");
			jmxConnectorServer = JMXConnectorServerFactory
					.newJMXConnectorServer(url, null, mbeanServer);
			jmxConnectorServer.start();

			// //创建适配器，用于能够通过浏览器访问MBean
			if (runHttp) {
				Class<?> clz = Class
						.forName("com.sun.jdmk.comm.HtmlAdaptorServer");
				Object htmlAdaptorServer = clz.newInstance();
 				Method method = ReflectionUtils.getDeclaredMethod(clz,"setPort",int.class);
 				Method startMethod = ReflectionUtils.getDeclaredMethod(clz,"start");
 				method.invoke(htmlAdaptorServer, httpPort);
				register(htmlAdaptorServer);
				startMethod.invoke(htmlAdaptorServer); 
			}
			// HtmlAdaptorServer adapter = new HtmlAdaptorServer();
			// adapter.setPort(httpPort);
			// register(adapter);
			// adapter.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void asyncStart() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				start();
			}
		});

		thread.setDaemon(true);
		thread.start();
	}

	@Override
	public void connect() {
		try {
			JMXServiceURL jmxServiceUrl = new JMXServiceURL(
					"service:jmx:rmi://localhost/jndi/rmi://" + host + ":"
							+ port + "/jmxrmi");
			JMXConnector connector = JMXConnectorFactory.connect(jmxServiceUrl);
			beanServerConnection = connector.getMBeanServerConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T> T getRpcProxy(Class<T> clz) {
		return getRpcProxy(clz, clz);
	}

	@Override
	public <T> T getRpcProxy(Class<T> clz, Class<? extends T> impClz) {
		return getRpcProxy(JxmUtli.getObjectName(impClz), impClz);
	}

	@Override
	public <T> T getRpcProxy(String name, Class<? extends T> clz) {
		try {
			ObjectName hwName = new ObjectName(name);
			return JMX.newMXBeanProxy(beanServerConnection, hwName, clz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void close() {
		if (jmxConnectorServer != null) {
			try {
				jmxConnectorServer.stop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (jxmConnector != null) {
			try {
				jxmConnector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Set<ObjectName> getAllRegisterMBean() {
		try {
			ObjectName filterName = new ObjectName("*:*");
			Set<ObjectName> objectNames = beanServerConnection.queryNames(
					filterName, null);
			return objectNames;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// getter
	public int getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}

	public boolean isStartServer() {
		return startServer;
	}

	public MBeanServer getMbeanServer() {
		return mbeanServer;
	}

	public JMXConnectorServer getJmxConnectorServer() {
		return jmxConnectorServer;
	}

	public JMXConnector getJxmConnector() {
		return jxmConnector;
	}

	public MBeanServerConnection getBeanServerConnection() {
		return beanServerConnection;
	}

	public boolean isRunHttp() {
		return runHttp;
	}

	public void setRunHttp(boolean runHttp) {
		this.runHttp = runHttp;
	}

}
