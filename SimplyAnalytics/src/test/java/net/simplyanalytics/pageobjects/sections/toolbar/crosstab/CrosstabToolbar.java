package net.simplyanalytics.pageobjects.sections.toolbar.crosstab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.CellDisplayType;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.DropdownToolbar;
import io.qameta.allure.Step;

public class CrosstabToolbar extends BaseViewToolbar {

    @FindBy(xpath = ".//div[contains(text(),'Cells Display')]/following-sibling::a[1]")
    private WebElement activeCellDisplay;
    
    @FindBy(xpath = ".//div[contains(text(),'Location:')]/following-sibling::a[1]")
    private WebElement activeLocation;
    
    @FindBy(xpath = ".//div[contains(text(),'Current Dataset')]/following-sibling::div[1]")
    private WebElement currentDataset;
  
    public CrosstabToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.CROSSTAB_TABLE, nextViewType);
    }
    
    @Override
    public ExportWindow clickExport() {
      return (ExportWindow) clickExportButton(ViewType.CROSSTAB_TABLE);
    }
    
    @Step("Open location menu")
    public DropdownToolbar openLocationMenu() {
      logger.debug("Open location list");
      activeLocation.click();
      return new DropdownToolbar(driver);
    }
    
    public Location getActiveLocation() {
      return Location.getByName(activeLocation.getText());
    }
    
    public String getCurrentDataset() {
      return currentDataset.getText();
    }
    
    public CellDisplayType getActiveCellsDisplay() {
        String nameActive = activeCellDisplay.findElement(By.cssSelector("span")).getText().trim();
        if (nameActive.contains("...")){
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
}
