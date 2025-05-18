package net.simplyanalytics.tests.view.quickreport;

import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.sections.view.base.HeaderDropdown;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QuickReportTableHeaderTests extends TestBase {

  private QuickReportPage quickReportPage;
  DataVariable expectedDataVariable = DataVariable.HASHTAG_POPULATION_2020;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private String aliasName = "Test Location";

  /**
   * Signing in, creating new project and open the quck report page.
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
    quickReportPage = (QuickReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.QUICK_REPORT).clickDone();
  }

  @Test
  public void addToFavoritesTest() {
    HeaderDropdown headerDropdown = quickReportPage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName());
    headerDropdown.clickAddFavorites();

    headerDropdown = quickReportPage.getActiveView().openColumnHeaderDropdown(losAngeles.getName());

    verificationStep("Verify that the location is added to favorites");
    Assert.assertTrue("The location should be added to favorites",
        headerDropdown.isAddedToFavorites());
  }

  @Test
  public void addAliasLocationNameTest() {
    HeaderDropdown headerDropdown = quickReportPage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName());
    AliasLocationWindow aliasLocationWindow = headerDropdown.clickAddAliasLocationName();

    aliasLocationWindow.enterAliasName(aliasName);
    aliasLocationWindow.clickSave();
    
    sleep(1000);

    List<String> columnHeaders = quickReportPage.getActiveView().getColumnHeaders();

    verificationStep("Verify that the location is renamed");
    Assert.assertTrue("The location should be renamed", columnHeaders.get(0).contains(aliasName));
  }
  
  @Test
  public void removeFromReportTest() {
    int sizeBefore = quickReportPage.getActiveView().getColumnHeaders().size();
    HeaderDropdown headerDropdown = quickReportPage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName());
    
    headerDropdown.clickHideLocation();
    
    verificationStep("Verify that the new map view is created");
    Assert.assertEquals("The actual actual view is the new created view", sizeBefore - 1,
        quickReportPage.getActiveView().getColumnHeaders().size());
  }
}
