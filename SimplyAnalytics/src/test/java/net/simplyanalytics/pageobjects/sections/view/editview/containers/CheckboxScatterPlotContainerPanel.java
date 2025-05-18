package net.simplyanalytics.pageobjects.sections.view.editview.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckboxScatterPlotContainerPanel extends BaseContainerPanel {
  
  @FindBy(css = ".sa-scatter-plot-attributes-panel-clear-link")
  private WebElement clearButton;
  
  @FindBy(css = ".sa-scatter-plot-attributes-panel-item")
  private List<WebElement> dataItems;
  
  @FindBy(css = ".sa-scatter-plot-attributes-panel-item-button-wrap:nth-child(1)")
  private List<WebElement> xAxisCheckbox;
  
  @FindBy(css = ".sa-scatter-plot-attributes-panel-item-button-wrap:nth-child(2)")
  private List<WebElement> yAxisCheckbox;

  private String panelName;

  public CheckboxScatterPlotContainerPanel(WebDriver driver, By root, String panelName) {
    super(driver, root);
    this.panelName = panelName;
  }

  @Override
  public void isLoaded() {
    //waitForElementToAppear(clearButton, "Clear button should present");
  }
  
  /**
   * Click on clear.
   */
  @Step("Click on the Clear button")
  public void clickClear() {
    allureStep("Click on the Clear button on panel " + panelName);
    logger.debug("Click on the Clear button on panel " + panelName);
    clearButton.click();
  }

  @Step("Click on the row data item")
  public void clickRowDataItem(int index) {
    logger.debug("Click Row checkbox beside data with index: " + index);
    xAxisCheckbox.get(index).click();
  }
  
  public boolean isXAxisCheckboxSelected(int index) {
    return xAxisCheckbox.get(index).findElement(By.cssSelector("div")).getAttribute("class").contains("sa-check-button-checked");
  }
  
  @Step("Click on the column cdata item")
  public void clickColumnDataItem(int index) {
    logger.debug("Click Column checkbox beside data with index: " + index);
    yAxisCheckbox.get(index).click();
  }
  
  public boolean isYAxisCheckboxSelected(int index) {
    return yAxisCheckbox.get(index).findElement(By.cssSelector("div")).getAttribute("class").contains("sa-check-button-checked");
  }
  
  public List<String> getAllData(){
    List<String> dataList = new ArrayList<String>();
    for(WebElement data : dataItems) {
      dataList.add(data.findElement(By.cssSelector(".sa-scatter-plot-attributes-panel-item-label")).getText());
    }
    return dataList;
  }
  
  public String getXAxisCheckedData(){
    String dataName = "";
    for(WebElement data : dataItems) {
      if (data.findElement(By.cssSelector(".sa-scatter-plot-attributes-panel-item-button-wrap:nth-child(1) div"))
          .getAttribute("class").contains("sa-check-button-checked")) {
      dataName = data.findElement(By.cssSelector(".sa-scatter-plot-attributes-panel-item-label")).getText();
      
      }
    }
    return dataName;
  }
  
  public String getYAxisCheckedData(){
    String dataName = "";
    for(WebElement data : dataItems) {
      if (data.findElement(By.cssSelector(".sa-scatter-plot-attributes-panel-item-button-wrap:nth-child(2) div"))
          .getAttribute("class").contains("sa-check-button-checked")) {
      dataName = data.findElement(By.cssSelector(".sa-scatter-plot-attributes-panel-item-label")).getText();
      }
    }
    return dataName;
  }
  
  @Override
  public List<String> getAllDisabledElements() {
    List<String> result = new ArrayList<>();
    root.findElements(By.cssSelector(".sa-edit-panel-disabled-item")).stream()
        .forEach(element -> {
          Pattern pattern = Pattern.compile("<span>(.+)<span>");
          //Matcher matcher = pattern.matcher(element.findElement(By.cssSelector(".sa-scatter-plot-attributes-panel-item-label")).getAttribute("outerHTML"));
			Matcher matcher = pattern.matcher(element.getAttribute("outerHTML"));
          if(matcher.find()) {
            result.add(matcher.group(1).trim());
          }
          else {
          result.add(element.getText());
          }
        });
    return result;
  }

}
