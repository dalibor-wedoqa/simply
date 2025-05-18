package net.simplyanalytics.pageobjects.sections.view.base;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class ViewPanel extends BasePage {

  protected static final By ACTIVE_VIEW = By.cssSelector(".sa-project-view:not(.sa-project-view-hidden)");
  protected WebElement root;

  public ViewPanel(WebDriver driver) {
    super(driver, ACTIVE_VIEW);
    root = driver.findElement(ACTIVE_VIEW);
  }

  @Override
  public void isLoaded() {
    waitForAllElementsToAppearByLocator(ACTIVE_VIEW, "There is no active ViewPanel");
  }

}
