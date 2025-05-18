package net.simplyanalytics.tests.view.export.custommap;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.StandardSize;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.LayoutWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.OverlayEditPanel;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;

/**
 * .
 *
 * @author wedoqa
 */
public class MapExportLayoutTests extends TestBase {

  private LayoutWindow layoutWindow;

  private static final double letterRation = 11 / 8.5;
  private static final double legalRation = 14 / 8.5;
  private static final double tabloidRation = 17.0 / 11;

  /**
   * Signing in, creating new project and opening map export panel.
   */
  @Before
  public void before() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();
    layoutWindow = croppingWindow.clickContinueToLayout();
  }
  //@Disabled("Skipping test testMapSizeAndLocationSelection ")
  public void testMapSizeAndLocationSelection() {

    if (layoutWindow.isPageSizeInInches()) {
      layoutWindow.clickInPixels();
    }

    int paperWidth = layoutWindow.getPaperSize().getWidth();
    layoutWindow.enterWidth(paperWidth);

    verificationStep("Verify that the map is present");
    Assert.assertTrue("The map should be present", layoutWindow.isMapPresent());

    OverlayEditPanel mapEditPanel = layoutWindow.clickMap();
    mapEditPanel.enterWidth(500);

    verificationStep("Verify that the map width is the given size");
    Assert.assertEquals("The map width is not the expected", 500,
        layoutWindow.getMapSize().getWidth());

    int left = 100;
    mapEditPanel.enterLeft(left);

    int xcoordPaper = layoutWindow.getPaperLocation().x;
    int xcoordLegend = layoutWindow.getMapLocation().x;
    int diffLeft = xcoordLegend - xcoordPaper;

    verificationStep(
        "Verify that the magin from the left side of the page for the map is the expected");
    Assert.assertEquals("The map left is not the expected", left, diffLeft, 2);
    
    int oldTop = layoutWindow.getMapTop();
    int oldLeft = layoutWindow.getMapLeft();

    int originalLeft = mapEditPanel.getLeft();
    int originalTop = mapEditPanel.getTop();

    mapEditPanel = layoutWindow.moveMap(5, 10);

    verificationStep("Verify that the map moved by the given vector");
    int expectedLeft = originalLeft + 5;
    int expectedTop = originalTop + 10;
    
    verificationStep("Verify that the map moved by the given vector (with map coordinates)");
    Assert.assertEquals("The x coordinate is not the expected", oldTop + 10, layoutWindow.getMapTop(), 2);
    Assert.assertEquals("The y coordinate is not the expected", oldLeft + 5, layoutWindow.getMapLeft(), 2);
    
    Assert.assertEquals("The x coordinate is not the expected", expectedLeft, mapEditPanel.getLeft(), 2);
    Assert.assertEquals("The y coordinate is not the expected", expectedTop, mapEditPanel.getTop(), 2);
    layoutWindow.clickClose();
  }

  @Test()
  public void testPageSizeSelection() {
    layoutWindow.clickInInches();

    verificationStep("Verify that the In inches option is selected");
    Assert.assertTrue("The option in inches should be selected", layoutWindow.isPageSizeInInches());

    verificationStep("Verify that the height of the page is in inches");
    Assert.assertEquals("The height is not in inches", "inches", layoutWindow.getHeightUnit());

    verificationStep("Verify that the width of the page is in inches");
    Assert.assertEquals("The width is not in inches", "inches", layoutWindow.getWidthUnit());

    layoutWindow.clickInPixels();

    verificationStep("Verify that the In pixels option is selected");
    Assert.assertTrue("The option in pixels should be selected", layoutWindow.isPageSizeInPixels());

    verificationStep("Verify that the height of the page is in pixels");
    Assert.assertEquals("The height is not in pixels", "pixels", layoutWindow.getHeightUnit());

    verificationStep("Verify that the width of the page is in pixels");
    Assert.assertEquals("The width is not in pixels", "pixels", layoutWindow.getWidthUnit());
  }

  @Test
  public void testStandardRatioMap() {
    layoutWindow.chooseStandardSize(StandardSize.LETTER);
    verificationStep("Verify that the selected size is: " + StandardSize.LETTER.getValue());
    Assert.assertEquals("The selected standard size is not the expected",
        StandardSize.LETTER.getValue(), layoutWindow.getStandardSizeComboText());

    Dimension dimension = layoutWindow.getPaperSize();
    double diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - letterRation);

    verificationStep("Verify that dimension ratio match with the letter ratio (11/8.5)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);

    verificationStep("Verify that the page size is 11/8.5");
    Assert.assertEquals("The width is not the letter standard", 11, layoutWindow.getWidth(), 0);
    Assert.assertEquals("The height is not the letter standard", 8.5, layoutWindow.getHeight(), 0);

    layoutWindow.chooseStandardSize(StandardSize.LEGAL);
    verificationStep("Verify that the selected size is: " + StandardSize.LEGAL.getValue());
    Assert.assertEquals("The selected standard size is not the expected",
        StandardSize.LEGAL.getValue(), layoutWindow.getStandardSizeComboText());

    dimension = layoutWindow.getPaperSize();
    diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - legalRation);
    verificationStep("Verify that dimension ratio match with the legal ratio (14/8.5)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);

    verificationStep("Verify that the page size is 14/8.5");
    Assert.assertEquals("The width is not the legal standard", 14, layoutWindow.getWidth(), 0);
    Assert.assertEquals("The height is not the legal standard", 8.5, layoutWindow.getHeight(), 0);

    layoutWindow.chooseStandardSize(StandardSize.TABLOID);
    verificationStep("Verify that the selected size is: " + StandardSize.TABLOID.getValue());
    Assert.assertEquals("The selected standard size is not the expected",
        StandardSize.TABLOID.getValue(), layoutWindow.getStandardSizeComboText());

    dimension = layoutWindow.getPaperSize();
    diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - tabloidRation);
    verificationStep("Verify that dimension ratio match with the tabloid ratio (17/11)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);

    verificationStep("Verify that the page size is 17/11");
    Assert.assertEquals("The width is not the tabloid standard", 17, layoutWindow.getWidth(), 0);
    Assert.assertEquals("The height is not the tabloid standard", 11, layoutWindow.getHeight(), 0);

    layoutWindow.clickPortrait();

    layoutWindow.chooseStandardSize(StandardSize.LETTER);
    verificationStep("Verify that the selected size is: " + StandardSize.LETTER.getValue());
    Assert.assertEquals("The selected standard size is not the expected",
        StandardSize.LETTER.getValue(), layoutWindow.getStandardSizeComboText());

    dimension = layoutWindow.getPaperSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - letterRation);
    verificationStep("Verify that dimension ratio match with the letter ratio (8.5/11)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);

    verificationStep("Verify that the page size is 8.5/11");
    Assert.assertEquals("The width is not the letter standard", 8.5, layoutWindow.getWidth(), 0);
    Assert.assertEquals("The height is not the letter standard", 11, layoutWindow.getHeight(), 0);

    layoutWindow.chooseStandardSize(StandardSize.LEGAL);
    verificationStep("Verify that the selected size is: " + StandardSize.LEGAL.getValue());
    Assert.assertEquals("The selected standard size is not the expected",
        StandardSize.LEGAL.getValue(), layoutWindow.getStandardSizeComboText());

    dimension = layoutWindow.getPaperSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - legalRation);
    verificationStep("Verify that dimension ratio match with the legal ratio (8.5/14)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);

    verificationStep("Verify that the page size is 8.5/14");
    Assert.assertEquals("The width is not the legal standard", 8.5, layoutWindow.getWidth(), 0);
    Assert.assertEquals("The height is not the legal standard", 14, layoutWindow.getHeight(), 0);

    layoutWindow.chooseStandardSize(StandardSize.TABLOID);
    verificationStep("Verify that the selected size is: " + StandardSize.TABLOID.getValue());
    Assert.assertEquals("The selected standard size is not the expected",
        StandardSize.TABLOID.getValue(), layoutWindow.getStandardSizeComboText());

    dimension = layoutWindow.getPaperSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - tabloidRation);
    verificationStep("Verify that dimension ratio match with the tabloid ratio (11/17)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);

    verificationStep("Verify that the page size is 11/17");
    Assert.assertEquals("The width is not the tabloid standard", 11, layoutWindow.getWidth(), 0);
    Assert.assertEquals("The height is not the tabloid standard", 17, layoutWindow.getHeight(), 0);

  }

  @Test
  public void testLockRatioIches() {

    verificationStep("Verify that the ratio is locked by default");
    Assert.assertTrue("The aspect ratio is not locked", layoutWindow.isRatioLocked());

    layoutWindow.enterWidth(5.5d);

    verificationStep("Verify that the height updated with the given ratio");
    Dimension dimension = layoutWindow.getPaperSize();
    double diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - letterRation);
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);

    layoutWindow.enterHeight(17d);

    verificationStep("Verify that the width updated with the given ratio");
    dimension = layoutWindow.getPaperSize();
    diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - letterRation);
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);

    layoutWindow.clickLockRatio();

    verificationStep("Verify that the ratio is unlocked");
    Assert.assertFalse("The aspect ratio is locked", layoutWindow.isRatioLocked());

    layoutWindow.enterWidth(10d);

    verificationStep("Verify that the height did not changed");
    Assert.assertEquals("The height should not change this time", 17d, layoutWindow.getHeight(), 0);

    layoutWindow.enterHeight(8.5d);

    verificationStep("Verify that the width did not changed");
    Assert.assertEquals("The width should not change this time", 10d, layoutWindow.getWidth(), 0);

    layoutWindow.clickLockRatio();

    verificationStep("Verify that the ratio is locked");
    Assert.assertTrue("The aspect ratio is unlocked", layoutWindow.isRatioLocked());

    layoutWindow.enterWidth(20d);

    verificationStep("Verify that the height updated properly");
    Assert.assertEquals("The height should change this time", 17d, layoutWindow.getHeight(), 0);
  }

  @Test
  public void testLockRatioPixels() {

    verificationStep("Verify that the ratio is locked by default");
    Assert.assertTrue("The aspect ratio is not locked", layoutWindow.isRatioLocked());

    layoutWindow.clickInPixels();

    layoutWindow.enterWidth(2500);

    verificationStep("Verify that the height updated with the given ratio");
    Dimension dimension = layoutWindow.getPaperSize();
    double diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - letterRation);
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);

    layoutWindow.enterHeight(1500);

    verificationStep("Verify that the width updated with the given ratio");
    dimension = layoutWindow.getPaperSize();
    diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - letterRation);
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);

    layoutWindow.clickLockRatio();

    verificationStep("Verify that the ratio is unlocked");
    Assert.assertFalse("The aspect ratio is locked", layoutWindow.isRatioLocked());

    layoutWindow.enterWidth(2500);

    verificationStep("Verify that the height did not changed");
    Assert.assertEquals("The height should not change this time", 1500, layoutWindow.getHeight(),
        0);

    layoutWindow.enterHeight(2000);

    verificationStep("Verify that the width did not changed");
    Assert.assertEquals("The width should not change this time", 2500, layoutWindow.getWidth(), 0);

    layoutWindow.clickLockRatio();

    verificationStep("Verify that the ratio is locked");
    Assert.assertTrue("The aspect ratio is unlocked", layoutWindow.isRatioLocked());

    layoutWindow.enterWidth(500);

    verificationStep("Verify that the height updated properly");
    Assert.assertEquals("The height should change this time", 400, layoutWindow.getHeight(), 0);
  }

  /*
   * SA-1045
   */
  @Test
  public void testOverlayEdition() {
    if (layoutWindow.isPageSizeInInches()) {
      layoutWindow.clickInPixels();
    }

    int paperWidth = layoutWindow.getPaperSize().getWidth();
    layoutWindow.enterWidth(paperWidth);

    OverlayEditPanel overlayEditPanel = layoutWindow.clickMap();

    overlayEditPanel.enterWidth(300);

    verificationStep("Verify that the map width is the given size");
    Assert.assertEquals("The map width is not the expected", 300,
        layoutWindow.getMapSize().getWidth());
  }

  @Test
  public void testLegend() {
    if (layoutWindow.isPageSizeInInches()) {
      layoutWindow.clickInPixels();
    }

    int paperWidth = layoutWindow.getPaperSize().getWidth();
    layoutWindow.enterWidth(paperWidth);

    verificationStep("Verify that the legend checkbox is selected");
    Assert.assertTrue("The legend checkbox should be selected",
        layoutWindow.isLegendCheckBoxChecked());

    verificationStep("Verify that the legend present on the view");
    Assert.assertTrue("The lengend should be present", layoutWindow.isLegendPresent());

    layoutWindow.clickLegendCheckBox();

    verificationStep("Verify that the legend disapeared from the view");
    Assert.assertFalse("The lengend should not be present", layoutWindow.isLegendPresent());

    layoutWindow.clickLegendCheckBox();

    verificationStep("Verify that the legend present on the view");
    Assert.assertTrue("The lengend should be present", layoutWindow.isLegendPresent());

    OverlayEditPanel overlayEditPanelLegend = layoutWindow.clickLegend();
    overlayEditPanelLegend.enterHeight(300);

    verificationStep("Verify that the legend width is the given size");
    Assert.assertEquals("The legend width is not the expected", 300,
        layoutWindow.getLegendSize().getHeight(), 2);

    int left = 100;
    overlayEditPanelLegend.enterLeft(left);

    int xcoordPaper = layoutWindow.getPaperLocation().x;
    int xcoordLegend = layoutWindow.getLegendLocation().x;
    int diffLeft = xcoordLegend - xcoordPaper;

    verificationStep(
        "Verify that the magin from the left side of the page for the legend is the expected");
    Assert.assertTrue("The legend left is not the expected", Math.abs(left - diffLeft) <= 1); 
    // max 1 pixel difference allowed

    int originalLeft = overlayEditPanelLegend.getLeft();
    int originalTop = overlayEditPanelLegend.getTop();

    overlayEditPanelLegend = layoutWindow.moveLegend(5, 10);

    verificationStep("Verify that the legend moved by the given vector");
    int expectedLeft = originalLeft + 5;
    int expectedTop = originalTop + 10;
    
    Assert.assertTrue("The x coordinate is not the expected",
        Math.abs(expectedLeft - overlayEditPanelLegend.getLeft()) <= 1);
    Assert.assertTrue("The y coordinate is not the expected",
        Math.abs(expectedTop - overlayEditPanelLegend.getTop()) <= 1);

    layoutWindow.clickLight();

    verificationStep("Verify that the legend theme light is selected");
    Assert.assertTrue("The legend theme light should be selected",
        layoutWindow.isLegendThemeLightSelected());

    layoutWindow.clickDark();

    verificationStep("Verify that the legend theme dark is selected");
    Assert.assertTrue("The legend theme dark should be selected",
        layoutWindow.isLegendThemeDarkSelected());

    layoutWindow.clickClose();
  }

  @Test
  public void testScaleBar() {
    if (layoutWindow.isPageSizeInInches()) {
      layoutWindow.clickInPixels();
    }

    int paperWidth = layoutWindow.getPaperSize().getWidth();
    layoutWindow.enterWidth(paperWidth);

    verificationStep("Verify that the scale bar checkbox is selected");
    Assert.assertTrue("The scale bar checkbox should be selected",
        layoutWindow.isScaleBarBoxChecked());

    verificationStep("Verify that the scale bar present on the view");
    Assert.assertTrue("The lengend should be present", layoutWindow.isScaleBarPresent());

    layoutWindow.clickScaleBarCheckBox();

    verificationStep("Verify that the scale bar disapeared from the view");
    Assert.assertFalse("The lengend should not be present", layoutWindow.isScaleBarPresent());

    layoutWindow.clickScaleBarCheckBox();

    verificationStep("Verify that the scale bar present on the view");
    Assert.assertTrue("The lengend should be present", layoutWindow.isScaleBarPresent());

    int left = 300;
    OverlayEditPanel overlayEditPanelScaleBar = layoutWindow.clickScaleBar();
    overlayEditPanelScaleBar.enterLeft(left);

    int xcoordPaper = layoutWindow.getPaperLocation().x;
    int xcoordScaleBar = layoutWindow.getScaleBarLocation().x;
    int diffLeft = xcoordScaleBar - xcoordPaper;

    verificationStep(
        "Verify that the magin from the left side of the page for the scale bar is the expected");
    Assert.assertEquals("The scale bar left is not the expected", left, diffLeft, 2);

    Dimension size = layoutWindow.getScaleBarSize();
    verificationStep("Verify that the size of the scale bar is the expected");
    Assert.assertEquals("The scale bar height is not the expected",
        overlayEditPanelScaleBar.getHeight(), size.getHeight());
    Assert.assertEquals("The scale bar width is not the expected",
        overlayEditPanelScaleBar.getWidth(), size.getWidth());

    int originalLeft = overlayEditPanelScaleBar.getLeft();
    int originalTop = overlayEditPanelScaleBar.getTop();

    overlayEditPanelScaleBar = layoutWindow.moveScaleBar(5, 10);

    verificationStep("Verify that the scale bar moved by the given vector");
    int expectedLeft = originalLeft + 5;
    int expectedTop = originalTop + 10;
    
    Assert.assertEquals("The x coordinate is not the expected", expectedLeft,
        overlayEditPanelScaleBar.getLeft());
    Assert.assertEquals("The y coordinate is not the expected", expectedTop,
        overlayEditPanelScaleBar.getTop());
  }

  @Test
  public void testInsetMiniMap() {
    if (layoutWindow.isPageSizeInInches()) {
      layoutWindow.clickInPixels();
    }

    int paperWidth = layoutWindow.getPaperSize().getWidth();
    layoutWindow.enterWidth(paperWidth);

    verificationStep("Verify that the insert map checkbox is not selected");
    Assert.assertFalse("The insert map checkbox should not be selected",
        layoutWindow.isInsetMapBoxChecked());

    verificationStep("Verify that the mini map not present on the view");
    Assert.assertFalse("The mini map should not be present", layoutWindow.isMiniMapPresent());

    layoutWindow.clickInsetMapCheckBox();

    verificationStep("Verify that the insert map checkbox is selected");
    Assert.assertTrue("The insert map checkbox should be selected",
        layoutWindow.isInsetMapBoxChecked());

    verificationStep("Verify that the mini map present on the view");
    Assert.assertTrue("The mini map should be present", layoutWindow.isMiniMapPresent());

    layoutWindow.clickInsetMapCheckBox();

    verificationStep("Verify that the mini map disapeared from the view");
    Assert.assertFalse("The mini map should not be present", layoutWindow.isMiniMapPresent());

    layoutWindow.clickInsetMapCheckBox();

    verificationStep("Verify that the mini map present on the view");
    Assert.assertTrue("The lengend should be present", layoutWindow.isMiniMapPresent());

    OverlayEditPanel overlayEditPanelMiniMap = layoutWindow.clickMiniMap();
    overlayEditPanelMiniMap.enterWidth(50);

    verificationStep("Verify that the mini map width is the given size");
    Assert.assertEquals("The mini map width is not the expected", 50,
        layoutWindow.getMiniMapSize().getWidth());

    int top = 200;
    overlayEditPanelMiniMap.enterTop(top);
    overlayEditPanelMiniMap.enterLeft(300);
    
    int ycoordPaper = layoutWindow.getPaperLocation().y;
    int ycoordMiniMap = layoutWindow.getMiniMapLocation().y;
    int diffTop = ycoordMiniMap - ycoordPaper + 1; // there is a 1 pixel margin

    verificationStep(
        "Verify that the magin from the top of the page for the mini map is the expected");
    Assert.assertEquals("The mini map top distance is not the expected", top, diffTop, 2);

    int originalLeft = overlayEditPanelMiniMap.getLeft();
    int originalTop = overlayEditPanelMiniMap.getTop();

    int xDef = 30;
    int yDef = 20;
    
    overlayEditPanelMiniMap = layoutWindow.moveMiniMap(xDef, yDef);
    
    verificationStep("Verify that the mini map moved by the given vector");
    int expectedLeft = originalLeft + xDef;
    int expectedTop = originalTop + yDef;
    
    Assert.assertEquals("The x coordinate is not the expected", expectedLeft,
        overlayEditPanelMiniMap.getLeft());
    Assert.assertEquals("The y coordinate is not the expected", expectedTop,
        overlayEditPanelMiniMap.getTop());
  }

  @Test
  public void testNorthArrow() {
    if (layoutWindow.isPageSizeInInches()) {
      layoutWindow.clickInPixels();
    }

    int paperWidth = layoutWindow.getPaperSize().getWidth();
    layoutWindow.enterWidth(paperWidth);

    verificationStep("Verify that the north arrow checkbox is not selected");
    Assert.assertFalse("The north arrow checkbox should not be selected",
        layoutWindow.isNorthArrowChecked());

    verificationStep("Verify that the north arrow not present on the view");
    Assert.assertFalse("The north arrow should not be present", layoutWindow.isArrowPresent());

    layoutWindow.clickNorthArrowCheckBox();

    verificationStep("Verify that the north arrow checkbox is selected");
    Assert.assertTrue("The north arrow checkbox should be selected",
        layoutWindow.isNorthArrowChecked());

    verificationStep("Verify that the north arrow present on the view");
    Assert.assertTrue("The north arrow should be present", layoutWindow.isArrowPresent());

    layoutWindow.clickNorthArrowCheckBox();

    verificationStep("Verify that the north arrow disappeared from the view");
    Assert.assertFalse("The north arrow should not be present", layoutWindow.isArrowPresent());

    layoutWindow.clickNorthArrowCheckBox();

    verificationStep("Verify that the north arrow present on the view");
    Assert.assertTrue("The north arrow should be present", layoutWindow.isArrowPresent());

    OverlayEditPanel overlayEditPanelNorthArrow = layoutWindow.clickArrow();
    overlayEditPanelNorthArrow.enterWidth(50);

    verificationStep("Verify that the north arrow width is the given size");
    Assert.assertEquals("The north arrow width is not the expected", 50, layoutWindow.getArrowSize().getWidth());

    int top = 100;
    overlayEditPanelNorthArrow.enterTop(top);

    int ycoordPaper = layoutWindow.getPaperLocation().y;
    int ycoordArrow = layoutWindow.getArrowLocation().y;
    int diffTop = ycoordArrow - ycoordPaper + 1; // there is a 1 pixel margin

    verificationStep(
        "Verify that the magin from the top of the page for the north arrow is the expected");
    Assert.assertTrue("The north arrow top distance is not the expected",
        Math.abs(top - diffTop) <= 1);

    int originalLeft = overlayEditPanelNorthArrow.getLeft();
    int originalTop = overlayEditPanelNorthArrow.getTop();

    overlayEditPanelNorthArrow = layoutWindow.moveArrow(5, 10);

    verificationStep("Verify that the north arrow moved by the given vector");
    int expectedLeft = originalLeft + 5;
    int expectedTop = originalTop + 10;
    
    Assert.assertEquals("The x coordinate is not the expected", expectedLeft,
        overlayEditPanelNorthArrow.getLeft());
    Assert.assertEquals("The y coordinate is not the expected", expectedTop,
        overlayEditPanelNorthArrow.getTop());
  }

}
