package net.simplyanalytics.tests.view.export.custommap;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.StandardSize;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

/**
 * .
 *
 * @author wedoqa
 */
public class MapExportCroppingTests extends TestBase {
  
  private CroppingWindow croppingWindow;
  
  private static final double letterRation = 11 / 8.5;
  private static final double legalRation = 14 / 8.5;
  private static final double tabloidRation = 17.0 / 11;
  
  /**
   * Signing in and creating new project.
   * Open the export cropping window.
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
    croppingWindow = mapPage.getToolbar().clickExport();
  }
  
  @Test
  public void testStandardRatioMap() {
    croppingWindow.chooseStandardSize(StandardSize.LETTER);
    verificationStep("Verify that the selected size is: " + StandardSize.LETTER.getValue());
    Assert.assertEquals(
        "The selected standard size is not the expected",
        StandardSize.LETTER.getValue(),
        croppingWindow.getStandardSizeComboText());
    
    Dimension dimension = croppingWindow.getHoleSize();
    double diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - letterRation);
    verificationStep("Verify that dimension ratio match with the letter ratio (11/8.5)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.LEGAL);
    verificationStep("Verify that the selected size is: " + StandardSize.LEGAL.getValue());
    Assert.assertEquals(
        "The selected standard size is not the expected",
        StandardSize.LEGAL.getValue(),
        croppingWindow.getStandardSizeComboText());
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - legalRation);
    verificationStep("Verify that dimension ratio match with the legal ratio (14/8.5)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.TABLOID);
    verificationStep("Verify that the selected size is: " + StandardSize.TABLOID.getValue());
    Assert.assertEquals(
        "The selected standard size is not the expected",
        StandardSize.TABLOID.getValue(),
        croppingWindow.getStandardSizeComboText());
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - tabloidRation);
    verificationStep("Verify that dimension ratio match with the tabloid ratio (17/11)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.clickPortrait();
    
    croppingWindow.chooseStandardSize(StandardSize.LETTER);
    verificationStep("Verify that the selected size is: " + StandardSize.LETTER.getValue());
    Assert.assertEquals(
        "The selected standard size is not the expected",
        StandardSize.LETTER.getValue(),
        croppingWindow.getStandardSizeComboText());
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - letterRation);
    verificationStep("Verify that dimension ratio match with the letter ratio (8.5/11)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.LEGAL);
    verificationStep("Verify that the selected size is: " + StandardSize.LEGAL.getValue());
    Assert.assertEquals(
        "The selected standard size is not the expected",
        StandardSize.LEGAL.getValue(),
        croppingWindow.getStandardSizeComboText());
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - legalRation);
    verificationStep("Verify that dimension ratio match with the legal ratio (8.5/14)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.TABLOID);
    verificationStep("Verify that the selected size is: " + StandardSize.TABLOID.getValue());
    Assert.assertEquals(
        "The selected standard size is not the expected",
        StandardSize.TABLOID.getValue(),
        croppingWindow.getStandardSizeComboText());
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - tabloidRation);
    verificationStep("Verify that dimension ratio match with the tabloid ratio (11/17)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.clickClose();
  }
  
  /*
   * SA-1045
   */
  //@Disabled("Skipping test testMoveHole")
  @Test
  public void testMoveHole() {
    Point startingLocation = croppingWindow.getHoleLocation();
    
    croppingWindow.moveHole(25, 10);//was 50 and 20
    
    verificationStep("Verify that the mask hole moved by the given vector");
    Assert.assertEquals("The x coordinates are not the expected", startingLocation.getX() + 25, /*here it was 50 originaly*/
        croppingWindow.getHoleLocation().getX());
    Assert.assertEquals("The y coordinates are not the expected", startingLocation.getY() + 10, /*here it was 20 originaly*/
        croppingWindow.getHoleLocation().getY());
  }
  
  /*
   * SA-1045
   */
  @Test
  public void testResizeHole() {
    croppingWindow.clickLockAspectRatioCheckbox();
    
    Dimension startingSize = croppingWindow.getHoleSize();
    
    croppingWindow.resizeHole(-50, 20);
    
    verificationStep("Verify that the mask hole resized by the given vector");
    Assert.assertEquals("The x coordinates are not the expected", startingSize.getWidth() + 50,
        croppingWindow.getHoleSize().getWidth());
    Assert.assertEquals("The y coordinates are not the expected", startingSize.getHeight() - 20,
        croppingWindow.getHoleSize().getHeight());
  }
  
  @Test
  public void testLockAspect() {

    croppingWindow.chooseStandardSize(StandardSize.LETTER);
    
    croppingWindow.clickLockAspectRatioCheckbox();
    croppingWindow.resizeHole(-10, -50);//changed from 20 100 to 10 50
    croppingWindow.moveHole(-10, -10);//changed from 20 20 to 10 10
    
    croppingWindow.clickLockAspectRatioCheckbox();
    
    Dimension dimension = croppingWindow.getHoleSize();
    double diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - letterRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (11/8.5)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.LEGAL);
    
    croppingWindow.clickLockAspectRatioCheckbox();
    croppingWindow.resizeHole(-10, -50);//changed from 20 100 to 10 50
    croppingWindow.moveHole(-10, -10);//changed from 20 20 to 10 10
    
    croppingWindow.clickLockAspectRatioCheckbox();
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - legalRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (14/8.5)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.TABLOID);
    
    croppingWindow.clickLockAspectRatioCheckbox();
    croppingWindow.resizeHole(-10, -50);//changed from 20 100 to 10 50
    croppingWindow.moveHole(-10, -10);//changed from 20 20 to 10 10
    
    croppingWindow.clickLockAspectRatioCheckbox();
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - tabloidRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (17/11)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.clickPortrait();
    croppingWindow.chooseStandardSize(StandardSize.LETTER);
    
    croppingWindow.clickLockAspectRatioCheckbox();
    croppingWindow.resizeHole(-10, -50);//changed from 20 100 to 10 50
    croppingWindow.moveHole(-10, -10);//changed from 20 20 to 10 10
    
    croppingWindow.clickLockAspectRatioCheckbox();
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - letterRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (8.5/11)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.LEGAL);
    
    croppingWindow.clickLockAspectRatioCheckbox();
    croppingWindow.resizeHole(-10, -50);//changed from 20 100 to 10 50
    croppingWindow.moveHole(-10, -10);//changed from 20 20 to 10 10
    
    croppingWindow.clickLockAspectRatioCheckbox();
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - legalRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (8.5/14)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.TABLOID);
    
    croppingWindow.clickLockAspectRatioCheckbox();
    croppingWindow.resizeHole(-10, -50);//changed from 20 100 to 10 50
    croppingWindow.moveHole(-10, -10);//changed from 20 20 to 10 10
    
    croppingWindow.clickLockAspectRatioCheckbox();
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - tabloidRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (11/17)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
  }
  
  @Test
  public void testLockedResize() {
    croppingWindow.chooseStandardSize(StandardSize.LETTER);
    
    croppingWindow.resizeHole(-20, 0);
    
    Dimension dimension = croppingWindow.getHoleSize();
    double diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - letterRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (11/8.5)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.LEGAL);
    croppingWindow.resizeHole(0, 10);
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - legalRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (14/8.5)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.TABLOID);
    croppingWindow.resizeHole(-5, -5);
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getWidth() / dimension.getHeight() - tabloidRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (17/11)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.clickPortrait();
    
    croppingWindow.chooseStandardSize(StandardSize.LETTER);
    croppingWindow.resizeHole(+10, +10);
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - letterRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (8.5/11)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.LEGAL);
    croppingWindow.resizeHole(+20, +30);
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - legalRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (8.5/14)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
    
    croppingWindow.chooseStandardSize(StandardSize.TABLOID);
    croppingWindow.resizeHole(-10, -20);
    
    dimension = croppingWindow.getHoleSize();
    diff = Math.abs(1.0 * dimension.getHeight() / dimension.getWidth() - tabloidRation);
    verificationStep("Verify that dimension ratio reset back to the letter ratio (11/17)");
    Assert.assertTrue("The dimension ration is not the expected: " + dimension, diff < 0.01);
  }
  
}
