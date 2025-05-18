package net.simplyanalytics.pageobjects.sections.ldb.locations;

import java.util.Arrays;
import java.util.List;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

/**
 * .
 * 
 *
 * @author wedoqa
 */
public class DeleteAlert extends BasePage {

  protected static final By rootLocator = By.cssSelector(".sa-prompt-window-default");
  protected WebElement root;

  @FindBy(css = ".sa-prompt-window-ok-btn")
  private WebElement okButton;

  @FindBy(xpath= "//div[contains(@class, 'sa-prompt-window-header')]//button[contains(@class, 'sa-close')]")
  private WebElement closeIcon;

  @FindBy(css = "div.sa-prompt-window-body ul")
  private WebElement content;

  public DeleteAlert(WebDriver driver) {
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
  public void clickOkButton() {
    logger.debug("Click on the OK button");
    okButton.click();
    waitForElementToDisappear(root);
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
