package net.simplyanalytics.tests.filter;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariableRelation;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.toolbar.Filtering;
import net.simplyanalytics.pageobjects.sections.toolbar.Filtering.FilteringConditionRow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapFilterTests extends TestBase {

  private MapPage mapPage;

  /**
   * Singning in and creating new project.
   */
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }

  @Test
  public void testFilterOpens() {
    Filtering filtering = mapPage.getToolbar().clickFiltering();

    verificationStep("Verify that the hide and strikeout buttons didn't appear");
    Assert.assertFalse("The hide button appeared", filtering.isHidePresent());
    Assert.assertFalse("The strikeout button appeared", filtering.isStrikeoutPresent());

    verificationStep("Verify that the default data variables are present");
    defaultDataVariables.stream()
        .forEach(dataVariable -> Assert.assertTrue("The data variable is missing: " + dataVariable,
            filtering.isDataVariableSelectable(dataVariable)));
  }

  //@Disabled("Skipping test testFilterLayerAppeareance")
  public void testFilterLayerAppeareance() {
    Filtering filtering = mapPage.getToolbar().clickFiltering();
    FilteringConditionRow filteringConditionRow = filtering
        .clickDataVariable(medianDefaultDataVariable);
    filteringConditionRow.selectRelation(DataVariableRelation.GREATEREQUAL);
    filteringConditionRow.enterValue1("65000");
    filtering.clickApply();
    mapPage = (MapPage) filtering.clickCloseFiltering();

    mapPage.getActiveView().waitForFullLoad();
    verificationStep("Verify that the filter layer appeared");
    Assert.assertTrue("The filter layer didn't appear",
        mapPage.getActiveView().isFilterLayerPresent());

    filtering = mapPage.getToolbar().clickFiltering();
    filtering.switchActivity();
    filtering.clickApply();
    mapPage = (MapPage) filtering.clickCloseFiltering();

    mapPage.getActiveView().waitForFullLoad();
    verificationStep("Verify that the filter layer disappeared");
    Assert.assertFalse("The filter layer didn't disappear",
        mapPage.getActiveView().isFilterLayerPresent());
  }
}
