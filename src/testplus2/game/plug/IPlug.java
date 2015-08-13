package testplus2.game.plug;

import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.console.IConsole;

/***
 * @author solq
 * */
public interface IPlug {

	public IProject findProject(String name);

	public IProject[] allProject();

	public ILaunch findLaunch(String name);

	public ILaunch[] allLaunch();

	public ILaunchConfiguration findLaunchConfiguration(String name);

	public ILaunchConfiguration[] allLaunchConfiguration();

	public IConsole findConsole(String name);

	public IConsole[] allConsole();

	public void consoleWriteMessage(String name, String message);

	public void startApplication(String name);

	public void stopApplication(String name);

	public void stopNowApplication(String name);

	public ApplicationState getApplicationState(String name);

}
