package testplus2.actions;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.console.IConsole;

import testplus2.game.jmx.JmxService;
import testplus2.game.model.GamePlug;
import testplus2.game.plug.Plug;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	private Plug plug;
	private JmxService jmxService;

	/**
	 * The constructor.
	 */
	public SampleAction() {
		testJmx();
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
//		testJmx();
//		testPlug();
//		MessageDialog.openInformation(window.getShell(), "TestPlus2", "Hello");
	}

	void testJmx() {

		if (jmxService != null) {
			return;
		}
		jmxService = new JmxService("localhost", 8855);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				if (jmxService != null) {
					jmxService.close();
				}
			}
		}));		
		Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// ×¢²ámbean ÊµÀý
				GamePlug gamePlus = new GamePlug();
				jmxService.register(gamePlus);
				jmxService.start();
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	void testPlug() {
		// MyConsole.send();
		if (plug == null) {
			plug = new Plug();
			System.out.println(" created : plug");
		}
		System.out.println("==========================");

		String application = "Main";
		IConsole console = plug.findConsole(application);
	  
 		    
		// plug.findLaunch(application);
		// plug.findLaunchConfiguration(application);
		// plug.startApplication(application);
		// plug.stopApplication(application);
		//VMRunnerConfiguration vmRunnerConfiguration;
		 
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}