package net.simplyanalytics.tests.view.map.legend;

import java.util.Random;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.sections.view.map.EditLegendPanel;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LegendEditTests extends TestBase {

  private EditLegendPanel editLegendPanel;
  private DataVariable income = medianDefaultDataVariable;

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
    mapPage.getToolbar().openDataVariableListMenu().clickDataVariable(income);
    
    LegendPanel legendPanel = mapPage.getActiveView().getLegend();
    editLegendPanel = legendPanel.clickEdit();
  }

  @Test
  public void changeNumberOfCategoriesTest() {
    int numberOfCategories = 7;

    editLegendPanel.changeNumberOfCategories(numberOfCategories);
    editLegendPanel.clickDone();

    verificationStep("Verify that the number of categories is " + numberOfCategories);
    Assert.assertEquals("The number of categories in the Legend Panel is not the expexted",
        numberOfCategories, editLegendPanel.numberOfCategoriesElements());
  }

  @Test
  public void classificationMethodChangeTest() {
    String classificationMethod = "Equal Intervals";

    editLegendPanel.changeClassificationMethodbutton(classificationMethod);

    verificationStep("Verify that the classification method is " + classificationMethod);
    Assert.assertEquals("The actual clasification method is not the expexted",
        editLegendPanel.getActualClassificationMethod(), classificationMethod);
  }

  @Test
  public void colorSchemeChangeTest() {
    Random random = new Random();
    int i = random.nextInt(editLegendPanel.numberOfCategoriesElements());
    String background = editLegendPanel.getColorSchemeButtonElementBackground(i);

    editLegendPanel.changeColorSchemeByIndex(4);

    verificationStep("Verify that the color scheme is changed");
    Assert.assertNotEquals("The actual clasification method is not the expexted",
        editLegendPanel.getColorSchemeButtonElementBackground(i), background);
  }

  @Test
  public void outlineThicknessChangeTest() {
    String i = "3";
    editLegendPanel.clickOutlineThicknesCombox();
    editLegendPanel.clickOutlineThicknessElementNumber(i);

    verificationStep("Verify that the outline thickness is changed");
    Assert.assertEquals("The actual outline thckness is not the expexted", i,
        editLegendPanel.getActualOutlineThickness());
  }

  @Test
  public void outlineColorChangeTest() {
    String actualColorBefore = editLegendPanel.getActualOutlineColor();
    ColorSelectionPanel colorSelectionPanel = editLegendPanel.clickOnOutlineColorButton();
    colorSelectionPanel.chooseColorByIndex(15);
    
    sleep(1000);
    
    String newColor = editLegendPanel.getActualOutlineColor();
    
    verificationStep("Verify that the outline color is changed");
    Assert.assertNotEquals("The outline color should be changed", actualColorBefore, newColor);
  }
  
  @Test
  public void changeCategoryRangeTest() {
    int index = 0;
    String range = "41,000";

    editLegendPanel.getRangeRow(index).enterUpperEdge(range);
    int editLegendPanelWidth = editLegendPanel.getLegendWidth();
    LegendPanel legendPanel = editLegendPanel.clickDone();

    verificationStep("Verify that the edit legend panel is closed");
    Assert.assertTrue("Legend panel is not closed",
        legendPanel.getLegendWidth() < editLegendPanelWidth);
    
    verificationStep("Verify that the category range is changed");
    Assert.assertEquals("The category range should be changed", "$" + range + ".00",
        legendPanel.getCategoriRangeUpperValueByIndex(index));
  }

}
