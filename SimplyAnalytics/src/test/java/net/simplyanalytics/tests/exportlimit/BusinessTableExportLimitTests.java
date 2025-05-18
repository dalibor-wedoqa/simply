package net.simplyanalytics.tests.exportlimit;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.SignUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.*;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.*;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.AdvancedBusinessSearchWindow;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesTab;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.SearchConditionRow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.ProjectManagerPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;

public class BusinessTableExportLimitTests extends TestBase {

  private BusinessesPage businessesPage;
  private MapPage mapPage;
  private ProjectManagerPage projectManager;
  private AdvancedBusinessSearchWindow advancedBusinessSearchWindow;

  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    NewProjectLocationWindow newProjectLocationWindow = signInPage.signIn(SignUser.USER3, SignUser.PASSWORD3).getHeaderSection().clickNewProject();
    mapPage = newProjectLocationWindow.createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_DMA);
    BusinessesTab businessesTab = mapPage.getLdbSection().clickBusinesses();
    advancedBusinessSearchWindow = businessesTab.clickUseAdvancedSearch();
    SearchConditionRow searchConditionRow = advancedBusinessSearchWindow.getConditionRow(0);
    searchConditionRow.selectSearchBy(BusinessSearchBy.NAICS);
    searchConditionRow.selectCondition(BusinessSearchConditions.STARTS);
    advancedBusinessSearchWindow.typeInput("721");
  }

  @Test
  public void ExportLargeBusinessTable() {
    advancedBusinessSearchWindow.clickSearch();
    businessesPage = (BusinessesPage) mapPage.getViewChooserSection().clickView(ViewType.BUSINESSES.getDefaultName());
    businessesPage.getToolbar().clickExport();

    System.out.println("Waiting for the export limit message to appear...");
    WebDriverWait wait = new WebDriverWait(driver, 20); // 20 seconds timeout
    try {
      WebElement exportLimitDiv = wait.until(ExpectedConditions.presenceOfElementLocated(
              By.xpath("//*[contains(text(), 'SimplyAnalytics users can export a maximum')]")
      ));

      System.out.println("Message appeared: " + exportLimitDiv.getText());
      Assert.assertNotNull("Export limit message appeared.", exportLimitDiv);

      WebElement parent = driver.findElements(By.xpath("//span[contains(text(), 'Export')]/..")).get(3);
      String classAttr = parent.getAttribute("class");

      boolean hasAllClasses =
              classAttr.contains("x-unselectable") && classAttr.contains("x-item-disabled");

      Assert.assertTrue("Parent does not have all required classes",
              hasAllClasses);

      System.out.println("Test Passed: The export limit message is present and the export button is disabled.");

    } catch (Exception e) {
      System.out.println("Test Failed: The export limit message did not appear in time.");
      Assert.fail("Export limit message did not appear within 20 seconds.");
    }
  }

//    @Test
//    public void MonitorAllBrowserNetworkRequests() throws InterruptedException {
//      System.out.println("Starting full browser network monitoring...");
//
//      // Start ChromeDriver
//      ChromeDriver driver = new ChromeDriver();
//      DevTools devTools = driver.getDevTools();
//      devTools.createSession();
//
//      // Enable network tracking
//      devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//
//      // Listen for network requests
//      devTools.addListener(Network.requestWillBeSent(), request -> {
//        Request req = request.getRequest();
//        System.out.println("\n------ NEW NETWORK REQUEST ------");
//        System.out.println("Request URL: " + req.getUrl());
//        System.out.println("Request Method: " + req.getMethod());
//        System.out.println("Request Headers: " + req.getHeaders());
//      });
//
//      // Listen for network responses
//      devTools.addListener(Network.responseReceived(), response -> {
//        Response resp = response.getResponse();
//        String requestId = response.getRequestId();
//
//        // Print response details
//        System.out.println("\n------ NETWORK RESPONSE ------");
//        System.out.println("Response URL: " + resp.getUrl());
//        System.out.println("Status Code: " + resp.getStatus());
//        System.out.println("Headers: " + resp.getHeaders());
//
//        // Capture and print response body
//        devTools.send(Network.getResponseBody(requestId))
//                .ifPresent(body -> {
//                  System.out.println("Response Body: " + body.getBody());
//                });
//      });
//
//      // Open a page that triggers network requests
//      driver.get("https://test.simplyanalytics.net");
//
//      // Monitor for 10 seconds
//      System.out.println("Monitoring network requests for 10 seconds...");
//      Thread.sleep(10000);
//
//      System.out.println("✅ Finished monitoring all network requests.");
//      driver.quit();
//    }
//
//
//
//@Test
//  public void MonitorAllRequestsWithRestAssured() throws InterruptedException {
//  advancedBusinessSearchWindow.clickSearch();
//  businessesPage = (BusinessesPage) mapPage.getViewChooserSection().clickView(ViewType.BUSINESSES.getDefaultName());
//  businessesPage.getToolbar().quickClickExport();
//    System.out.println("Starting full network monitoring using REST Assured...");
//
//    long startTime = System.currentTimeMillis();
//    long duration = 20 * 1000; // Monitor for 10 seconds
//
//    while (System.currentTimeMillis() - startTime < duration) {
//      try {
//
//        // Capture ALL requests, without filtering
//        Response response = given()
//                .relaxedHTTPSValidation() // Avoid SSL certificate issues
//                .when()
//                .get("https://test.simplyanalytics.net/dispatch.php?v=check&r=data%2FlocationLimits") // Example API URL
//                .then()
//                .extract()
//                .response();
//
//        // Capture request details
//        String requestUrl = response.getDetailedCookies().toString();
//        String requestMethod = response.getStatusLine();
//        String requestHeaders = response.getHeaders().toString();
//
//        // Capture response details
//        String currentResponse = response.body().asPrettyString();
//        int statusCode = response.getStatusCode();
//
//        // Print ALL request and response details
//        System.out.println("\n------ API REQUEST ------");
//        System.out.println("Request URL: " + response.getDetailedCookies());
//        System.out.println("Request Method: " + requestMethod);
//        System.out.println("Request Headers: " + requestHeaders);
//        System.out.println("Request Cookies: " + requestUrl);
//
//        System.out.println("------ API RESPONSE ------");
//        System.out.println("Status Code: " + statusCode);
//        System.out.println("Response JSON: " + currentResponse);
//
//      } catch (Exception e) {
//        System.out.println("❌ Error capturing request: " + e.getMessage());
//      }
//
//      Thread.sleep(2000); // Wait 2 seconds before checking again
//    }
//
//    System.out.println("✅ Finished monitoring all requests.");
//  }
//
//  @Test
//  public void MonitorAllAPIRequestsWithRestAssured() throws InterruptedException {
//    advancedBusinessSearchWindow.clickSearch();
//    businessesPage = (BusinessesPage) mapPage.getViewChooserSection().clickView(ViewType.BUSINESSES.getDefaultName());
//    businessesPage.getToolbar().quickClickExport();
//    System.out.println("Starting full API monitoring using REST Assured...");
//
//    String apiUrl = "https://test.simplyanalytics.net/dispatch.php?v=check&r=data%2FlocationLimits";
//    String previousResponse = null;
//    boolean hasChanged = false;
//
//    long startTime = System.currentTimeMillis();
//    long duration = 20 * 1000; // Monitor for 10 seconds
//
//    while (System.currentTimeMillis() - startTime < duration) {
//      try {
//        // Capture request and response without filtering by status code
//        Response response = given()
//                .relaxedHTTPSValidation() // Avoid SSL certificate issues
//                .when()
//                .get(apiUrl)
//                .then()
//                .extract()
//                .response();
//
//        // Capture request details
//        String requestMethod = response.getStatusLine();
//        String requestHeaders = response.getHeaders().toString();
//        String requestCookies = response.getDetailedCookies().toString();
//
//        // Capture response details
//        String currentResponse = response.body().asPrettyString();
//        int statusCode = response.getStatusCode();
//
//        // Print all request and response details
//        System.out.println("\n------ API REQUEST ------");
//        System.out.println("Request URL: " + apiUrl);
//        System.out.println("Request Method: " + requestMethod);
//        System.out.println("Request Headers: " + requestHeaders);
//        System.out.println("Request Cookies: " + requestCookies);
//
//        System.out.println("------ API RESPONSE ------");
//        System.out.println("Status Code: " + statusCode);
//        System.out.println("Response JSON: " + currentResponse);
//
//        // Compare with previous response
//        if (previousResponse != null && !previousResponse.equals(currentResponse)) {
//          System.out.println("⚠️ API response has changed!");
//          hasChanged = true;
//        }
//
//        previousResponse = currentResponse;
//      } catch (Exception e) {
//        System.out.println("❌ Error fetching API request: " + e.getMessage());
//      }
//
//      Thread.sleep(2000); // Wait 2 seconds before checking again
//    }
//
//    if (!hasChanged) {
//      System.out.println("✅ No changes detected in API response during monitoring period.");
//    } else {
//      System.out.println("❗ API response changed during monitoring.");
//    }
//
//    System.out.println("Test completed.");
//  }
//
//    @Test
//    public void CaptureNetworkRequestsUsingJS() throws InterruptedException {
//      System.out.println("Starting network request capture using JavaScript injection...");
//      advancedBusinessSearchWindow.clickSearch();
//      businessesPage = (BusinessesPage) mapPage.getViewChooserSection().clickView(ViewType.BUSINESSES.getDefaultName());
//      businessesPage.getToolbar().clickExport();
//
//      // Inject JavaScript to capture network requests
//      JavascriptExecutor js = (JavascriptExecutor) driver;
//      js.executeScript(
//              "let originalFetch = window.fetch; " +
//                      "window.fetch = function(url, options) { " +
//                      "   if (url.includes('dispatch.php?v=check&r=data%2FlocationLimits')) { " +
//                      "       console.log('Intercepted Fetch Request:', url); " +
//                      "       return originalFetch(url, options).then(response => { " +
//                      "           response.clone().json().then(data => console.log('Response JSON:', JSON.stringify(data))); " +
//                      "           return response; " +
//                      "       }); " +
//                      "   } " +
//                      "   return originalFetch(url, options); " +
//                      "}; " +
//
//                      "let originalXHR = window.XMLHttpRequest.prototype.open; " +
//                      "window.XMLHttpRequest.prototype.open = function(method, url) { " +
//                      "   if (url.includes('dispatch.php?v=check&r=data%2FlocationLimits')) { " +
//                      "       console.log('Intercepted XHR Request:', url); " +
//                      "       this.addEventListener('load', function() { " +
//                      "           console.log('XHR Response:', this.responseText); " +
//                      "       }); " +
//                      "   } " +
//                      "   originalXHR.apply(this, arguments); " +
//                      "};"
//      );
//
//      // Monitor for at least 10 seconds
//      System.out.println("Monitoring for 10 seconds...");
//      Thread.sleep(10000);
//
//      System.out.println("Test completed.");
//      driver.quit();
//    }
//


  @After
  public void after() {
    projectManager = new ProjectManagerPage(driver);
    projectManager.deleteProject("New Project");
  }
}