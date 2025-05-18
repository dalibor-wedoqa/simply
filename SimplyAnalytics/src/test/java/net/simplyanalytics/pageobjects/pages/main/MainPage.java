package net.simplyanalytics.pageobjects.pages.main;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.header.Header;
import net.simplyanalytics.pageobjects.sections.ldb.LdbSection;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseToolbar;
import net.simplyanalytics.pageobjects.sections.view.base.ViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class MainPage extends BasePage {

  private final ViewChooserSection viewChooserSection;
  private final Header headerSection;
  private final LdbSection ldbSection;

  @FindBy(css = ".sa-project-view:not(.sa-project-view-hidden)")
  private WebElement mainContainerElement;

  /**
   * MainPage constructor.
   * 
   * @param driver     WebDriver
   * @param ldbPresent boolean if ldb is present
   */
  public MainPage(WebDriver driver, boolean ldbPresent) {
    super(driver);
    viewChooserSection = new ViewChooserSection(driver);
    headerSection = new Header(driver);
    if (ldbPresent) {
      ldbSection = new LdbSection(driver);
    } else {
      ldbSection = null;
    }
  }

  public MainPage(WebDriver driver) {
    this(driver, true);
  }

  @Override
  public void isLoaded() {
    // isLoaded is solved with initializing
  }

  public ViewChooserSection getViewChooserSection() {
    return viewChooserSection;
  }

  public Header getHeaderSection() {
    return headerSection;
  }

  public LdbSection getLdbSection() {
    return ldbSection;
  }

  public abstract BaseToolbar getToolbar();

  public abstract ViewPanel getActiveView();

  public String addRandomBusiness() {
    return getLdbSection().getBusinessTab().addRandomBusinesses();
  }

  /**
   * Checkig if Session Expiration dialog is displayed.
   * 
   * @return Session Expiration dialog is displayed
   */
  public boolean isSessionExpiredWindowPresent() {
    try {
      waitForElementToAppear(driver.findElement(By.cssSelector(".sa-session-expired-popup-body")),
          "Session expired window did not appear.");
      return driver
          .findElement(By.xpath("//div[text()='Your session has expired due to inactivity.']"))
          .isDisplayed();
    } catch (Exception e) {
      return false;
    }

  }

}
