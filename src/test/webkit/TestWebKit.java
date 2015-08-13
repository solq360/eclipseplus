package test.webkit;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class TestWebKit {
	public static void main(String[] args) throws PartInitException, MalformedURLException {
		PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL("http://www.baidu.com/"));

	 
	}
}

