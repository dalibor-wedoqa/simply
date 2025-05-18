package net.simplyanalytics.core;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * . Test run configuration
 * 
 * @author WeDoQA
 *
 */
public class DriverConfiguration {
  
  /**
   * .
   * 
   */
  private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
  
  protected String browser;
  protected TestMode mode;
  protected boolean headless;
  
  /**
   * . Class constructor which sets mode and browser taken from system properties
   */
  public DriverConfiguration() {
    this(
        getBrowserType(System.getProperty("browser")),
        getTestMode(System.getProperty("mode")),
        getHeadless(System.getProperty("headless")));
  }
  
  /**
   * . Class constructor which takes browser and mode as parameters
   */
  public DriverConfiguration(String browser, TestMode mode, boolean headless) {
    this.browser = browser;
    this.mode = mode;
    this.headless = headless;
    
    logger.debug("Test mode: " + mode);
    logger.debug("Browser: " + browser);
    logger.debug("Headless: " + headless);
    
  }
  
  public TestMode getTestMode() {
    return mode;
  }
  
  private static TestMode getTestMode(String testMode) {
    TestMode result = null;
    
    if (testMode == null || testMode.isEmpty()) {
      logger.info("Test mode was null. Setting LOCAL mode.");
      return TestMode.LOCAL;
    }
    
    switch (testMode) {
      case "REMOTE":
        result = TestMode.REMOTE;
        break;
      case "LOCAL":
        result = TestMode.LOCAL;
        break;
      default:
        break;
    }
    
    logger.info("Test mode set to: " + result.toString());
    return result;
  }
  
  public String getBrowser() {
    return browser;
  }
  
  private static String getBrowserType(String testBrowser) {
	  TestBrowser result = null;
	  
	    if (testBrowser == null || testBrowser.isEmpty()) {  
	    logger.info("Test browser was null. Setting CHROME mode.");
	    result = TestBrowser.chrome;
	    return result.name().toString();
	    }
	    
	    switch (testBrowser) {
	    case "chrome":
	    	result = TestBrowser.chrome;
	    	break;
	    case "edge":
	    	result = TestBrowser.edge;
	    	break;
	    case "firefox":
	    	result = TestBrowser.firefox;
	    	break;
	    default:
	    	break;
	    }
	    
	    logger.info("Test browser set to: " + result.toString());
	    return result.name().toString();
  }
  
  public boolean isHeadless() {
    return headless;
  }
  
  private static boolean getHeadless(String headless) {
    if (headless == null || headless.isEmpty()) {
      return false;
    }
    try {
      return Boolean.parseBoolean(headless);
    } catch (Throwable e) {
      return false;
    }
  }
  
}