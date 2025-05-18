package net.simplyanalytics.tests.color;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.panels.color.PaletteTab;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecentColorTests extends TestBase {

  private ColorSelectionPanel colorSelectionPanel;
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
    colorSelectionPanel = legendPanel.getRangeRow(2).clickColorIcon();
    
  }

  @Test
  public void testRecentColor() {

    int index = 2;
    int paletetIndex = 15;

    PaletteTab paletteTab = colorSelectionPanel.clickPaletteButton();

    String background = paletteTab.getColorByIndex(paletetIndex);
    paletteTab.chooseColorByIndex(paletetIndex);

    verificationStep("Verify that the color is changed properly");
    Assert.assertEquals("The color should be changed properly", background,
        legendPanel.getRangeRow(index).getSelectedColorCode());

    index++;

    colorSelectionPanel = legendPanel.getRangeRow(index).clickColorIcon();
    paletteTab = colorSelectionPanel.clickPaletteButton();
    paletteTab.clickOnRecentColorByIndex(0);

    verificationStep("Verify that the color is changed properly");
    Assert.assertEquals("The color should be changed properly", background,
        legendPanel.getRangeRow(index).getSelectedColorCode());
    
  }

  @Test
  public void testRecentColorsPanel() {
    
    List<String> colors = new ArrayList<String>();
    List<String> recentColors = new ArrayList<String>();
    colors.add("FFFFBF");
    colors.add("0000FF");
    colors.add("000000");
//
    for (String color : colors) {
      legendPanel.getRangeRow(2).clickColorIcon().clickPaletteButton().clickOnColorButton(color);
    }

    PaletteTab paletteTab = legendPanel.getRangeRow(3).clickColorIcon().clickPaletteButton();
    recentColors = paletteTab.getRecentColors();

    verificationStep("Verify that all the recent colors are shown properly");
    Assert.assertTrue("The all the recent colors are the choosen colors",
        colors.size() == recentColors.size());
    Assert.assertTrue("The all the recent colors are the choosen colors",
        colors.containsAll(recentColors));
    
    paletteTab.clickOnRecentColorByIndex(2);
    verificationStep("Verify that the expected color appears");
    String actualColor = legendPanel.getRangeRow(3).getSelectedColorCode();
    Assert.assertEquals("The color is not the expected", colors.get(0), actualColor);
    
    paletteTab = legendPanel.getRangeRow(3).clickColorIcon().clickPaletteButton();
    paletteTab.clickOnRecentColorButton(colors.get(1));
    verificationStep("Verify that the expected color appears");
    actualColor = legendPanel.getRangeRow(3).getSelectedColorCode();
    Assert.assertEquals("The color is not the expected", colors.get(1), actualColor);
    
  }
}
