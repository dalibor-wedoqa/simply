package net.simplyanalytics.tests.view.map;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.view.map.MapViewPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapZoomTests extends TestBase {

  MapViewPanel mapViewPanel;

  /**
   * Sign-in and creating a new project.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();

    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    mapViewPanel = mapPage.getActiveView();
  }
  //@Disabled("Skipping test testZoomWithPlusAndMinusButton")
  public void testZoomWithPlusAndMinusButton() {
    
    String[] mapScaleBefore = mapViewPanel.getScalePanel().getMapScale().split(" ");

    mapViewPanel.getZoomSlider().clickZoomInButton();

    String[] mapScaleafter = mapViewPanel.getScalePanel().getMapScale().split(" ");

    verificationStep("Verify that map scale is changed properly ");
    Assert.assertTrue("The map scale should be changed",
        (Integer.valueOf(mapScaleBefore[0]) > Integer.valueOf(mapScaleafter[0]))
            || mapScaleafter[1].contains("ft"));
    
    mapScaleBefore = mapScaleafter;
    
    mapViewPanel.getZoomSlider().clickZoomOutButton();
    
    verificationStep("Verify that map scale is changed properly ");
    Assert.assertTrue("The map scale should be changed",
        (Integer.valueOf(mapScaleBefore[0]) < Integer.valueOf(mapScaleafter[0]))
            || mapScaleafter[1].contains("mi"));
  }
  
  @Test
  public void testZoomWithSlider() {
    
    String[] mapScaleBefore = mapViewPanel.getScalePanel().getMapScale().split(" ");
    
    mapViewPanel.getZoomSlider().moveZoomLevelWithSlider(2);
    
    String[] mapScaleafter = mapViewPanel.getScalePanel().getMapScale().split(" ");
    
    verificationStep("Verify that map scale is changed properly ");
    Assert.assertTrue("The map scale should be changed",
        (Integer.valueOf(mapScaleBefore[0]) < Integer.valueOf(mapScaleafter[0]))
            || mapScaleafter[1].contains("mi"));   
  }
  
  @Test
  public void testZoomWithBoundingBox() {
    
    String[] mapScaleBefore = mapViewPanel.getScalePanel().getMapScale().split(" ");
    
    mapViewPanel.getControlsPanel().zoomWithBoundingBox(80, 80);
    
    String[] mapScaleafter = mapViewPanel.getScalePanel().getMapScale().split(" ");
    
    verificationStep("Verify that map scale is changed properly ");
    Assert.assertTrue("The map scale should be changed",
        (Integer.valueOf(mapScaleBefore[0]) > Integer.valueOf(mapScaleafter[0]))
            || mapScaleafter[1].contains("ft"));
  }
}
