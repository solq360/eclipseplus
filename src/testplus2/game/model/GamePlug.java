package testplus2.game.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

import testplus2.game.plug.Plug;

public class GamePlug implements GamePlugMBean {

	private Plug plug = new Plug();

	@Override
	public List<String> getAllConsoleMessage(String debugName) {
		File file = new File(debugName);
		if (!file.canExecute()) {
			return null;
		}
		BufferedReader br = null;
		List<String> result = new LinkedList<>();
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
 				result.add(line+" <br/>");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void start(String debugName) {
		plug.startApplication(debugName);
	}

	@Override
	public void stop(String debugName) {
		plug.stopApplication(debugName);
	}

	@Override
	public void stopNow(String debugName) {
		plug.stopNowApplication(debugName);
	}

	@Override
	public String[] getAllLaunchConfiguration() {
		ILaunchConfiguration[] launchConfigurations = plug
				.allLaunchConfiguration();
		String[] result = new String[launchConfigurations.length];
		int i = 0;
		for (ILaunchConfiguration launchConfiguration : launchConfigurations) {
			result[i++] = launchConfiguration.getName();
		}
		return result;
	}

	@Override
	public String[] getAllLaunch() {
		ILaunch[] launchs = plug.allLaunch();
		String[] result = new String[launchs.length];
		int i = 0;
		for (ILaunch launch : launchs) {
			result[i++] = launch.getLaunchConfiguration().toString();
		}
		return result;
	}

}
