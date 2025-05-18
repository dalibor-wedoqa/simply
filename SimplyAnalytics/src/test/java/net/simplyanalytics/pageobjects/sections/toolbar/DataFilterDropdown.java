package net.simplyanalytics.pageobjects.sections.toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.view.BarChartViewPanel;
import net.simplyanalytics.pageobjects.sections.view.TimeSeriesViewPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.EditTimeSeriesPanel;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DataFilterDropdown extends BasePage {
  
  @FindBy(css = ".x-panel .sa-arrow-ct")
  private WebElement dropdownWindow;
  
  @FindBy(css = ".x-menu-item-text")
  private List<WebElement> elements;
  
  @FindBy(css = ".x-menu-item-checked")
  private List<WebElement> checkedElements;
  
  @FindBy(xpath = "//table[contains(@id,'time-series-report-grid')]" + "//table[contains(@id,'normalTable')]")
  private WebElement timeSeriesReport;
  
  public DataFilterDropdown(WebDriver driver) {
    super(driver);
    // TODO Auto-generated constructor stub
  }
  
  @Override
  public void isLoaded() {
  }
  
  public List<WebElement> getElements() {
    return elements;
  }
  
  @Step("Put the webelement to String")
  public List<String> getTextOfElements() {
    List<String> toStringElements = new ArrayList<String>();
    for (WebElement element : elements) {
      toStringElements.add(element.getText());
    }
    return toStringElements;
  }
  
  public List<WebElement> getActiveWindowElements() {
    List<WebElement> list = new ArrayList<WebElement>();
    for (WebElement webElement : elements) {
      if (webElement.isDisplayed()) {
        list.add(webElement);
      }
    }
    return list;
  }
  
  public WebElement getCheckedElement() {
    for (WebElement webElement : checkedElements) {
      if (webElement.isDisplayed()) {
        return webElement;
      }
    }
    return null;
  }
  
  @Step("Click a random data to be active")
  public TableViewWithoutDataVariableColoumnPanel clickRandomFilter(ViewType view) {
    List<WebElement> list = getActiveWindowElements();
    int index = new Random().nextInt(list.size());
    while (list.get(index).getText().equals(getCheckedElement().getText())) {
      index = new Random().nextInt(list.size());
    }
    logger.debug("Click on random filter: " + list.get(index).getText().trim());
    waitForElementToBeClickable(list.get(index), "Element is not clickable");
    list.get(index).click();
    // previousIndex=index;
    sleep(2000);
    switch (view) {
      case TIME_SERIES:
        return new TimeSeriesViewPanel(driver);
      case BAR_CHART:
        return new BarChartViewPanel(driver);
      default:
        throw new AssertionError("View type is not supported");
    }
  }
  
  public TableViewWithoutDataVariableColoumnPanel clickOnFilter(String filter, ViewType view) {
    logger.debug("Click on the filter: " + filter);
    waitForElementToBeClickable(driver.findElement(
        By.xpath(".//a[@class=\"x-menu-item-link\"][.//span[normalize-space(.)=\"" + filter + "\"]]")), "Element is not clickable").click();
    switch (view) {
      case TIME_SERIES:
        return new TimeSeriesViewPanel(driver);
      case BAR_CHART:
        return new BarChartViewPanel(driver);
      default:
        throw new AssertionError("View type is not supported");
    }
  }
  @Step("Click a random data to be active")
  public EditTimeSeriesPanel clickRandomFilterEditBaseTimeSeries(ViewType view) {
    List<WebElement> list = getActiveWindowElements();
    int index = new Random().nextInt(list.size());
    while (list.get(index).getText().equals(getCheckedElement().getText())) {
      index = new Random().nextInt(list.size());
    }
    logger.debug("Click on random filter: " + list.get(index).getText().trim());
    waitForElementToBeClickable(list.get(index), "Element is not clickable");
    list.get(index).click();
    // previousIndex=index;
    sleep(2000);
    switch (view) {
      case TIME_SERIES:
        return new EditTimeSeriesPanel(driver);
      default:
        throw new AssertionError("View type is not supported");
    }
  }
}
