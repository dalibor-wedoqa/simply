package net.simplyanalytics.pageobjects.sections.header;

import java.util.Set;

import net.simplyanalytics.enums.ModeIndicator;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.pages.support.DataDocumentationPage;
import net.simplyanalytics.pageobjects.pages.support.ReplayTutorialPage;
import net.simplyanalytics.pageobjects.pages.support.SupportHelpCenterPage;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ImportSection;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ManageWindow;
import net.simplyanalytics.pageobjects.sections.header.windows.ChangeEmailAddressWindow;
import net.simplyanalytics.pageobjects.sections.header.windows.ChangePasswordWindow;
import net.simplyanalytics.pageobjects.windows.ContactSupportWindow;
import net.simplyanalytics.pageobjects.windows.ImportTermsWindow;
import net.simplyanalytics.pageobjects.windows.IntroToSimplyAnalyticsWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class Header extends BasePage {

  protected static final By USER_DROPDOWN_ROOT_LOCATOR = By
      .cssSelector(".x-menu:not([style*='hidden'])");

  @FindBy(css = ".sa-page-header-project-name-wrapper")
  private WebElement blankSpace;

  @FindBy(css = ".sa-sidebar-logo.sa-sidebar-logo-light")
  private WebElement exitViewActions;

  @FindBy(css = ".sa-page-header-new-button")
  private WebElement newProjectButton;

  @FindBy(css = ".sa-page-header-open-button")
  private WebElement openProjectButton;

  @FindBy(css = ".sa-page-header-manage-button")
  private WebElement manageProjectButton;

  @FindBy(css = ".sa-page-header-support-button")
  private WebElement supportButton;

  @FindBy(css = ".sa-page-header-username-button")
  private WebElement userButton;

  @FindBy(css = ".sa-page-header-project-name")
  private WebElement currentProjectButtonElement;

  @FindBy(css = ".sa-sidebar-collapse-btn-inner")
  private WebElement collapseButton;
  
  @FindBy(css = ".sa-mode-indicator-text")
  private WebElement modeIndicator;

  public Header(WebDriver driver) {
    super(driver);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(newProjectButton, "Create Project button is not loaded");
    waitForElementToAppear(openProjectButton, "Open Project button is not loaded");
    waitForElementToAppear(manageProjectButton, "Manage Project button is not loaded");
    waitForElementToAppear(supportButton, "Support button is not loaded");
    findUserButton();
    waitForElementToAppear(userButton, "User button is not loaded");
    waitForElementToAppear(collapseButton, "Collapse button is not loaded");

  }

  @Step("Click on the << button (collapse LDB section)")
  public void clickCollapseButton() {
    logger.debug("Click on the << button (collapse LDB section)");
    collapseButton.click();
  }
  
  private void findUserButton() {
    userButton = driver.findElement(By.cssSelector(".sa-page-header-username-button"));
  }

  /**
   * . This method clicks on the name of the current project in order to open
   * dropdown menu for renaming the project
   * 
   * @return a RenameProjectDropdown object.
   */

  @Step("Click on the project name to open rename menu")
  public RenameProjectDropdown clickCurrentProjectName() {
    logger.debug("Click on the project name to open rename menu");
    currentProjectButtonElement.click();
    return new RenameProjectDropdown(driver);
  }

  public class RenameProjectDropdown extends BasePage {

    @FindBy(css = ".sa-rename-menu-input>input")
    private WebElement inputRenameFieldElement;

    public RenameProjectDropdown(WebDriver driver) {
      super(driver);
    }

    @Override
    public void isLoaded() {
      waitForElementToAppear(inputRenameFieldElement, "Rename dropdown is not loaded");
    }

    /**
     * . This method enters the new name of the project
     */
    @Step("Enter the new project name: \"{0}\"")
    private void enterNewName(String name) {
      logger.debug("Enter the new project name: " + name);
      inputRenameFieldElement.click();
      inputRenameFieldElement.clear();
      inputRenameFieldElement.sendKeys(name + Keys.ENTER);
      waitForElementToDisappear(inputRenameFieldElement);
    }
  }

  public String getNameOfCurrentProject() {
    return currentProjectButtonElement.getText();
  }

  /**
   * Click on the New Project button.
   * 
   * @return NewProjectLocationWindow
   */
  @Step("Click on the New Project button")
  public NewProjectLocationWindow clickNewProject() {
    logger.debug("Click on the New Project button");
    newProjectButton.click();
    return new NewProjectLocationWindow(driver);
  }

  /**
   * Click on the Open Project button.
   * 
   * @return OpenProjectDropdown
   */
  @Step("Click on the Open Project button")
  public OpenProjectDropdown clickOpenProject() {
    logger.debug("Click on the Open Project button");
    openProjectButton.click();
    return new OpenProjectDropdown(driver);
  }

  public class OpenProjectDropdown extends BasePage {

    private WebElement openProjectDropdownElement;

    @FindBy(css = ".sa-open-project-menu")
    private WebElement openProjectDropdown;

    public OpenProjectDropdown(WebDriver driver) {
      super(driver);
    }

    @Override
    public void isLoaded() {
      waitForElementToAppear(openProjectDropdown, "Open Project Dropdown is  not loaded");
    }

    /**
     * Click on the project by project name.
     * 
     * @param projectName project name
     * @return ManageProjectPage
     */
    @Step("Click on the project \"{0}\"")
    public ComparisonReportPage clickToOpenProjectWithGivenName(String projectName) {
      logger.debug("Click on the project " + projectName);
      openProjectDropdownElement = driver.findElement(
          By.xpath("//div[@class='sa-open-project-menu-item-name'][text()='" + projectName + "']"));
      waitForElementToAppear(openProjectDropdownElement, "There is no project with that Name");
      openProjectDropdownElement.click();
      return new ComparisonReportPage(driver);
    }
  }

  /**
   * Check if Manage Project button is blue.
   * 
   * @return result
   */
  public boolean isManageProjectButtonBlue() {
    String blueColor = "rgba(23, 121, 232, 1)";
    String blueColorFf = "rgb(23, 121, 232)";
    String colorOfManageProjectButton = manageProjectButton.getCssValue("background-color");
    return blueColor.equalsIgnoreCase(colorOfManageProjectButton) || blueColorFf.equalsIgnoreCase(colorOfManageProjectButton);
  }

  /**
   * Click on the Manage Project button.
   * 
   * @return ManageProjectPage
   */
  @Step("Click on the Project Settings button")
  public ProjectSettingsPage clickProjectSettings() {
    logger.debug("Click on the Project Settings button");
    waitForElementToAppear(manageProjectButton, "the button is not clickable").click();
    return new ProjectSettingsPage(driver);
  }

  /**
   * Click on the Support button.
   * 
   * @return SupportDropdown
   */
  @Step("Click on the Support button")
  public SupportDropdown clickSupport() {
    logger.debug("Click on the Support button");
    supportButton.click();
    return new SupportDropdown(driver);
  }

  public class SupportDropdown extends BasePage {
    private WebElement openSupportDropdownElement;

    @FindBy(css = ".sa-page-header-support-button")
    private WebElement openSupportDropdown;

    public SupportDropdown(WebDriver driver) {
      super(driver);
    }

    @Override
    public void isLoaded() {
      waitForElementToAppear(openSupportDropdown, "Open Support Dropdown is  not loaded");
    }

    /**
     * Click on the Support Help Center.
     * 
     * @return SupportHelpCenterPage
     */
    @Step("Click on the Support Help Center")
    public SupportHelpCenterPage clickToOpenSupportHelpCenter() {
      logger.debug("Click on the Support drop-down.");
      openSupportDropdownElement = driver
          .findElement(By.xpath("//span[@class='x-menu-item-text'][text()='Help Center']"));
      waitForElementToAppear(openSupportDropdownElement,
          "There is no support  help center " + "element");
      logger.debug("Click on the Support Help Center option");
      openSupportDropdownElement.click();
      switchToWindow();
      return new SupportHelpCenterPage(driver);
    }

    private void switchToWindow() {
      sleep(3000);
      String originalWindowHandle = driver.getWindowHandle();
      Set<String> windowHandles = driver.getWindowHandles();
      logger.debug("Switching to the new tab.");
      for (String window : windowHandles) {
        if (!window.equals(originalWindowHandle)) {
          driver.switchTo().window(window);
        }
      }
    }

    /**
     * Click on the Contact Support.
     * 
     * @return ContactSupportWindow
     */
    @Step("Click on the project \"{0}\"")
    public ContactSupportWindow clickToOpenContactSupport() {
      logger.debug("Click on the Contact Support ");
      openSupportDropdownElement = driver
          .findElement(By.xpath("//span[@class='x-menu-item-text'][text()='Contact Support']"));
      waitForElementToAppear(openSupportDropdownElement,
          "There is no Contact Support " + "element");
      openSupportDropdownElement.click();
      switchToIframe();
      return new ContactSupportWindow(driver);
    }

    private void switchToIframe() {
      driver.switchTo().frame(driver.findElement(By.id("webWidget")));

    }

    /**
     * Click on the Data Documentation.
     * 
     * @return DataDocumentationPage
     */
    @Step("Click on the project \"{0}\"")
    public DataDocumentationPage clickToOpenDataDocumentation() {
      logger.debug("Click on the Data Documentation");
      openSupportDropdownElement = driver
          .findElement(By.xpath("//span[@class='x-menu-item-text'][text()='Data Documentation']"));
      waitForElementToAppear(openSupportDropdownElement,
          "There is no Data Documentation" + "element");
      openSupportDropdownElement.click();
      return new DataDocumentationPage(driver);
    }

    /**
     * Click on the Replay Tutorial.
     * 
     * @return ReplayTutorialPage
     */
    @Step("Click on the project \"{0}\"")
    public ReplayTutorialPage clickToOpenReplayTutorial() {
      logger.debug("Click on the Replay Tutorial");
      openSupportDropdownElement = driver
          .findElement(By.xpath("//span[@class='x-menu-item-text'][text()='Replay Tutorial']"));
      waitForElementToAppear(openSupportDropdownElement,
          "There is no Replay Tutorial element");
      openSupportDropdownElement.click();
      return new ReplayTutorialPage(driver);
    }
    
    /**
     * Click on the Download Training Guide.
     * 
     */
    @Step("Click on the project \"{0}\"")
    public void clickToDownloadTrainingGuide() {
      logger.debug("Click on the Download Training Guide");
      openSupportDropdownElement = driver
          .findElement(By.xpath("//span[@class='x-menu-item-text'][text()='Download Training Guide']"));
      waitForElementToAppear(openSupportDropdownElement,
          "There is no Download Training Guide element");
      openSupportDropdownElement.click();
    }

    /**
     * Click on the Intro to SimplyAnalytics.
     * 
     * @return IntroToSimplyAnalyticsWindow
     */
    @Step("Click on the project \"{0}\"")
    public IntroToSimplyAnalyticsWindow clickToOpenIntroToSimplyAnalytics() {
      logger.debug("Click on the Intro to SimplyAnalytics");
      openSupportDropdownElement = driver.findElement(
          By.xpath("//span[@class='x-menu-item-text'][text()='Intro to SimplyAnalytics (5:12)']"));
      waitForElementToAppear(openSupportDropdownElement,
          "There is no Intro to SimplyAnalytics" + "element");
      openSupportDropdownElement.click();
      switchToVideoPlayerIframe();
      waitForElementToAppearByLocator(By.cssSelector("#player"),
          "Intro To Simply Analytics Window not appear");
      sleep(1000);
      return new IntroToSimplyAnalyticsWindow(driver);
    }

    private void switchToVideoPlayerIframe() {
      WebElement iFrame = waitForElementToAppearByLocator(By.cssSelector(".sa-video-window-iframe"),
          "Frame is not appear");
      waitForElementToStop(iFrame);
      driver.switchTo().frame(iFrame);
    }
  }

  /**
   * Click on the user button.
   * 
   * @return UserDropdown
   */
  @Step("Click on the user button")
  public UserDropdown clickUser() {
    logger.debug("Click on the user button");
    waitForElementToBeClickable(userButton, "The User button is not clickable").click();
    return new UserDropdown(driver);
  }

  public class UserDropdown extends BasePage {

    @FindBy(xpath = ".//a[.//span[normalize-space(.)='Sign Out']]")
    private WebElement signOutButton;

    @FindBy(xpath = ".//a[.//span[normalize-space(.)='Import Data…']]")
    private WebElement importButton;

    //@FindBy(xpath = "//span[text()='Manage Imported Data']/../..")   
    @FindBy(xpath = ".//a[.//span[normalize-space(.)='Manage Imported Data']]")
    private WebElement manageImportButton;
    
    @FindBy(xpath = ".//a[.//span[normalize-space(.)='Change Email Address…']]")
    private WebElement changeEmailButton;
    
    @FindBy(xpath = ".//a[.//span[normalize-space(.)='Change Password…']]")
    private WebElement changePasswordButton;

    public UserDropdown(WebDriver driver) {
      super(driver, USER_DROPDOWN_ROOT_LOCATOR);
    }

    @Override
    public void isLoaded() {
      waitForElementToAppear(signOutButton, "The Sign Out button not appeared");
    }

    /**
     * Click on the Import button.
     */
    @Step("Click on the Import button")
    public ImportSection clickImport() {
      logger.debug("Click on the Import button");
      importButton.click();
      return new ImportSection(driver);
      // TODO verify that https://simplyanalytics.com/ loads correctly (implement a
      // new page object for it with isLoaded)
    }
    
    /**
     * Click on the Import button.
     */
    @Step("Click on the Import button")
    public ImportTermsWindow clickImportWithImportTerms() {
      logger.debug("Click on the Import button");
      importButton.click();
      return new ImportTermsWindow(driver);
      // TODO verify that https://simplyanalytics.com/ loads correctly (implement a
      // new page object for it with isLoaded)
    }

    /**
     * Click on the Manage Import button.
     */
    @Step("Click on the Manage Import button")
    public ManageWindow clickManageImport() {
      logger.debug("Click on the Manage Import button");
      manageImportButton.click();
      return new ManageWindow(driver);
    }
    
    /**
     * Click on the Change email address button.
     */
    @Step("Click on the Change Email Address button")
    public ChangeEmailAddressWindow clickChangeEmailAddressButton() {
      logger.debug("Click on the Change email address button");
      changeEmailButton.click();
      return new ChangeEmailAddressWindow(driver);
    }
    
    /**
     * Click on the Change password button.
     */
    @Step("Click on the Change Password button")
    public ChangePasswordWindow clickChangePasswordButton() {
      logger.debug("Click on the Change password button");
      changePasswordButton.click();
      return new ChangePasswordWindow(driver);
    }

    /**
     * Verify is there imported data to modify.
     */
    @Step("Click on the Manage Import button")
    public boolean verfiyManageImport() {
      logger.debug("Verify is there imported data to modify");
      if (manageImportButton.isEnabled()) {
        return false;
      } else {
        return true;
      }
    }

    /**
     * Click on the Sign Out button.
     */
    @Step("Click on the Sign Out button")
    public void clickSignOut() {
      logger.debug("Click on the Sign Out button");
      signOutButton.click();
      // TODO verify that https://simplyanalytics.com/ loads correctly (implement a
      // new page object for it with isLoaded)
    }
  }

  @Step("Rename the actual project")
  public void renameProjectName(String newName) {
    RenameProjectDropdown renameWindow = clickCurrentProjectName();
    renameWindow.enterNewName(newName);
  }

  @Step("Click on a blank, empty space")
  public void clickOnBlankSpace() {
    logger.debug("Click on a blank, empty space");
    blankSpace.click();
  }

  @Step("Click on a blank, empty space")
  public void exitViewActions() {
    logger.debug("Click on a blank, empty space");
    exitViewActions.click();
  }
  
  public ModeIndicator getModeIndicator() {
    return ModeIndicator.getModeIndicatorByName(modeIndicator.getText().trim());
  }

}