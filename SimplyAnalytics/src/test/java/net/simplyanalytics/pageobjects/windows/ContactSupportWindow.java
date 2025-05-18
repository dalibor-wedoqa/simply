package net.simplyanalytics.pageobjects.windows;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ContactSupportWindow extends BasePage {

  protected static final By rootElement = By
      .cssSelector("div[data-embed='ticketSubmissionForm']");
  protected WebElement root;
  
  private static final By title = By
      .cssSelector("h1");

  @FindBy(css = "h1")
  private WebElement contactSupportTitle;
  
  @FindBy(xpath = "//button[contains(@data-garden-id,'buttons.icon_button')]")
  private WebElement cancelButton;

  public ContactSupportWindow(WebDriver driver) {
    super(driver, rootElement);
    this.root = driver.findElement(rootElement);    
  }

  @Override
  public void isLoaded() {
    waitForElementToAppearWithCustomTime(title, "Contact support title is not loaded", 70);
  }

  public String getContactSupportTitle() {
    logger.trace("Get title");
    return contactSupportTitle.getText();
  }
  
  @Step("Click on the Cancel button")
  public void clickCloseButton() {
    logger.debug("Click on the Cancel button");
    cancelButton.click();
    
    //This line is not necessary for GC or MS edge and make also this line make a problem for firefox browsers
    //waitForElementToDisappear(root);
    driver.switchTo().defaultContent();
  }
  
}
