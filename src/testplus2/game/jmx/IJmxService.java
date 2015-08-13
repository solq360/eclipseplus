package testplus2.game.jmx;

import java.util.Set;

import javax.management.ObjectName;
/**
 * jmx ����ӿ� �������ͻ���
 * 
 * @author solq
 * */
public interface IJmxService {
	public void close();

	// /////////����˲���//////////////
	
	/**
	 * ע��MBEAN
	 * */
	public void register(Object mbean);
	/**
	 * ע��MBEAN
	 * */
	public void register(String name,Object mbean);
	
	/**
	 * ����jxm�����
	 * */
	public void start();
	
	/**
	 * �첽����jxm�����
	 * */
	public void asyncStart();

	// /////////�ͻ��˲���//////////////
	/**
	 * ����jxm�����
	 * */
	public void connect();

	/**
	 * ��ȡmbean proxy
	 * @param clz ע���ǽӿ�
	 * */
	public <T> T getRpcProxy(Class<T> clz);
	/**
	 * ��ȡmbean proxy
	 * @param clz ע���ǽӿ�
	 * @param impClz ʵ����
	 * */
	public <T> T getRpcProxy(Class<T> clz, Class<? extends T> impClz);
	
	/**
	 * ��ȡmbean proxy
	 * @param name ע������
	 * @param clz ע���ǽӿ�
	 * */
	public <T> T getRpcProxy(String name, Class<? extends T> clz);
	/**
	 * ��ȡjxm���������ע��mbean 
	 * */
	public Set<ObjectName> getAllRegisterMBean();
}
