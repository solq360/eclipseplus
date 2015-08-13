package testplus2.game.model;

import java.util.List;

import javax.management.MXBean;

/**
 * 
 * 通过jmx通信来控制 eclipse plus <br>
 * 游戏控制插件
 * 
 * @author solq
 * */
@MXBean
public interface GamePlugMBean {

	/***
	 * 获取控制台所有消息
	 * 
	 * @param debugName
	 *            调试程序名称
	 * */
	public List<String> getAllConsoleMessage(String debugName);

	/***
	 * 启动调试程序(返回执行结果，查看 getConsoleMessages )
	 * 
	 * @param debugName
	 *            调试程序名称
	 * 
	 * */
	public void start(String debugName);

	/***
	 * 关闭调试程序(返回执行结果，查看 getConsoleMessages )
	 * 
	 * @param debugName
	 *            调试程序名称
	 * 
	 * */
	public void stop(String debugName);
	
	/***
	 * 马上关闭调试程序(返回执行结果，查看 getConsoleMessages )
	 * 
	 * @param debugName
	 *            调试程序名称
	 * 
	 * */
	public void stopNow(String debugName);

	/**
	 * 获取所有调试配置名称
	 * */
	public String[] getAllLaunchConfiguration();

	/**
	 * 获取所有已执行调试配置名称
	 * */
	public String[] getAllLaunch();
}
