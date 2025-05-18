package net.simplyanalytics.tests.importtest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.header.Header;
import net.simplyanalytics.pageobjects.windows.ImportTermsWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class ImportTermsWindowTests extends TestBase {
  private Header header;
  private NewProjectLocationWindow createNewProjectWindow;
  private ImportTermsWindow importTermsWindow;
  
  /**
   * Signing in, creating new project and open the comparison report page.
   */
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    header = mapPage.getHeaderSection();
    importTermsWindow = header.clickUser().clickImportWithImportTerms();
  }
  
  @Test
  public void testImportTermsWindowContent() {
    String expectWindowTitle = "Importing Your Data into SimplyAnalytics\n" + "Terms of Service";
    verificationStep("Verify that window title is the expected");
    Assert.assertTrue("Window title is not the expected", expectWindowTitle.toUpperCase().equals(importTermsWindow.getWindowTitle()));
    
    String introNote = "By importing your data into SimplyAnalytics you agree to the following terms of service.";
    verificationStep("Verify that intro note is the expected");
    Assert.assertTrue("Intro note is not the expected", introNote.equals(importTermsWindow.getIntroNote()));
    
    String firstParagraph = "Your SimplyAnalytics Account\n" + "You are responsible for safeguarding your "
        + "SimplyAnalytics login credentials. You are responsible for activity on your account, "
        + "whether or not you authorized that activity. "
        + "You should immediately notify us of any unauthorized use of your account.";
    verificationStep("Verify that first paragraph is the expected");
    Assert.assertTrue("First paragraph is not the expected", firstParagraph.equals(importTermsWindow.getFirstParagraph()));
    
    String secondParagraph = "Your Data\n" 
        + "When importing data into SimplyAnalytics you provide us with tabular data (“your data”). "
        + "You retain full ownership of your data.\n" 
        + "You can remove your data by deleting it. "
        + "However, in certain instances, some of your data may not be completely removed "
        + "(when your data is shared with someone else, for example). "
        + "We are not responsible or liable for the removal or deletion of any of your data, "
        + "or the failure to remove or delete such data.\n"
        + "You are solely responsible for your data and indicate "
        + "that you own or have the necessary rights to all of your data, "
        + "and that use of your data does not infringe, misappropriate "
        + "or violate a third party’s intellectual property rights, "
        + "or rights of publicity or privacy, or result in the violation of any applicable law or regulation.";
    verificationStep("Verify that second paragraph is the expected");
    Assert.assertTrue("Second paragraph is not the expected", secondParagraph.equals(importTermsWindow.getSecondParagraph()));
    
    String contactUsNote = "If you have any questions about these Terms, please contact us at "
        + "support@simplyanalytics.com.";
    verificationStep("Verify that contact us note is the expected");
    Assert.assertTrue("Contact us note is not the expected", contactUsNote.equals(importTermsWindow.getContactUsNote()));
    
    importTermsWindow.clickCancelButton();
  }
  
  @Test
  public void testImportTermsSupportMailTo() {
    verificationStep("Verify support@simplyanalitics.com mail to link");
    Assert.assertEquals("Mail to link is not the expected","mailto:support@simplyanalytics.com",importTermsWindow.getMailToLink());

    importTermsWindow.clickCancelButton();
  }
  
  @After
  public void after() {
  }

}
