package net.simplyanalytics.pageobjects.sections.header.importpackage;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class LocationNotFoundWindow extends BasePage {

  protected static final By rootLocator = By.cssSelector(".sa-locations-not-found-window");
  protected WebElement root;
  
  @FindBy(css = ".sa-prompt-window-ok-btn")
  private WebElement okButton;
  
  @FindBy(xpath = "//div[contains(@class,'sa-locations-not-found-window-body-content')]//li")
  private List<WebElement> locations;
  
  @FindBy(xpath = "//div[contains(@class,'sa-prompt-window-header')]//div[contains(@class,'sa-close')]")
  private WebElement closeButton;
  
  public LocationNotFoundWindow(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(locations.get(0), "The content of the message is missing");
    waitForElementToAppear(okButton, "The ok button is missing");
  }
  
  @Step("Click on Ok")
  public void clickOk() {
    logger.debug("Click on the OK button");
    okButton.click();
    waitForElementToDisappear(root);
  }
  
  public List<String> getErrors() {
    List<String> errors = new ArrayList<String>();
    for (WebElement location : locations) {
      errors.add(location.getText());
    }
    return errors;
  }
  
  public boolean isDisplayed() {
    return isPresent(root);
  }
}
