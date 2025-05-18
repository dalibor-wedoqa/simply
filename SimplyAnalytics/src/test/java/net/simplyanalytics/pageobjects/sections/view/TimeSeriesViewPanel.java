package net.simplyanalytics.pageobjects.sections.view;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TimeSeriesViewPanel extends TableViewWithoutDataVariableColoumnPanel {
  
  @FindBy(xpath = "//div[contains(@id,\"time-series-report-grid\")]" + "//div[@class=\"sa-grid-header-normal\"]")
  private WebElement locationHeader;
  
  @FindBy(css = ".sa-grid-cell")
  private List<WebElement> tableContent;
  
  public TimeSeriesViewPanel(WebDriver driver) {
    super(driver, ViewType.TIME_SERIES);
  }	
  
  @Override
  public void isLoaded() {
    //waitForAllElementsToAppear(tableContent, "Report is not present");
  }
  
  /**
   * Getting locations.
   * 
   * @return list of locations.
   */
  public List<Location> getLocations() {
    List<Location> result = new ArrayList<>();
    locationHeader.findElements(By.cssSelector(".sa-grid-column")).stream().forEach(webElement -> {
      String name = webElement.getText();
      if (name.isEmpty()) {
        sleep(500);
        name = webElement.getText();
      }
      result.add(Location.getByName(name));
    });
    return result;
  }
  
  /**
   * Get column headers.
   * 
   * @return list of column headers
   */
  public List<String> getColumnHeaders() {
    List<String> list = new ArrayList<>();
    for (WebElement header : locationHeader.findElements(By.cssSelector("" + ".sa-grid-column"))) {
      list.add(header.getText());
    }
    return list;
  }
  
  public List<String> getTableContent() {
    List<String> listContent = new ArrayList<String>();
    for (WebElement content : tableContent) {
      listContent.add(content.getText());
    }
    return listContent;
  }
  
}
