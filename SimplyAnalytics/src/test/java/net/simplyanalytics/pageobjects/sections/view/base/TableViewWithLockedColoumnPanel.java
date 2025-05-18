package net.simplyanalytics.pageobjects.sections.view.base;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.ViewType;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class TableViewWithLockedColoumnPanel extends TableViewPanel {
  
  public TableViewWithLockedColoumnPanel(WebDriver driver, ViewType viewType) {
    super(driver, viewType);
  }
  
  public String getCellValue(DataVariable row, String columnName) {
    return getCellValue(row.getFullName(), columnName);
  }
  
  @Override
  public String getCellValue(String row, String column) {
    return getInnerText(getCellElement(row, column));
  }
  
  @Override
  protected WebElement getCellElement(String row, String column) {
    int x = scrollColumnInView(column);
    int y = scrollToRow(row);
    
    return getAllCells().stream()
        .filter(webElement -> webElement.getLocation().equals(new Point(x, y))).findAny().get();
  }
  
  @Override
  public int scrollToRow(String rowHeaderName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    
    By locator = By.xpath(".//td[contains(.," + xpathSafe(rowHeaderName) + ")]");
    while (!isChildPresent(lockedColumn, locator) && getPixelsToBottom() > 0) {
      js.executeScript("arguments[0].scrollTop += " + 500,
          tableRoot.findElement(By.cssSelector(".sa-grid-normal")));
    }
    
    if (!isChildPresent(lockedColumn, locator)) {
      throw new AssertionError("No row with the given name: " + rowHeaderName);
    }
    
    return lockedColumn.findElement(locator).getLocation().getY();
  }
  
}
