package net.simplyanalytics.pageobjects.sections.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;
import net.simplyanalytics.utils.StringParsing;

public class BarChartViewPanel extends TableViewWithoutDataVariableColoumnPanel {
  
  @FindBy(xpath = ".//div[contains(@class, 'sa-bar-chart')]//*[@class='axis-x']")
  private WebElement rootLocation;
  
  @FindBy(css = "g>text[text-anchor]")
  private List<WebElement> valueOfBars;
  
  @FindBy(css = ".tick>text")
  private List<WebElement> valueOfYAxis;
  
  @FindBy(css = ".sa-bar-chart-view-scroll")
  private WebElement barChartViewWindow;
  
  @FindBy(css = ".bar")
  private List<WebElement> bars;
  
  public BarChartViewPanel(WebDriver driver) {
    super(driver, ViewType.BAR_CHART);
    
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(barChartViewWindow, "Bar chart data window is not present");
  }
  
  /**
   * Getting locations.
   * 
   * @return list of locations
   */
  public List<Location> getLocations() {
    List<Location> result = new ArrayList<>();
    rootLocation
        .findElements(By.cssSelector(".sa-bar-chart g.axis-x g.x-axis-tick > g"))
        .stream().forEach(webElement -> {
          String name = webElement.getText();
          if (name.isEmpty()) {
            sleep(250);
            name = webElement.getText();
          }
          result.add(Location.getByName(name));
        });
    return result;
  }
  
  public List<String> getValueOfBars() {
    List<String> listValuesOfBars = new ArrayList<String>();
    getCurrentBarValues();
    for (WebElement value : valueOfBars) {
      listValuesOfBars.add(value.getText().trim());
    }
    return listValuesOfBars;
  }

  private void getCurrentBarValues() {
    valueOfBars = driver.findElements(By.cssSelector(".sa-bar-chart g>text[text-anchor]"));
  }
  
  public List<Double> getDoubleValueOfBars() {
    List<String> values = getValueOfBars();
    return values.stream().map(value -> { 
      if(value.contains("$")) {
        value =  StringParsing.parsePriceToString(value);
      }
      if(value.contains("%")) {
        return  StringParsing.parsePercentageToDouble(value);
      }
      if(value.contains("K")) {
        return Double.parseDouble(value.split("K")[0]) * 1000;
      }
      if(value.contains("M")) {
        return Double.parseDouble(value.split("M")[0]) * 1000000;
      }
      return Double.parseDouble(value);
    }).collect(Collectors.toList());
  }
  
  public List<String> getValuesOfYAxis() {
    List<String> listValuesOfAxis = new ArrayList<String>();
    for (WebElement value : valueOfYAxis) {
      listValuesOfAxis.add(value.getText().trim());
    }
    return listValuesOfAxis;
  }
  
  public int getBarHight(int index) {
    return bars.get(index).getSize().getHeight();
  }
  
  public BarDialog clickBar() {
    logger.debug("Click a Bar");
    bars.get(0).click();
    return new BarDialog(driver);
  }
  
}
