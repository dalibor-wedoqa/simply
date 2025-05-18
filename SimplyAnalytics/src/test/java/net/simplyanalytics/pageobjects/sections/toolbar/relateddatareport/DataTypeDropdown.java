package net.simplyanalytics.pageobjects.sections.toolbar.relateddatareport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.simplyanalytics.enums.DataType;
import net.simplyanalytics.pageobjects.base.BasePage;

public class DataTypeDropdown extends BasePage {
  
  private static final By rootElement = By
      .cssSelector(".x-panel:not([style*='visibility: hidden'])");
  private WebElement root;
  
  public DataTypeDropdown(WebDriver driver) {
    super(driver, rootElement);

    root = driver.findElement(rootElement);
  }
  
  @Override
  public void isLoaded() {
  }
  
  public void clickOnDataType(DataType dataType) {
    logger.debug("Click on the datatype: " + dataType);
    String dataTypeName = dataType.getType().toLowerCase();
    if (dataType == DataType.ALL) {
      dataTypeName = dataType.getType();
    }
    
    waitForElementToBeClickable(root.findElement(By.xpath(".//a[@class='x-menu-item-link'][.//span[normalize-space(.)='" 
        + dataTypeName + "']]")), "Data type: " + dataType + " is not present").click();
    waitForLoadingToDisappear();
  }
}
