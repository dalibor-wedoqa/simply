package net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites;

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
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RelatedDataTableFavoritesTests extends TestBase {
  
  private RelatedDataReportPage relatedDataTablePage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable dataVariable = DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024;
  
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
  public void testAddColumnHeaderLocationToFavorites() {
    relatedDataTablePage = (RelatedDataReportPage) relatedDataTablePage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName()).clickAddFavorites();
    LocationsTab locationsTab = relatedDataTablePage.getLdbSection().clickLocations();
    
    verificationStep("Verify that the favorites menu present");
    Assert.assertTrue("The favorites button should be present", locationsTab.isFavoritesPresent());
    
    RecentFavoriteMenu recentFavoriteMenu = locationsTab.clickFavorites();
    
    verificationStep("Verify that the favorites menu contains the selected location");
    Assert.assertTrue("The selected location should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(losAngeles.getName()));
    
    relatedDataTablePage = (RelatedDataReportPage) relatedDataTablePage.getActiveView()
        .openColumnHeaderDropdown(losAngeles.getName()).clickRemoveFavorites();
    
    locationsTab = (LocationsTab) relatedDataTablePage.getLdbSection().getLocationsTab();
    
    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present",
        locationsTab.isFavoritesPresent());
  }
  
  @Test
  public void testAddRowHeaderDataVariableToFavorites() {
    relatedDataTablePage.getToolbar().openDataTypeMenu().clickOnDataType(DataType.PERCENT);
    relatedDataTablePage = (RelatedDataReportPage) relatedDataTablePage.getActiveView()
        .openRowHeaderDropdown(dataVariable.getFullName()).clickAddFavorites();
    DataTab dataTab = relatedDataTablePage.getLdbSection().clickData();
    
    verificationStep("Verify that the favorites menu present");
    Assert.assertTrue("The favorites button should be present", dataTab.isFavoritesPresent());
    
    RecentFavoriteMenu recentFavoriteMenu = dataTab.clickFavorites();
    
    verificationStep("Verify that the favorites menu contains the selected data variable");
    Assert.assertTrue("The selected data variable should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(dataVariable.getFullName()));
    
    relatedDataTablePage = (RelatedDataReportPage) relatedDataTablePage.getActiveView()
        .openRowHeaderDropdown(dataVariable.getFullName()).clickRemoveFavorites();
    
    dataTab = relatedDataTablePage.getLdbSection().getDataTab();
    
    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present", dataTab.isFavoritesPresent());
  }
}
