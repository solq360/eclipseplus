package testplus2.game.model;

import javax.management.MXBean;
@MXBean
public interface JmxStopMBean {
	public static final String NAME= "jmx.eyu.model:type=StopServer";
	public static final int PORT= 6978;
	public void stop();
}

