package net.simplyanalytics.pageobjects.sections.toolbar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.CellDisplayType;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import io.qameta.allure.Step;

public class ScarboroughCrosstabToolbar extends BaseViewToolbar {

  @FindBy(xpath = ".//div[contains(text(),'Cells Display')]/following-sibling::a[1]")
  private WebElement activeCellDisplay;
  
  @FindBy(xpath = ".//div[contains(text(),'Location:')]/following-sibling::a[1]")
  private WebElement activeLocation;
  
  @FindBy(xpath = ".//div[contains(text(),'Dataset')]/following-sibling::div[1]")
  private WebElement dataset;
  
  public ScarboroughCrosstabToolbar(WebDriver driver, ViewType nextViewType) {
  super(driver, ViewType.SCARBOROUGH_CROSSTAB_TABLE, nextViewType);
  }
  
  @Override
  public ExportWindow clickExport() {
    return (ExportWindow) clickExportButton(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
  }
  
  @Step("Open location menu")
  public DropdownToolbar openLocationMenu() {
    logger.debug("Open location list");
    activeLocation.click();
    return new DropdownToolbar(driver);
  }
  
  public String getActiveLocation() {
    return activeLocation.getText().trim();
  }
  
  public CellDisplayType getActiveCellsDisplay() {
    String nameActive = activeCellDisplay.findElement(By.cssSelector("span")).getText().trim();
    if (nameActive.contains("...")) {
      String[] nameActiveArray = nameActive.split("\n", -1);
      return CellDisplayType.getByName(nameActiveArray[0]);
    } else {
      return CellDisplayType.getByName(nameActive);
    }
  }
  
  @Step("Open Cell display menu")
  public DropdownToolbar openCellDisplayMenu() {
    logger.debug("Open cellDisplayMenu list");
    activeCellDisplay.click();
    return new DropdownToolbar(driver);
  } 
  
  public String getCurrentDataset() {
    return dataset.getText().trim();
  }
  
}
