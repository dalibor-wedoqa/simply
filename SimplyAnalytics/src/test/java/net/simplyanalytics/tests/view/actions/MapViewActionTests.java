package net.simplyanalytics.tests.view.actions;

import java.util.List;

import javax.mail.Message;

import net.simplyanalytics.constants.Emails;
import net.simplyanalytics.constants.GMailUser;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.sections.toolbar.map.ExportShapefilesWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.map.MapToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.map.MapViewActionMenu;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.sections.view.map.MapViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.EmailUtils;
import net.simplyanalytics.utils.EmailUtils.EmailFolder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapViewActionTests extends TestBase {

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
        .createNewProjectWithLocationAndDefaultVariables(Location.FLORIDA);
  }

  @Test
  public void testExportShapefiles() throws Exception {
    final String link = "Download Shapefile";
    MapToolbar mapToolbar = mapPage.getToolbar();
    MapViewActionMenu viewActionsMenu = (MapViewActionMenu) mapToolbar.clickViewActions();
    ExportShapefilesWindow exportShapefilesWindow = viewActionsMenu.clickExportShapefiles();

    exportShapefilesWindow.chooseLocation("USA");
    exportShapefilesWindow.chooseGeographicUnit("States");
    exportShapefilesWindow.enterEmailTo(GMailUser.EMAIL);
    exportShapefilesWindow.clickOnSendButton();

    EmailUtils emailUtils = new EmailUtils(GMailUser.EMAIL, GMailUser.PASSWORD, "mail.wedoqa.co",
        EmailFolder.INBOX);
    Message message = emailUtils.waitMessageBySubjectAndMessage(Emails.DATA_EXPORTED.getTitle(),
        link);
    emailUtils.getUrlsFromMessage(message, link);
  }

  @Test
  public void testShowLegend() {
    verificationStep("Verify that the Legend is displayed on the Map page");
    Assert.assertTrue("The Legend didn't appear on the Map page",
        mapPage.getActiveView().isLegendPresent());

    MapToolbar mapToolbar = mapPage.getToolbar();
    MapViewActionMenu viewActionsMenu = (MapViewActionMenu) mapToolbar.clickViewActions();
    viewActionsMenu.clickShowLegend();

    verificationStep("Verify that the Legend is not displayed on the Map page");
    Assert.assertFalse("The Legend did appear on the Map page",
        mapPage.getActiveView().isLegendPresent());
  }

  //@Disabled("Skipping test testShowMapLabels")
  public void testShowMapLabels() {
    MapViewPanel mapViewPanel = mapPage.getActiveView();

    verificationStep("Verify that the Map labels are not displayed on the Map page");
    Assert.assertFalse("The Map labels did appear on the Map page",
        mapViewPanel.isMapLabelsPresent());

    MapToolbar mapToolbar = mapPage.getToolbar();
    MapViewActionMenu viewActionsMenu = (MapViewActionMenu) mapToolbar.clickViewActions();
    viewActionsMenu.clickShowMapLabels();

    verificationStep("Verify that the Map labels are displayed on the Map page");
    Assert.assertTrue("The Map labels didn't appear on the Map page",
        mapViewPanel.isMapLabelsPresent());
  }

  //@Disabled("Skipping test testHighlightLocation")
  public void testHighlightLocation() {
    MapViewPanel mapViewPanel = mapPage.getActiveView();

    verificationStep("Verify that the Location Highlight are displayed on the Map page");
    Assert.assertTrue("The Location Highlight didn't appear on the Map page",
        mapViewPanel.isHighlighLocationsPresent());

    MapToolbar mapToolbar = mapPage.getToolbar();
    MapViewActionMenu viewActionsMenu = (MapViewActionMenu) mapToolbar.clickViewActions();
    viewActionsMenu.clickHighlightLocations();

    verificationStep("Verify that the Location Highlight are not displayed on the Map page");
    Assert.assertFalse("The Location Highlight did appear on the Map page",
        mapViewPanel.isHighlighLocationsPresent());
  }

  //@Disabled("Skipping test testApplyLocationMask")
  public void testApplyLocationMask() {
    MapViewPanel mapViewPanel = mapPage.getActiveView();

    String tilesIdBefore = mapViewPanel.getMapTilesId();

    MapToolbar mapToolbar = mapPage.getToolbar();
    MapViewActionMenu viewActionsMenu = (MapViewActionMenu) mapToolbar.clickViewActions();
    mapPage = viewActionsMenu.clickApplyLocationMask();

    mapViewPanel = mapPage.getActiveView();

    String tilesIdAfter = mapViewPanel.getMapTilesId();

    verificationStep("Verify that the Applied Location Masks are loaded on the Map page");
    Assert.assertNotEquals("The applied Location Masks didn't appear on the Map page",
        tilesIdBefore, tilesIdAfter);
  }

  @Test
  public void testDragLegendPanel() {
    MapViewPanel mapViewPanel = mapPage.getActiveView();
    LegendPanel legendPanel = mapViewPanel.getLegend();
    int xPoz = legendPanel.getLegendLocation().getX();
    int yPoz = legendPanel.getLegendLocation().getY();
    mapViewPanel.dragAndDrop(-400, 300);
    
    verificationStep("Verify that the Legend panel is moved");
    Assert.assertNotEquals("The X coordinates are the same after moving the panel",
        legendPanel.getLegendLocation().getX(), xPoz);

    Assert.assertNotEquals("The Y coordinates are the same after moving the panel",
        legendPanel.getLegendLocation().getY(), yPoz);
  }
  
  @Test
  public void testCreateDataTable() {
	  
	  String activeData = mapPage.getToolbar().getNameOfActiveDataVariable();
	  
	  MapToolbar mapToolbar = mapPage.getToolbar();
	  MapViewActionMenu viewActionsMenu = (MapViewActionMenu) mapToolbar.clickViewActions();
	  RankingPage rankingPage = viewActionsMenu.clickOnCreateDataTable();
	  
	  List<String> dataVariables = rankingPage.getActiveView().getRowHeaderValues();
	  
	  verificationStep("Verify that the correct data variable is present in Ranking table");
	  Assert.assertNotEquals("Wrong data is disaplyed in the Ranking table", dataVariables, activeData);
  }
}
