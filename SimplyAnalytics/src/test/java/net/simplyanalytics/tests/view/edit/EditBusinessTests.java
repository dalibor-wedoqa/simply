package net.simplyanalytics.tests.view.edit;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ManageProjectLdbPanel;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditBusinessTests extends TestBase {

  private BusinessesPage businessesPage;
  private ViewType view = ViewType.BUSINESSES;

  private String business1;
  private String business2;
  private String business3;

  /**
   * Signing in and creating new project.
   * Add more locations.
   * Add random businesses
   * Open the businesses page.
   */
  @Before
  public void createProjectWithMapView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);

    mapPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.CHICAGO_IL_CITY);
    mapPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.MIAMI_FL_CITY);

    business1 = mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
    business2 = mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
    business3 = mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();

    businessesPage = (BusinessesPage) mapPage.getViewChooserSection()
        .clickView(view.getDefaultName());
  }

  @Test
  public void testEditLocation() {
    Location original = Location.LOS_ANGELES_CA_CITY;
    Location edited = Location.MIAMI_FL_CITY;

    // verify default active location
    verificationStep("Verify that the default active location is present in the toolbar");
    Assert.assertEquals("The default active location is incorrect in the toolbar", original,
        businessesPage.getToolbar().getActiveLocation());

    EditBusinessesPage editBusinessPage = (EditBusinessesPage) businessesPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();

    RadioButtonContainerPanel dataPanel = editBusinessPage.getActiveView().getLocationsPanel();
    dataPanel.clickElement(edited.getName());

    dataPanel = editBusinessPage.getActiveView().getLocationsPanel();
    verificationStep("Verify that the location become selected");
    Assert.assertEquals("The active location is not the selected", edited.getName(),
        dataPanel.getSelectedElement());

    businessesPage = (BusinessesPage) editBusinessPage.clickDone();

    // verify edited active data
    verificationStep("Verify that the selected location is the active location in the toolbar");
    Assert.assertEquals("The active location is incorrect in the toolbar", edited,
        businessesPage.getToolbar().getActiveLocation());
  }

  @Test
  public void testEditBusiness() {
    // verify active business
    verificationStep("Verify that the first selected business is present in the toolbar");
    Assert.assertEquals("The default active location is incorrect in the toolbar", business1,
        businessesPage.getToolbar().getActiveBusiness());

    EditBusinessesPage editBusinessesPage = (EditBusinessesPage) businessesPage
        .getViewChooserSection().openViewMenu(view.getDefaultName()).clickEdit();

    RadioButtonContainerPanel businessPanel = editBusinessesPage.getActiveView()
        .getBusinessesPanel();
    businessPanel.clickElement(business3);

    verificationStep("Verify that the business become selected");
    Assert.assertEquals("The active location is not the selected", business3,
        businessPanel.getSelectedElement());

    businessesPage = (BusinessesPage) editBusinessesPage.clickDone();

    // verify edited active data
    verificationStep("Verify that the selected business is the active business in the toolbar");
    Assert.assertEquals("The active business is incorrect in the toolbar", business3,
        businessesPage.getToolbar().getActiveBusiness());
  }

  @Test
  public void testCreateBusinessViewWithoutBusiness() {
    ProjectSettingsPage projectSettingsPage = businessesPage.getHeaderSection().clickProjectSettings();
    ManageProjectLdbPanel businessPanel = projectSettingsPage.getProjectSettingsHeader().clickRemoveLDBButton().clickBusinesses();
    for (String name : businessPanel.getItemsName()) {
      businessPanel.clickX(name);
    }
    EditBusinessesPage editBusinessesPage = (EditBusinessesPage) projectSettingsPage
        .getViewChooserSection().clickNewView().getActiveView().clickCreate(ViewType.BUSINESSES);

    verificationStep("Verify that the \"" + EditViewWarning.BUSINESS_MISSING + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editBusinessesPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.BUSINESS_MISSING,
        editBusinessesPage.getActiveView().getErrorMessage());
  }

}
