package net.simplyanalytics.tests;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.constants.WelcomeDialogTitle;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WelcomeTourTests extends TestBase {

  private WelcomeScreenTutorialWindow welcomeScreenTutorialWindow;

  /**
   * Signing in.
   */
  @Before
  public void before() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
  }

  @Test
  public void testGoThroughTour() {

    String title = welcomeScreenTutorialWindow.getSlideTitle();

    while (title.isEmpty()) {
      title = welcomeScreenTutorialWindow.getActiveSlideTitle();
    }

    if (!title.equals(WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2)) {
      verificationStep(
          "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_1);
      Assert.assertEquals("The title is not the expected for the slide",
          WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_1, title);
    }
    allureStep("Wait for the second slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(2);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2,
        welcomeScreenTutorialWindow.getActiveSlideTitle());
    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the third slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(3);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_3);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_3,
        welcomeScreenTutorialWindow.getActiveSlideTitle());
    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the fourth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(4);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_4);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_4,
        welcomeScreenTutorialWindow.getActiveSlideTitle());
    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the fifth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(5);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_5);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_5,
        welcomeScreenTutorialWindow.getActiveSlideTitle());
    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the sixth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(6);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_6);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_6,
        welcomeScreenTutorialWindow.getActiveSlideTitle());
    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the seventh and final slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(7);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_7);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_7,
        welcomeScreenTutorialWindow.getActiveSlideTitle());
    welcomeScreenTutorialWindow.clickGoToSimplyAnalyticsButton();
  }

  @Test
  public void testBackAndForthNavigation() {

    String title = welcomeScreenTutorialWindow.getSlideTitle();
    if (title.isEmpty() || !title.equals(WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2)) {
      allureStep("Wait for the second slide to appear");
      welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(2);
    }

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2,
        welcomeScreenTutorialWindow.getActiveSlideTitle());
    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the third slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(3);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_3);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_3,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickBack();

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2,
        welcomeScreenTutorialWindow.getActiveSlideTitle());
    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the third slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(3);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_3);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_3,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the fourth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(4);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_4);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_4,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickBack();

    allureStep("Wait for the third slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(3);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_3);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_3,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the fourth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(4);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_4);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_4,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the fifth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(5);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_5);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_5,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickBack();

    allureStep("Wait for the fourth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(4);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_4);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_4,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the fifth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(5);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_5);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_5,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the sixth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(6);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_6);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_6,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickBack();

    allureStep("Wait for the fifth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(5);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_5);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_5,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the sixth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(6);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_6);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_6,
        welcomeScreenTutorialWindow.getActiveSlideTitle());

    welcomeScreenTutorialWindow.clickNext();

    allureStep("Wait for the seventh and final slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(7);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_7);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_7,
        welcomeScreenTutorialWindow.getActiveSlideTitle());
    welcomeScreenTutorialWindow.clickGoToSimplyAnalyticsButton();
  }

  @Test
  public void testReplayTutorial() {

    String title = welcomeScreenTutorialWindow.getSlideTitle();
    if (title.isEmpty() || !title.equals(WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2)) {
      allureStep("Wait for the second slide to appear");
      welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(2);
    }

    welcomeScreenTutorialWindow.clickNext();
    allureStep("Wait for the third slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(3);

    welcomeScreenTutorialWindow.clickNext();
    allureStep("Wait for the fourth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(4);

    welcomeScreenTutorialWindow.clickNext();
    allureStep("Wait for the fifth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(5);

    welcomeScreenTutorialWindow.clickNext();
    allureStep("Wait for the sixth slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(6);

    welcomeScreenTutorialWindow.clickNext();
    allureStep("Wait for the seventh and final slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(7);

    welcomeScreenTutorialWindow.clickReplayTutorial();
    allureStep("Wait for the second slide to appear");
    welcomeScreenTutorialWindow.waitForSlideWithGivenNumberToAppear(2);

    verificationStep(
        "Verify that the dialog title is: " + WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2);
    Assert.assertEquals("The title is not the expected for the slide",
        WelcomeDialogTitle.WELCOME_DIALOG_SLIDE_2,
        welcomeScreenTutorialWindow.getActiveSlideTitle());
  }
}
