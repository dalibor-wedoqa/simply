package net.simplyanalytics.pageobjects.sections.view.base;

import net.simplyanalytics.enums.ViewType;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public abstract class TableViewWithoutDataVariableColoumnPanel extends TableViewPanel {

  public TableViewWithoutDataVariableColoumnPanel(WebDriver driver, ViewType viewType) {
    super(driver, viewType);
  }
  
  public String getCellValue(int row, String coloumn) {
    return getCellValue("" + row, coloumn);
  }
  
  @Override
  public String getCellValue(String row, String coloumn) {
    return getInnerText(getCellElement(row, coloumn));
  }
  
  @Override
  protected WebElement getCellElement(String row, String coloumn) {
    int x = scrollColumnInView(coloumn);
    int y = scrollToRow(row);
    
    return getAllCells().stream()
            .filter(webElement -> Math.abs(webElement.getLocation().getX() - x) <= 5 && Math.abs(webElement.getLocation().getY() - y) <= 5)
            .findAny().get();
  }
  
  @Override
  public int scrollToRow(String rowName) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    
    By locator = By.xpath(".//tr/td[contains(normalize-space(.), " + xpathSafe(rowName) + ")]");
    while (!isPresent(locator) && getPixelsToBottom() > 0) {
      js.executeScript("arguments[0].scrollTop += " + 500,
          tableRoot.findElement(By.cssSelector(".sa-grid-normal")));
    }
    
    if (!isChildPresent(lockedColumn, locator)) {
      throw new AssertionError("No row with the given name: " + rowName);
    }
    
    return lockedColumn.findElement(locator).getLocation().getY();
  }
  
}
