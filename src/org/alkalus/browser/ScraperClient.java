package org.alkalus.browser;

import java.io.IOException;

import org.alkalus.browser.ScraperClient.WebPage;
import org.alkalus.gui.MainFrame;
import org.alkalus.objects.AutoMap;
import org.alkalus.objects.Date;
import org.alkalus.objects.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ScraperClient {

	private static final WebClient mInternalClient;
	
	static {
		mInternalClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
	}

	HtmlPage mWebPage;
	
	public ScraperClient() {
		
	}
	
	public static synchronized final WebClient client() {
		return mInternalClient;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static class WebPage implements Runnable {
		
		private HtmlPage mWebPage;
		
		public WebPage(String aDomainName){
			try {
				mWebPage = client().getPage(aDomainName);
			} catch (FailingHttpStatusCodeException | IOException e) {
			}
		}
		
		public HtmlPage get() {
			if (mWebPage == null) {
				return (HtmlPage) null;
			}
			else {
				return mWebPage;
			}
		}

		@Override
		public void run() {
			
		}
		
	}
	
}
