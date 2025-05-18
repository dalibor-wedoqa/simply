package net.simplyanalytics.pageobjects.windows;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DataSheetWindow extends BasePage {
  
  protected static final By ROOT_ELEMENT = By.cssSelector(".sa-data-sheet-window");
  protected WebElement root;
  
  @FindBy(css = ".sa-close")
  private WebElement closeIcon;
  
  @FindBy(css = ".sa-simple-container-default:nth-child(1)")
  private WebElement header;
  
  @FindBy(css = ".sa-data-sheet-window-header")
  private WebElement title;
  
  @FindBy(css = "#closeBtn")
  private WebElement closeButton;
  
  public DataSheetWindow(WebDriver driver) {
    super(driver, ROOT_ELEMENT);
    root = driver.findElement(ROOT_ELEMENT);
  }
  
  @Override
  protected void isLoaded() {
    waitForElementToAppear(header, "Header should be present");
    waitForElementToAppear(title, "Title should be present");
    waitForElementToAppear(closeButton, "Close button should be present");
  }
  
  /**
   * Click on the close button (X).
   */
  @Step("Click on the close button (X)")
  public void clickClose() {
    logger.debug("Click on the close button (X)");
    closeIcon.click();
    waitForElementToDisappear(root);
  }
  
  public int getHeaderHeight() {
    return header.getSize().getHeight();
  }
  
  public int getTitleHeight() {
    return title.getSize().getHeight();
  }
  
  public int getCloseButtonWidth() {
    return header.getSize().getWidth();
  }
  
  public int getWindowWidth() {
    return root.getSize().getWidth();
  }
  
  public int getCloseButtonXLocation() {
    return header.getLocation().getX();
  }
  
  public int getWindowXLocation() {
    return root.getLocation().getX();
  }
  
  /**
   * Click on the Close button.
   */
  @Step("Click on the Close button")
  public void clickCloseButton() {
    logger.debug("Click on the Close button");
    closeButton.click();
    waitForElementToDisappear(root);
  }
  
  public boolean isDisplayed() {
    return isPresent(root);
  }
}
