package net.simplyanalytics.pageobjects.sections.ldb.data.byimport;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BaseImportPanel extends BasePage {
  
  private ImportsHeaderPanel importHeaderPanel;
  private ImportsResultListPanel importResultListPanel;
  private ImportsSearchPanel importSearchPanel;
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-imports-data-browse-window");
  protected WebElement root;
  
  public BaseImportPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
    importHeaderPanel = new ImportsHeaderPanel(driver);
    importResultListPanel = new ImportsResultListPanel(driver);
    importSearchPanel = new ImportsSearchPanel(driver);
  }
  
  @Override
  public void isLoaded() {
    
  }
  
  public String getTitle() {
    return importHeaderPanel.getTitle();
  }
  
  public String clickRandomData() {
    return importResultListPanel.clickRandomData();
  }
}
