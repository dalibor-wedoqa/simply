package net.simplyanalytics.pageobjects.sections.header.importpackage;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;

import io.qameta.allure.Step;

public class SuccessfulDialog extends BasePage {

  protected static final By rootLocator = By.cssSelector(".sa-import-success-window");
  protected WebElement root;

  @FindBy(css = ".sa-prompt-window-ok-btn")
  private WebElement okButton;

  @FindBy(css= "use")
  private WebElement closeIcon;

  @FindBy(css = "div.sa-prompt-window-body")
  private WebElement content;

  public SuccessfulDialog(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(closeIcon, "The close icon is missing");
    waitForElementToAppear(content, "The content of the message is missing");
    waitForElementToAppear(okButton, "The ok button is missing");
  }

  public List<String> getProjects() {
    return Arrays.asList(content.getText().split(System.lineSeparator()));
  }

  /**
   * Click on the OK button.
   */
  @Step("Click on the OK button")
  public MapPage clickOkButton() {
    logger.debug("Click on the OK button");
    okButton.click();
    waitForElementToDisappear(root);
    return new MapPage(driver);
  }

  /**
   * Click on the close button (x icon).
   */
  @Step("Click on the close button (x icon)")
  public void clickClose() {
    logger.debug("Click on the close button (x icon)");
    closeIcon.click();
    waitForElementToDisappear(root);
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }
}
