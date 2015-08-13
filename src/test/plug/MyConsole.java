package test.plug;

import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchMode;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.internal.core.LaunchConfiguration;
import org.eclipse.debug.internal.core.LaunchManager;
import org.eclipse.debug.internal.ui.views.console.ProcessConsole;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class MyConsole {

	static void jmx() {
		Properties props = new Properties();
		// props.put("type", AdminClient.CONNECTOR_TYPE_SOAP);
		props.put("host", "localhost");
		props.put("port", "8879");
	}

	@SuppressWarnings("restriction")
	public static void send() {
		findProject();
		findLaunch();

		IConsole console = findConsole("Main");
		if (console == null) {
			return;
		}
		if (console instanceof MessageConsole) {
			MessageConsoleStream consoleStream = ((MessageConsole) console)
					.newMessageStream();

			consoleStream.setActivateOnWrite(true);
			consoleStream.print("stop\n");
			try {
				consoleStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (console instanceof ProcessConsole) {
			ProcessConsole processConsole = (ProcessConsole) console;
			IOConsoleOutputStream cos = processConsole.newOutputStream();
			try {
				cos.write("stop\n");
				cos.flush();
				// �ڲ����û�ر�
				cos.close();
				// processConsole.getProcess().getStreamsProxy().write("stop\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	static void findProject() {
		// ȡ�ù�������root
		IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
		// ȡ����Ŀ
		IProject[] projects = wsroot.getProjects();

		for (IProject project : projects) {
			System.out.println("project : " + project.getName());
		}
	}

	static void findLaunch() {
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		ILaunch[] launchs = launchManager.getLaunches();

		// LaunchMode
		for (ILaunchMode launchMode : launchManager.getLaunchModes()) {
			System.out.println("launchMode : " + launchMode.getLabel());
		}

		for (IDebugTarget debugTarget : launchManager.getDebugTargets()) {
			try {
				System.out.println("debugTarget : " + debugTarget.getName());
			} catch (DebugException e) {
				e.printStackTrace();
			}
		}

		// ���û�������
		for (ILaunchConfigurationType launchConfigurationType : launchManager
				.getLaunchConfigurationTypes()) {
			System.out.println("launchConfigurationType : "
					+ launchConfigurationType.getName());
		}

		// ����debug ����
		try {
			for (ILaunchConfiguration launchConfiguration : launchManager
					.getLaunchConfigurations()) {
				String name = launchConfiguration.getName();
				System.out.println("launchConfiguration : "
						+ launchConfiguration.getName());
				if (name.indexOf("Main") == 0) {
					DebugUITools.launch(launchConfiguration,
							ILaunchManager.RUN_MODE);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		// ��ִ�� Launch
		for (ILaunch launch : launchs) {
			System.out.println(" launch : "
					+ launch.getLaunchConfiguration().toString());
		}
	}

	static void println(MessageConsoleStream consoleStream, String msg) {
		consoleStream.setActivateOnWrite(true);
		consoleStream.print(msg);
	}

	static IConsole findConsole(String name) {

		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++) {
			System.out.println("console : " + existing[i].getName() + "\n");
			if (existing[i].getName().indexOf(name) == 0) {
				return existing[i];
			}
		}
		System.err.println("can't find console : " + name);
		return null;
		// MessageConsole newConsole = new MessageConsole(name, null);
		// conMan.addConsoles(new IConsole[] { newConsole });
		// conMan.showConsoleView(newConsole);
		// MessageConsoleStream consoleStream = newConsole.newMessageStream();
		// println(consoleStream, "console length : " +existing.length + "\n");
		// return newConsole;
	}

	// //���Debug��ͼ�е�lauch����
	// ILaunch[] launchs =
	// DebugPlugin.getDefault().getLaunchManager().getLaunches();
	// DebugPlugin.getDefault().getLaunchManager().removeLaunch(templaunch);
	// //�Ƴ�ĳ��Launch
	//
	// //������еĶϵ�
	// IBreakpoint[] breakpoints =
	// DebugPlugin.getDefault().getBreakpointManager().getBreakpoints();
	// for (int i = 0; i < breakpoints.length; i++) {
	// System.out.println("breakpoint :   " + breakpoints[i]);
	// }
	// //������е�Console,�����Console�����Ƽ�ִ�н��
	// IConsole[] consoles =
	// ConsolePlugin.getDefault().getConsoleManager().getConsoles();
	// System.out.println("Console ���ȣ�" + consoles.length);
	// for(IConsole con: consoles){
	// if(con instanceof ProcessConsole){
	// ProcessConsole proc = ((ProcessConsole)con);
	// System.out.println( "��Ϣ��" + proc.getName() + " :" +
	// proc.getProcess().getStreamsProxy().getOutputStreamMonitor().getContents());
	// System.out.println("���: " +proc.getDocument().get());//��õ�����̨���������
	// }
	// }
	// //��õ�ǰ����ı༭��
	// IEditorPart editor =
	// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	//
	// //Launch ���Ƴ�process
	// IProcess[] process = launch.getProcesses();
	// System.out.println("process����:" + process.length);
	// for(IProcess pro:process){
	// launch.removeProcess(pro);
	// }
	// //Launch ���Ƴ�DebugTarget
	// IDebugTarget[] iDebugTargets = launch.getDebugTargets();
	// System.out.println("IDebugTarget����:" + iDebugTargets.length);
	// for(IDebugTarget iTarget:iDebugTargets){
	// launch.removeDebugTarget(iTarget);
	// }
}
