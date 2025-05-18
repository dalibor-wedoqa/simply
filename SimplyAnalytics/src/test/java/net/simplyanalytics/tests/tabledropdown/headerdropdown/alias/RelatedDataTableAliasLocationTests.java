package net.simplyanalytics.tests.tabledropdown.headerdropdown.alias;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RelatedDataTableAliasLocationTests extends TestBase {

  private RelatedDataReportPage relatedDataTablePage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;

  /**
   * Signing in, creating new project and open the related data table page.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(losAngeles);
    relatedDataTablePage = (RelatedDataReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.RELATED_DATA).clickDone();
  }

  @Test
  public void testColumnHeaderAliasLocation() {
    String aliasName = "alias name";

    AliasLocationWindow aliasLocationWindow = relatedDataTablePage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName()).clickAddAliasLocationName();

    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();

    relatedDataTablePage = new RelatedDataReportPage(driver);
    
    String newName = relatedDataTablePage.getActiveView().getNormalHeaderValues().get(0);
    relatedDataTablePage.getActiveView().waitForColumnElementToChange(newName);


    verificationStep(
        "Verify that the new location name contains the alias name and the original name");
    String expectedName = aliasName + "  [" + losAngeles.getName() + "]";
    Assert.assertEquals("The location name is not the expected", expectedName, newName);

    aliasLocationWindow = relatedDataTablePage.getActiveView()
        .openColumnHeaderDropdown(expectedName).clickEditAliasLocationName();

    aliasName = "alias name edited";
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();

    relatedDataTablePage = new RelatedDataReportPage(driver);    
    
    newName = relatedDataTablePage.getActiveView().getNormalHeaderValues().get(0);
    
    relatedDataTablePage.getActiveView().waitForColumnElementToChange(newName);


    verificationStep(
        "Verify that the new location name contains the edited alias name and the original name");
    expectedName = aliasName + "  [" + losAngeles.getName() + "]";
    Assert.assertEquals("The location name is not the expected", expectedName, newName);

    aliasLocationWindow = relatedDataTablePage.getActiveView()
        .openColumnHeaderDropdown(expectedName).clickEditAliasLocationName();

    aliasName = "";
    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();
    relatedDataTablePage = new RelatedDataReportPage(driver);

    newName = relatedDataTablePage.getActiveView().getNormalHeaderValues().get(0);

    verificationStep("Verify that the location name is the original name");
    Assert.assertEquals("The location name is not the expected", losAngeles.getName(), newName);
  }
}
