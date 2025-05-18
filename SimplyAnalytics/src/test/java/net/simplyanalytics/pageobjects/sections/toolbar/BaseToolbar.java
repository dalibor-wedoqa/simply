package net.simplyanalytics.pageobjects.sections.toolbar;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BaseToolbar extends BasePage {
protected static final By ROOT = By.cssSelector(".sa-toolbar:not([style='display: none;'])");

	
  @SuppressWarnings("ucd")
  protected WebElement root;
  
  public BaseToolbar(WebDriver driver) {
    super(driver, ROOT);
    root = driver.findElement(ROOT);
  }
  
  @Override
  public void isLoaded() {
    
  }
}
