package net.simplyanalytics.tests.tabledropdown.celldropdown.createmap;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataType;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.sections.view.base.CellDropdown;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RelatedDataTableCreateMapTests extends TestBase {

  private RelatedDataReportPage relatedDataTablePage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable dataVariable = firstDefaultDataVariable;

  /**
   * Signing in, creating new project and open the related data page.
   */
  @Before
  public void createProject() {
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
  public void testCreateMap() {
    Location expectedLocation = losAngeles;

    relatedDataTablePage.getToolbar().openDataTypeMenu().clickOnDataType(DataType.PERCENT);
    CellDropdown cellDropdown = relatedDataTablePage.getActiveView()
        .openCellDropdown(dataVariable.getFullName(), expectedLocation.getName());
    MapPage mapPage = cellDropdown.clickCreateMapButton();

    verificationStep("Verify that the selected data variable appears on the map view");
    DataVariable actualDataVariable = mapPage.getToolbar().getActiveDataVariable();
    Assert.assertEquals("The actual data variable is not the selected", dataVariable,
        actualDataVariable);

    verificationStep("Verify that the selected location appears on the map view");
    Location actualLocation = mapPage.getToolbar().getActiveLocation();
    Assert.assertEquals("The actual location is not the selected", expectedLocation,
        actualLocation);
  }

}
