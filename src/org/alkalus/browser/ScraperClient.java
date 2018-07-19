package org.alkalus.browser;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

public class ScraperClient {

	private static final WebClient mInternalClient;
	
	static {
		mInternalClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
	}

	public ScraperClient() {
		
	}
	
	public static synchronized final WebClient getBrowser() {
		return mInternalClient;
	}
	
}
