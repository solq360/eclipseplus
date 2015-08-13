package testplus2.game.plug;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.ui.views.console.ProcessConsole;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import testplus2.game.jmx.JmxService;
import testplus2.game.model.JmxStopMBean;

/**
 * ����eclipse plug api ʵ��
 * 
 * @author solq
 * */
@SuppressWarnings("restriction")
public class Plug implements IPlug {
	private static final boolean DEBUG = false;

	@Override
	public IProject findProject(String name) {
		IProject[] projects = allProject();
		for (IProject project : projects) {
			if (project.getName().equals(name)) {
				return project;
			}
		}
		debug("δ�ҵ���Ŀ [" + name + "]");

		return null;
	}

	@Override
	public IProject[] allProject() {
		// ȡ�ù�������root
		IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
		// ȡ����Ŀ
		IProject[] projects = wsroot.getProjects();
		return projects;
	}

	@Override
	public ILaunch findLaunch(String name) {
		ILaunch[] launchs = allLaunch();
		for (ILaunch launch : launchs) {
			if (launch.getLaunchConfiguration().toString().equals(name)) {
				return launch;
			}
		}
		debug("δ�ҵ�����Ӧ�� [" + name + "]");

		return null;
	}

	@Override
	public ILaunch[] allLaunch() {
 		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
 		ILaunch[] launchs = launchManager.getLaunches();
		return launchs;
	}

	@Override
	public ILaunchConfiguration findLaunchConfiguration(String name) {
		ILaunchConfiguration[] launchConfigurations = allLaunchConfiguration();
		for (ILaunchConfiguration launchConfiguration : launchConfigurations) {
			if (launchConfiguration.getName().indexOf(name) == 0) {
				return launchConfiguration;
			}
		}
		debug("δ�ҵ�Ӧ������[" + name + "]");
		return null;
	}

	@Override
	public ILaunchConfiguration[] allLaunchConfiguration() {
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		try {
			return launchManager.getLaunchConfigurations();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IConsole findConsole(String name) {
		IConsole[] consoles = allConsole();
		for (IConsole console : consoles) {
			if (console.getName().indexOf(name) == 0) {
				return console;
			}
		}
		debug("δ�ҵ�����̨ [" + name + "]");
		return null;
	}

	@Override
	public IConsole[] allConsole() {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] consoles = conMan.getConsoles();
		return consoles;
	}

	@Override
	public void consoleWriteMessage(String name, String message) {
		IConsole console = findConsole(name);
		if (console == null) {
			log("δ�ҵ�����̨ [" + name + "]");
			return;
		}

		if (console instanceof MessageConsole) {
			MessageConsoleStream consoleStream = ((MessageConsole) console)
					.newMessageStream();
 			consoleStream.setActivateOnWrite(true);
			consoleStream.print(message+"\n");
			try {
				consoleStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (console instanceof ProcessConsole) {
			ProcessConsole processConsole = (ProcessConsole) console;
			IOConsoleOutputStream cos = processConsole.newOutputStream();
			cos.setActivateOnWrite(true);
			try {
				cos.write(message + "\n");
				cos.flush();
				// �ڲ����û�ر�
				cos.close();
				// processConsole.getProcess().getStreamsProxy().write("stop\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized void startApplication(String name) {
		ILaunch launch = findLaunch(name);
		if (launch != null) {
			log("Ӧ�ó��� [" + name + "] ������");
			return;
		}

		ILaunchConfiguration launchConfiguration = findLaunchConfiguration(name);
		if (launchConfiguration == null) {
			log("δ�ҵ�Ӧ���������� [" + name + "]");
			return;
		}
		DebugUITools.launch(launchConfiguration, ILaunchManager.RUN_MODE);
	}

	@Override
	public synchronized void stopApplication(String name) {
		ILaunch launch = findLaunch(name);
		if (launch == null) {
			log("Ӧ�ó��� [" + name + "] �ѹر�");
			return;
		}
		
		JmxService jmxService = new JmxService("localhost", JmxStopMBean.PORT);
		jmxService.connect();
		JmxStopMBean proxy = jmxService.getRpcProxy(JmxStopMBean.NAME,
				JmxStopMBean.class);
		try {
			proxy.stop();
		} catch (Exception e) {
			 
		}
		
		jmxService.close();
	}

	@Override
	public synchronized void stopNowApplication(String name) {
		ILaunch launch = findLaunch(name);
		if (launch == null) {
			log("Ӧ�ó��� [" + name + "] �ѹر�");
			return;
		}
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		launchManager.removeLaunch(launch);
	}

	@Override
	public ApplicationState getApplicationState(String name) {
		if(findLaunchConfiguration(name)==null){
			return ApplicationState.NOT;
		}
		if(findLaunch(name)==null){
			return ApplicationState.STOP;
		}
		
		return ApplicationState.RUN;
	}

	private void log(String string) {
		System.err.println(string);
	}

	private void debug(String string) {
		if (!DEBUG) {
			return;
		}
		System.err.println(string);
	}
}
