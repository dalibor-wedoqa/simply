package net.simplyanalytics.tests.support;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Cookie;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.parallel.ConcurrentParameterizedTestRunner;
import net.simplyanalytics.enums.DataDocumentationLinkEnum;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.support.DataDocumentationPage;
import net.simplyanalytics.pageobjects.sections.header.Header.SupportDropdown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.HttpUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

@RunWith(ConcurrentParameterizedTestRunner.class)
public class DataDocumentationTests extends TestBase {

  private NewProjectLocationWindow createNewProjectWindow;
  private String category;
  private NewViewPage newViewPage;

  private String originalHandle;

  /**
   * .
   * @return
   */
  @Parameters(name = "{index}: category {0}")
  public static List<String> category() {
    return DataDocumentationLinkEnum.getAllCategories();
  }

  public DataDocumentationTests(String category) {
    this.category = category;
  }

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
    newViewPage = createNewProjectWindow.clickClose();
  }

  @Test
  public void testClickDataDocumentationLinks() throws Exception {
    System.out.println("Starting test: testClickDataDocumentationLinks");

    SupportDropdown support = newViewPage.getHeaderSection().clickSupport();
    System.out.println("Clicked on Support dropdown.");

    DataDocumentationPage documentationPage = support.clickToOpenDataDocumentation();
    System.out.println("Opened Data Documentation page.");

    String title = documentationPage.getDataDocumentationTitle();
    System.out.println("Fetched page title: " + title);

    verificationStep("Verify that the title of the page is Data Documentation");
    Assert.assertEquals("The title is not Data Documentation", "Data Documentation", title);

    System.out.println("Verified page title successfully.");

    for (DataDocumentationLinkEnum link : DataDocumentationLinkEnum.getLinksByCategory(category)) {
      System.out.println("Processing link: " + link.getName());

      Set<Cookie> cookies = driver.manage().getCookies();
      Cookie sessionCookie = null;
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("SA_SESSION")) {
          sessionCookie = cookie;
          System.out.println("Session cookie found: " + sessionCookie);
          break;
        }
      }

      originalHandle = driver.getWindowHandle();
      System.out.println("Original window handle: " + originalHandle);

      String currentUrl = documentationPage.clickOnTheLink(link);
      System.out.println("Clicked link, got URL: " + currentUrl);

      if (!currentUrl.contains(".pptx")) {
        System.out.println("Detected non-PPTX link, checking for new tabs...");

        for (String handler : driver.getWindowHandles()) {
          if (!originalHandle.equals(handler)) {
            System.out.println("Switching to new tab: " + handler);
            driver.switchTo().window(handler);

          }
        }

        verificationStep("Verify that the link response is 200");
        boolean responseValid = linkResponse(currentUrl, sessionCookie);
        Assert.assertTrue("Unable to open url: " + currentUrl, responseValid);
        System.out.println("Link response validation: " + responseValid);

        // Close the new tab and return to original
//        System.out.println("Getting window handles");
//        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
//        int handleCount = windowHandles.size();
//        List<String> tabURLs = new ArrayList<>();

//        for (int i = 0; i < handleCount; i++) {
//          String handle = windowHandles.get(i);
//          driver.switchTo().window(handle);
//          String pageURL = driver.getCurrentUrl();
//          if (!handle.equals(originalHandle)&&!pageURL.startsWith("chrome")) {
//            System.out.println("Closing tab:" + pageURL);
//            Actions actions = new Actions(driver);
//            actions.sendKeys(Keys.CONTROL, "w").perform();
////            driver.close();
//          }
//        }
        driver.switchTo().window(originalHandle);
        System.out.println("Switched back to original window.");

      } else {
        System.out.println("Detected PPTX file, verifying link response...");
        verificationStep("Verify that the link response is 200");
        boolean responseValid = linkResponse(currentUrl, sessionCookie);
        Assert.assertTrue("Unable to open url: " + currentUrl, responseValid);
        System.out.println("PPTX link response validation: " + responseValid);
      }
    }

    System.out.println("Test testClickDataDocumentationLinks completed successfully.");
  }

  private static boolean linkResponse(String url, Cookie cookie) throws IOException {
    OkHttpClient client = HttpUtils.getUnsafeOkHttpClient();

    Request request = new Request.Builder()
        .url(url)
        .header("Cookie", cookie.toString())
        .build();

    Response response = client.newCall(request).execute();
    return response.isSuccessful();
  }

  @After
  public void switchBackTab() {
//    if(driver.getWindowHandles().size() >= 2) {
//      for(String handle : driver.getWindowHandles()) {
//        if (!handle.equals(originalHandle)) {
//            driver.switchTo().window(handle);
//            driver.close();
//        }
//      }
      driver.switchTo().window(originalHandle);
//    }
  }

}
