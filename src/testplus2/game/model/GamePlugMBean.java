package testplus2.game.model;

import java.util.List;

import javax.management.MXBean;

/**
 * 
 * ͨ��jmxͨ�������� eclipse plus <br>
 * ��Ϸ���Ʋ��
 * 
 * @author solq
 * */
@MXBean
public interface GamePlugMBean {

	/***
	 * ��ȡ����̨������Ϣ
	 * 
	 * @param debugName
	 *            ���Գ�������
	 * */
	public List<String> getAllConsoleMessage(String debugName);

	/***
	 * �������Գ���(����ִ�н�����鿴 getConsoleMessages )
	 * 
	 * @param debugName
	 *            ���Գ�������
	 * 
	 * */
	public void start(String debugName);

	/***
	 * �رյ��Գ���(����ִ�н�����鿴 getConsoleMessages )
	 * 
	 * @param debugName
	 *            ���Գ�������
	 * 
	 * */
	public void stop(String debugName);
	
	/***
	 * ���Ϲرյ��Գ���(����ִ�н�����鿴 getConsoleMessages )
	 * 
	 * @param debugName
	 *            ���Գ�������
	 * 
	 * */
	public void stopNow(String debugName);

	/**
	 * ��ȡ���е�����������
	 * */
	public String[] getAllLaunchConfiguration();

	/**
	 * ��ȡ������ִ�е�����������
	 * */
	public String[] getAllLaunch();
}
