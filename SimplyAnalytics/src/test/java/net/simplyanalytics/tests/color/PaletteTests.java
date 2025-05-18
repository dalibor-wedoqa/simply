package net.simplyanalytics.tests.color;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PaletteTests extends TestBase {

  private LegendPanel legendPanel;

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
    legendPanel = mapPage.getActiveView().getLegend();
  }
  
  @Test
  public void changeColorTest() {

    int index = 2;
    int paleteIndex = 15;
    
    ColorSelectionPanel colorSelectionPanel = legendPanel.getRangeRow(index).clickColorIcon();
    
    String background = colorSelectionPanel.getColorByIndex(paleteIndex);
    colorSelectionPanel.chooseColorByIndex(paleteIndex);

    verificationStep("Verify that the color is changed properly");
    Assert.assertNotEquals("The color should be changed properly", background,
        legendPanel.getRangeRow(index).getSelectedColorCode());
  }

}
