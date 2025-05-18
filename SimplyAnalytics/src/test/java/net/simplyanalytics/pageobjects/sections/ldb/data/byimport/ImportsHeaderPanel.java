package net.simplyanalytics.pageobjects.sections.ldb.data.byimport;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ImportsHeaderPanel extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-imports-data-browse-window-header");
  protected WebElement root;
  
  @FindBy(css = ".sa-imports-data-browse-window-title")
  private WebElement title;
  
  @FindBy(xpath = "//div[contains(@class,'sa-imports-data-browse-window-header')]//div[contains(@class,'sa-close')]")
  private WebElement closeButton;
  
  public ImportsHeaderPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
    
  }
  
  @Step("Get title")
  protected String getTitle() {
    return title.getText();
  }
  
}
