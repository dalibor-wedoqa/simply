package net.simplyanalytics.pageobjects.sections.viewchooser;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class RenameViewPanel extends BasePage {
  
  public static final By ROOT = By.cssSelector("div[class^='x-panel sa-arrow-menu']");
  
  @FindBy(css = "div[class^='x-panel sa-arrow-menu'] input")
  private WebElement input;
  
  public RenameViewPanel(WebDriver driver) {
    super(driver, ROOT);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(input, "The input field is not present");
  }
  
  /**
   * Enter new name for the view.
   * @param newName new view name
   */
  @Step("Enter new name for the view: \"{0}\"")
  public void enterText(String newName) {
    logger.debug("Enter new name for the view: " + newName);
    input.click();
    input.clear();
    input.sendKeys(newName);
    input.sendKeys(Keys.ENTER);
    sleep(500);
  }
}
