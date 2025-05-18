package net.simplyanalytics.pageobjects.sections.view;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RelatedDataReportViewPanel extends TableViewWithoutDataVariableColoumnPanel {

  @FindBy(css = ".sa-grid-header-normal")
  private WebElement locationHeader;

  @FindBy(css = "div.sa-report-grid table[id*='lockedTable'].sa-grid-table")
  private WebElement dataVariablesColumn;

  public RelatedDataReportViewPanel(WebDriver driver) {
    super(driver, ViewType.RELATED_DATA);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(dataVariablesColumn, "Related data table is not loaded");
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
   * Get data variables.
   * 
   * @return list of data variables
   */
  public List<DataVariable> getDataVariables() {
    List<DataVariable> result = new ArrayList<>();
    dataVariablesColumn.findElements(By.cssSelector(".sa-report-grid-row-header")).stream().forEach(
        webElement -> result.add(DataVariable.getByFullName(webElement.getAttribute("innerHTML"))));
    return result;
  }

}
