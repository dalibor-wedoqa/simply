package net.simplyanalytics.pageobjects.sections.ldb.data.byimport;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ImportsSearchPanel extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-imports-data-browse-window-search-field");
  protected WebElement root;
  
  @FindBy(xpath = "//div[contains(@class,'sa-imports-data-browse-window-search-field')]//input[contains(@class,'sa-text-field-input')]")
  private WebElement inputField;
  
  @FindBy(xpath = "//div[contains(@class,'sa-imports-data-browse-window-search-field')]//a[contains(@class,'sa-text-field-clear')]")
  private WebElement clearInputField;
  
  public ImportsSearchPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
    
  }
}
