package testplus2;

import org.eclipse.ui.IStartup;

import testplus2.game.jmx.JmxService;
import testplus2.game.model.GamePlug;

public class ServiceStartup implements IStartup {

	protected JmxService jmxService;

	@Override
	public void earlyStartup() {
		if (jmxService != null) {
			return;
		}
		System.out.println("Æô¶¯ jxm");

		jmxService = new JmxService("localhost", 8855);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				if (jmxService != null) {
					jmxService.close();
				}
			}
		}));
		Thread thread = new Thread(new Runnable() {

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

}