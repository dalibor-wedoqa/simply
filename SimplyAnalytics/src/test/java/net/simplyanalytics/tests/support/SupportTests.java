package net.simplyanalytics.tests.support;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.WelcomeDialogTitle;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.TestBrowser;
import net.simplyanalytics.core.TestMode;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.support.DataDocumentationPage;
import net.simplyanalytics.pageobjects.pages.support.ReplayTutorialPage;
import net.simplyanalytics.pageobjects.pages.support.SupportHelpCenterPage;
import net.simplyanalytics.pageobjects.sections.header.Header.SupportDropdown;
import net.simplyanalytics.pageobjects.windows.ContactSupportWindow;
import net.simplyanalytics.pageobjects.windows.IntroToSimplyAnalyticsWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Cookie;

public class SupportTests extends TestBase {
  
  private NewProjectLocationWindow createNewProjectWindow;
  private MapPage mapPage;
  //private Location searchFor = Location.CONGRESS_DIST_CD07_IL;
  private Location searchFor = Location.COUNTY_CUYAHOGA_COUNTY_OH;

  private String originalHandle;

  
  /**
   * Signing in.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();  
  }
  
  @Test
  public void testSupportHelpCenter() {
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);
    mapPage.getActiveView().waitForFullLoad();
    
    SupportDropdown support = mapPage.getHeaderSection().clickSupport();
    originalHandle = driver.getWindowHandle();
    SupportHelpCenterPage supportPage = support.clickToOpenSupportHelpCenter();

    String title = supportPage.getSupportHelpCenterTitle();

    verificationStep("Verify that the title of the page is SIMPLYANALYTICS HELP CENTER");
    Assert.assertEquals("The title is not SIMPLYANALYTICS HELP CENTER",
        "SIMPLYANALYTICS HELP CENTER", title);
  }
  
  @Test
  public void testContactSupport() {
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);
    mapPage.getActiveView().waitForFullLoad();
    
    SupportDropdown support = mapPage.getHeaderSection().clickSupport();
    ContactSupportWindow supportPage = support.clickToOpenContactSupport();
    String title = supportPage.getContactSupportTitle();
    
    verificationStep("Verify that the title of the form is Contact Support");
    Assert.assertEquals("The title is not Contact Support", "Contact Support", title);

    supportPage.clickCloseButton();    
  }
  
  @Test
  public void testDataDocumentation() {
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);
    mapPage.getActiveView().waitForFullLoad();
    
    SupportDropdown support = mapPage.getHeaderSection().clickSupport();
    DataDocumentationPage supportPage = support.clickToOpenDataDocumentation();
    String title = supportPage.getDataDocumentationTitle();
    
    verificationStep("Verify that the title of the page is Data Documentation");
    Assert.assertEquals("The title is not Data Documentation", "Data Documentation", title);
    
  }
  
  @Test
  public void testReplayTutorial() {
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);
    mapPage.getActiveView().waitForFullLoad();
    
    SupportDropdown support = mapPage.getHeaderSection().clickSupport();
    ReplayTutorialPage supportPage = support.clickToOpenReplayTutorial();
    String title = supportPage.getReplayTutorialTitle();
    
    verificationStep("Verify that the title is Here’s a quick tour of SimplyAnalytics.");
    Assert.assertEquals("The title is not Here’s a quick tour of SimplyAnalytics.",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2, title);
    
  }

  @Test
  public void testDownloadTrainingGuide() {
    System.out.println("Starting test: testDownloadTrainingGuide");

    // Step 1: Create new project with default variables and location
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);
    System.out.println("Created new project. Active view: " + mapPage.getActiveView());

    // Step 2: Wait for full view load
    mapPage.getActiveView().waitForFullLoad();
    System.out.println("Map view fully loaded.");

    // Step 3: Click Support dropdown
    SupportDropdown support = mapPage.getHeaderSection().clickSupport();
    System.out.println("Clicked on Support dropdown.");

    // Step 4: Get cookies and original window handle
    Set<Cookie> cookies = driver.manage().getCookies();
    originalHandle = driver.getWindowHandle();
    System.out.println("Captured original window handle: " + originalHandle);

    // Step 5: Find SA_SESSION cookie
    Cookie sessionCookie = null;
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("SA_SESSION")) {
        sessionCookie = cookie;
        System.out.println("Found SA_SESSION cookie: " + sessionCookie);
      }
    }

    // Step 6: Click to download the training guide
    support.clickToDownloadTrainingGuide();
    System.out.println("Clicked 'Download Training Guide' from support dropdown.");

    sleep(500); // give time for new tab to load
    System.out.println("Waited 500ms for new tab to potentially open.");

// Step 7: Switch to the newly opened tab (if any)
    Set<String> handles = driver.getWindowHandles();
    System.out.println("Window handles: " + handles);

    Assert.assertTrue("No new tab was opened", handles.size() > 1);

    for (String handle : handles) {
      if (!handle.equals(originalHandle)) {
        try {
          System.out.println("Attempting to switch to child tab: " + handle);
          driver.switchTo().window(handle);

          String currentUrl = driver.getCurrentUrl();
          System.out.println("Current URL in child tab: " + currentUrl);

          if (!currentUrl.startsWith("chrome-extension:")) {
            verificationStep("Verify that the link response is 200");
            System.out.println("Verifying response from URL: " + currentUrl);

            Assert.assertTrue("Unable to open url: " + currentUrl, linkResponse(currentUrl, sessionCookie));
            System.out.println("Verified response 200 OK from: " + currentUrl);
          }

          switchBackTab();
          System.out.println("Switched back to original tab and closed the child tab.");
          break; // success, break out

        } catch (Exception e) {
          System.out.println("Failed to switch to window: " + handle + ". Error: " + e.getMessage());
          // move on to next handle
        }
      }
    }


    System.out.println("Test finished: testDownloadTrainingGuide");
  }

  
  // https://atlassian.simplymap.net:9080/browse/SA-1074
  @Test
  public void testIntroToSimplyAnalytics() {
    System.out.println("Creating a new project with the specified location and default variables.");
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(searchFor);

    System.out.println("Waiting for the map page's active view to fully load.");
    mapPage.getActiveView().waitForFullLoad();

    System.out.println("Clicking the support dropdown from the header section.");
    SupportDropdown support = mapPage.getHeaderSection().clickSupport();

    System.out.println("Opening the 'Intro to Simply Analytics' window.");
    IntroToSimplyAnalyticsWindow supportPage = support.clickToOpenIntroToSimplyAnalytics();

    if (driverConfiguration.getBrowser() == TestBrowser.firefox.name()) {
      System.out.println("Detected browser: Firefox. Waiting for the video to start playing.");

      sleep(1000);

      System.out.println("Verifying that the video is playing by checking the progress bar value.");
      double value = Double.parseDouble(supportPage.getPlayerProgressBarValue());
      System.out.println("Progress bar value: " + value);

      Assert.assertTrue("The video is not playing", value > 0);

      System.out.println("Closing the support page.");
      supportPage.clickClose();
    } else {
      System.out.println("Detected browser: Not Firefox. Clicking the play button.");
      supportPage.clickPlayButton();

      System.out.println("Waiting for the video to start playing.");
      sleep(2000);

      System.out.println("Verifying that the video is playing by checking the progress bar value.");
      double value = Double.parseDouble(supportPage.getPlayerProgressBarValue());
      System.out.println("Progress bar value: " + value);

      Assert.assertTrue("The video is not playing", value > 0);

      System.out.println("Closing the support page.");
      supportPage.clickClose();
    }
  }


  private static boolean linkResponse(String url, Cookie cookie) {
    try {
      HttpURLConnection.setFollowRedirects(false);
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("HEAD");
      connection.setRequestProperty("Cookie", cookie.toString());
      connection.connect();
      
      return (connection.getResponseCode() == HttpURLConnection.HTTP_OK);
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
  @After
  public void switchBackTab() {
    if(driver.getWindowHandles().size() >= 2) {
      for(String handle : driver.getWindowHandles()) {
        if (!handle.equals(originalHandle)) {
            driver.switchTo().window(handle);
            driver.close();
        }
      }
      driver.switchTo().window(originalHandle);
    }
  }
}
