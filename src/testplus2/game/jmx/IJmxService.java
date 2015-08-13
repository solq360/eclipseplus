package testplus2.game.jmx;

import java.util.Set;

import javax.management.ObjectName;
/**
 * jmx 服务接口 服务端与客户端
 * 
 * @author solq
 * */
public interface IJmxService {
	public void close();

	// /////////服务端部分//////////////
	
	/**
	 * 注册MBEAN
	 * */
	public void register(Object mbean);
	/**
	 * 注册MBEAN
	 * */
	public void register(String name,Object mbean);
	
	/**
	 * 启动jxm服务端
	 * */
	public void start();
	
	/**
	 * 异步启动jxm服务端
	 * */
	public void asyncStart();

	// /////////客户端部分//////////////
	/**
	 * 连接jxm服务端
	 * */
	public void connect();

	/**
	 * 获取mbean proxy
	 * @param clz 注意是接口
	 * */
	public <T> T getRpcProxy(Class<T> clz);
	/**
	 * 获取mbean proxy
	 * @param clz 注意是接口
	 * @param impClz 实现类
	 * */
	public <T> T getRpcProxy(Class<T> clz, Class<? extends T> impClz);
	
	/**
	 * 获取mbean proxy
	 * @param name 注册名称
	 * @param clz 注意是接口
	 * */
	public <T> T getRpcProxy(String name, Class<? extends T> clz);
	/**
	 * 获取jxm服务端所有注册mbean 
	 * */
	public Set<ObjectName> getAllRegisterMBean();
}
