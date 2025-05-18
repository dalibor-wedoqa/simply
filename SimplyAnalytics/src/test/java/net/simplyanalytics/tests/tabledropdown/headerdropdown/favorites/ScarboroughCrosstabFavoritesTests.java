package net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DMAsLocation;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;
import net.simplyanalytics.pageobjects.sections.view.ScarboroughCrosstabViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class ScarboroughCrosstabFavoritesTests extends TestBase {

  private MapPage mapPage;
  
  @Before
  public void createProject() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.USA);
    ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
  }
  
  @Test
  public void testRowHeaderDataVariableViewMetadata() {
    
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    EditScarboroughCrosstabPage editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.GENDER);
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    List<String> row = new ArrayList<String>();
    for(int i = 0; i < 10; i++) {
      row.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }
    
    editScarboroughCrosstabPage.getLdbSection().clickData();
    sleep(1000);
    
    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    
    ScarboroughCrosstabViewPanel scarboroughCrosstabViewPanel = scarboroughCrosstabPage.getActiveView();
    scarboroughCrosstabPage = (ScarboroughCrosstabPage) scarboroughCrosstabViewPanel.openRowHeaderDropdown(1).clickAddFavorites();
    
    DataTab dataTab = scarboroughCrosstabPage.getLdbSection().clickData();

    verificationStep("Verify that the favorites menu is present");
    Assert.assertTrue("The favorites button should be present", dataTab.isFavoritesPresent());

    RecentFavoriteMenu recentFavoriteMenu = dataTab.clickFavorites();

    verificationStep("Verify that the favorites menu contains the selected data variable");
    Assert.assertTrue("The selected data variable should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(row.get(0)));

    scarboroughCrosstabPage = (ScarboroughCrosstabPage) scarboroughCrosstabViewPanel
        .openRowHeaderDropdown(1).clickRemoveFavorites();

    dataTab = scarboroughCrosstabPage.getLdbSection().getDataTab();

    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present", dataTab.isFavoritesPresent());
  }
  
  @Test
  public void testColumnHeaderDataVariableViewMetadata() {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    EditScarboroughCrosstabPage editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.GENDER);
    List<String> column = new ArrayList<String>();
    for(int i = 0; i < 10; i++) {
      column.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    
    for(int i = 0; i < 10; i++) {
      dataByCategoryDropwDown.clickOnARandomDataResult();
    }
    
    editScarboroughCrosstabPage.getLdbSection().clickData();
    sleep(1000);
    
    ScarboroughCrosstabPage scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    
    ScarboroughCrosstabViewPanel scarboroughCrosstabViewPanel = scarboroughCrosstabPage.getActiveView();
    scarboroughCrosstabPage = (ScarboroughCrosstabPage) scarboroughCrosstabViewPanel.openColumnHeaderDropdown(1).clickAddFavorites();
    
    DataTab dataTab = scarboroughCrosstabPage.getLdbSection().clickData();

    verificationStep("Verify that the favorites menu is present");
    Assert.assertTrue("The favorites button should be present", dataTab.isFavoritesPresent());

    RecentFavoriteMenu recentFavoriteMenu = dataTab.clickFavorites();

    verificationStep("Verify that the favorites menu contains the selected data variable");
    Assert.assertTrue("The selected data variable should be on the favorites menu",
        recentFavoriteMenu.isItemPresent(column.get(0)));

    scarboroughCrosstabPage = (ScarboroughCrosstabPage) scarboroughCrosstabViewPanel
        .openColumnHeaderDropdown(1).clickRemoveFavorites();

    dataTab = scarboroughCrosstabPage.getLdbSection().getDataTab();

    verificationStep("Verify that the favorites menu disappeared");
    Assert.assertFalse("The favorites button should not be present", dataTab.isFavoritesPresent());
 
  }
  
}
