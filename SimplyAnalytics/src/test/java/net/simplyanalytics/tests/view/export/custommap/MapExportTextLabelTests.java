package net.simplyanalytics.tests.view.export.custommap;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.DriverConfiguration;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.TestBrowser;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.AddTextLabeLPanel;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.Font;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.LayoutWindow;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.TextLabel;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.TextLabelEditPanel;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.panels.color.PaletteTab;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * .
 *
 * @author wedoqa
 */
public class MapExportTextLabelTests extends TestBase {

  private LayoutWindow layoutWindow;

  /**
   * Signing in, creating new project and opening map export panel.
   */
  @Before
  public void before() {

    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.CHICAGO_IL_CITY);
    CroppingWindow croppingWindow = mapPage.getToolbar().clickExport();
    layoutWindow = croppingWindow.clickContinueToLayout();

    if (layoutWindow.isPageSizeInInches()) {
      layoutWindow.clickInPixels();
    }

    int paperWidth = layoutWindow.getPaperSize().getWidth();
    layoutWindow.enterWidth(paperWidth);

  }

  @Test
  public void testAddTextLabel() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");

    verificationStep("Verify that the entered text label is the given text");
    Assert.assertEquals("The text label is not the expected", "text",
        textLabelPanel.getTextLabel());

    textLabelPanel.clickOk();

    verificationStep("Verify that the text label appears");
    Assert.assertTrue("The text label should appear", layoutWindow.isTextLabelPresent());

    verificationStep("Verify that only a single text label appears");
    Assert.assertEquals("The number of text label is not the expected", 1,
        layoutWindow.getTextLabels().size());

  }

  @Test
  public void testTextChange() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);
    TextLabel textLabel = layoutWindow.getTextLabels().get(0);
    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();

    String newText = "text2";
    labelEditPanel.enterText(newText);

    verificationStep("Verify that the text changed");
    Assert.assertEquals("The text is not the expected", newText, labelEditPanel.getTextLabel());

  }

  @Test
  public void testDelete() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);
    
    TextLabel textLabel = layoutWindow.getTextLabels().get(0);
    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();
    labelEditPanel.clickDeleteButton();

    verificationStep("Verify that the text label disappeared");
    Assert.assertFalse("The text label should disappear", layoutWindow.isTextLabelPresent());
  }

  @Test
  public void testFontChange() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);
    
    TextLabel textLabel = layoutWindow.getTextLabels().get(0);
    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();

    Font newFont = Font.VERDANA;
    labelEditPanel.selectFont(newFont);
    labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that the text font is the expected");
    Assert.assertEquals("The text font is not the expected", newFont,
        labelEditPanel.getSelectedFont());

  }

  @Test
  public void testTextSizeChange() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);
    sleep(1000);
    TextLabel textLabel = layoutWindow.getTextLabels().get(0);

    int originalWidth = textLabel.getSize().getWidth();
    int originalHeight = textLabel.getSize().getHeight();

    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();

    int originalSize = labelEditPanel.getFontSize();

    int newSize = 32;
    labelEditPanel.selectFontSize(newSize);
    labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that the text size is the expected");
    Assert.assertEquals("The text size is not the expected", newSize, labelEditPanel.getFontSize());

    int newWidth = textLabel.getSize().getWidth();
    int newHeight = textLabel.getSize().getHeight();

    verificationStep("Verify that the label size increased by the expected rate");
    Assert.assertEquals("The text width did not increased as expected",
        1. * originalWidth * newSize / originalSize, 1. * newWidth, 2);
    Assert.assertEquals("The text height did not increased as expected",
        1. * originalHeight * newSize / originalSize, 1. * newHeight, 2);

    newSize = 12;
    labelEditPanel.selectFontSize(newSize);
    labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that the text size is the expected");
    Assert.assertEquals("The text size is not the expected", newSize, labelEditPanel.getFontSize());

    newWidth = textLabel.getSize().getWidth();
    newHeight = textLabel.getSize().getHeight();

    verificationStep("Verify that the label size decreased by the expected rate");
    Assert.assertEquals("The text width did not increased as expected",
        1. * originalWidth * newSize / originalSize, 1. * newWidth, 2);
    Assert.assertEquals("The text height did not increased as expected",
        1. * originalHeight * newSize / originalSize, 1. * newHeight, 2);

  }

  @Test
  public void testTextColorChange() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);
    TextLabel textLabel = layoutWindow.getTextLabels().get(0);
    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();

    String newColor = "0000FF";
    ColorSelectionPanel colorSelectionPanel = labelEditPanel.clickTextColorButton();
    PaletteTab paletteTab = (PaletteTab) colorSelectionPanel.getActualTab();
    
    verificationStep("Verify that transparent is not an option");
    Assert.assertFalse("The transparent should not appear for font color", paletteTab.isTransparentOptionPresent());
    
    paletteTab.clickOnColorButton(newColor);
    labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that the text color is the expected");
    Assert.assertEquals("The text color is not the expected", newColor,
        labelEditPanel.getTextColorHexCode());

  }

  @Test
  public void testBackgroundColorChange() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);
    TextLabel textLabel = layoutWindow.getTextLabels().get(0);
    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();

    String newColor = "FFFF00";
    ColorSelectionPanel colorSelectionPanel = labelEditPanel.clickBackgroundColorButton();
    PaletteTab paletteTab = (PaletteTab) colorSelectionPanel.getActualTab();
    
    verificationStep("Verify that transparent is an option");
    Assert.assertTrue("The transparent should appear as an option for background color", paletteTab.isTransparentOptionPresent());
    
    paletteTab.clickOnColorButton(newColor);
    labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that the background color is the expected");
    Assert.assertEquals("The background color is not the expected", newColor,
        labelEditPanel.getBackgroundColorHexCode());

  }

  @Test
  public void testTransparentBackgroundColor() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);
    TextLabel textLabel = layoutWindow.getTextLabels().get(0);
    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();

    ColorSelectionPanel colorSelectionPanel = labelEditPanel.clickBackgroundColorButton();
    PaletteTab paletteTab = (PaletteTab) colorSelectionPanel.getActualTab();
    paletteTab.clickTransparentButton();
    labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that the background color is the expected");
    Assert.assertEquals("The background color is not the expected", "Transparent",
        labelEditPanel.getBackgroundColorHexCode());

  }

  @Test
  public void testBold() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);
    TextLabel textLabel = layoutWindow.getTextLabels().get(0);
    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that the bold is not selected by default");
    Assert.assertFalse("The bold button should not be checked",
        labelEditPanel.isBoldButtonPressed());

    labelEditPanel.clickBoldButton();

    labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that the bold is now selected");
    Assert.assertTrue("The bold button still not checked", labelEditPanel.isBoldButtonPressed());

  }

  @Test
  public void testItalic() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);
    TextLabel textLabel = layoutWindow.getTextLabels().get(0);
    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that the italic is not selected by default");
    Assert.assertFalse("The italic button should not be checked",
        labelEditPanel.isItalicButtonPressed());

    labelEditPanel.clickItalicButton();

    labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that the italic is now selected");
    Assert.assertTrue("The italic button still not checked",
        labelEditPanel.isItalicButtonPressed());

  }

  @Test
  public void testTextLabelMoving() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);

    TextLabel textLabel = layoutWindow.getTextLabels().get(0);
    TextLabelEditPanel labelEditPanel = textLabel.clickTextLabelImage();

    labelEditPanel.enterLeft(200);
    labelEditPanel.enterTop(100);

    int xcoordPaper = layoutWindow.getPaperLocation().x;
    int xcoordLabel = textLabel.getLocation().x;
    int diffLeft = xcoordLabel - xcoordPaper;

    verificationStep("Verify that label left is the entered value");
    Assert.assertEquals("The label left is not the expected", 200, diffLeft, 2);

    int ycoordPaper = layoutWindow.getPaperLocation().y;
    int ycoordLabel = textLabel.getLocation().y;
    int diffTop = ycoordLabel - ycoordPaper;

    verificationStep("Verify that label top is the entered value");
    Assert.assertEquals("The label left is not the expected", 100, diffTop, 2);

    textLabel.moveTextLabelImage(100, 50);
    labelEditPanel = textLabel.clickTextLabelImage();

    verificationStep("Verify that label left is the expected value");
    Assert.assertEquals("The label left is not the expected", 300, labelEditPanel.getLeft(), 2);

    verificationStep("Verify that label top is the expected value");
    Assert.assertEquals("The label top is not the expected", 150, labelEditPanel.getTop(), 2);

  }

  @Test
  public void testAddMultipleTextLabel() {

    AddTextLabeLPanel textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel("text");
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);

    verificationStep("Verify that the text label appears");
    Assert.assertTrue("The text label should appear", layoutWindow.isTextLabelPresent());

    verificationStep("Verify that only a single text label appears");
    Assert.assertEquals("The number of text label is not the expected", 1,
        layoutWindow.getTextLabels().size());

    String text2 = "text2";

    textLabelPanel = layoutWindow.clickAddTextLabel();
    textLabelPanel.enterTextLabel(text2);
    textLabelPanel.clickOk();
    if(driverConfiguration.getBrowser() == TestBrowser.firefox.name())
    	sleep(1000);
    
    verificationStep("Verify that the text label appears");
    Assert.assertTrue("The text label should appear", layoutWindow.isTextLabelPresent());

    verificationStep("Verify that two text label appears");
    Assert.assertEquals("The number of text label is not the expected", 2,
        layoutWindow.getTextLabels().size());

    TextLabelEditPanel textLabelEditPanel = layoutWindow.getTextLabels().get(1)
        .clickTextLabelImage();

    verificationStep("Verify that the text is expected");
    Assert.assertEquals("The text is not the expected", text2, textLabelEditPanel.getTextLabel());
  }
}
