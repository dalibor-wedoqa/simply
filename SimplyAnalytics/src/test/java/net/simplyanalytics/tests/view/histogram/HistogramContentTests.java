package net.simplyanalytics.tests.view.histogram;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Point;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.HistogramPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.sections.view.histogram.EditLegendPanel;
import net.simplyanalytics.pageobjects.sections.view.histogram.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class HistogramContentTests extends TestBase {
  private HistogramPage histogramPage;
  private Location losAngeles = Location.LOS_ANGELES_CA_CITY;
  private DataVariable dataVariable = DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2020;
  private EditLegendPanel editLegendPanel;
  private LegendPanel legendPanel;

  /**
   * Signing in, creating new project and open the histogram page.
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
    histogramPage = (HistogramPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.HISTOGRAM).clickDone();
  }

  //TODO check parameterized tests, it should be the same one
//  @Test
  public void createHistogramTest() {
    verificationStep("Verify that the new histogram view is created");
    Assert.assertEquals("The actual view is the new created view", "Histogram",
    		histogramPage.getViewChooserSection().getActiveViewName());

    verificationStep("Verify that the new histogram view legend shows data correctly");
    Assert.assertEquals("The actual view legend active data should be shown correctly",
        dataVariable, histogramPage.getActiveView().getLegend().getActiveData());
  }
  
  @Test
  public void outlineColorChangeTest() {
	editLegendPanel = histogramPage.getActiveView().getLegend().clickEdit();
    String actualColorBefore = editLegendPanel.getActualBarColor();
    ColorSelectionPanel colorSelectionPanel = editLegendPanel.clickColorBarCmb();
    colorSelectionPanel.chooseColorByIndex(15);

    verificationStep("Verify that the bar color is changed");
    Assert.assertNotEquals("The bar color should be changed", actualColorBefore,
        editLegendPanel.getActualBarColor());
  }
  
  @Test
  public void legendMoveTest() {
	legendPanel = histogramPage.getActiveView().getLegend();
    Point beforeMoving = legendPanel.getLegendLocation();
    legendPanel.moveLegend(-300, 300);
    verificationStep("Verify that the legend panel is moved");
    Assert.assertNotEquals("The legend panel should be moved", beforeMoving,
        legendPanel.getLegendLocation());
  }
}
