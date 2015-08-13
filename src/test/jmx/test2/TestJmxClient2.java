package test.jmx.test2;

import testplus2.game.jmx.JmxService;
import testplus2.game.model.JmxStopMBean;

/***
 * ²âÊÔ²»Í¬°ü....
 * */
public class TestJmxClient2 {

 
	public static void main(String args[]) throws Exception {
		JmxService jmxService = new JmxService("localhost", JmxStopMBean.PORT);
		jmxService.connect();
		JmxStopMBean proxy = jmxService.getRpcProxy(JmxStopMBean.NAME,
				JmxStopMBean.class);
		System.out.println("start ================================");
		try {
			proxy.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		jmxService.close();
		
		System.out.println("end ===============");
	}

}