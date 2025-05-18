package net.simplyanalytics.pageobjects.sections.view.editview.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class CheckboxCrosstabContainerPanel extends BaseContainerPanel {
  
  @FindBy(xpath = ".//div[normalize-space(.)='Clear']")
  private WebElement clearButton;
  
  @FindBy(css = ".sa-scarborough-crosstab-attributes-panel-clear-link")
  private WebElement clearScarboroughButton;
  
  @FindBy(xpath = ".//div[normalize-space(.)='Select all']")
  private WebElement selectAllButton;
  
  @FindBy(css = ".sa-scarborough-crosstab-attributes-panel-select-all-link")
  private WebElement selectAllScarboroughButton;
  
  @FindBy(css = ".sa-crosstab-attributes-panel-item")
  private List<WebElement> dataItems;
  
  @FindBy(css = ".sa-crosstab-attributes-panel-item-row-button-wrap div")
  private List<WebElement> rowCheckbox;
  
  @FindBy(css = ".sa-crosstab-attributes-panel-item-column-button-wrap div")
  private List<WebElement> columnCheckbox;
  
  private String panelName;
  
  public CheckboxCrosstabContainerPanel(WebDriver driver, By root, String panelName) {
    super(driver, root);
    this.panelName = panelName;
  }
  
  @Override
  public void isLoaded() {
//    waitForElementToAppear(selectAllButton, "Select all button should appear");
//    waitForElementToAppear(clearButton, "Clear button should appear");
  }
  
  /**
   * Click on select all.
   */
  @Step("Click on the Select All button")
  public void clickSelectAll() {
    allureStep("Click on the Select All button on panel " + panelName);
    logger.debug("Click on the Select All button on panel " + panelName);
    selectAllButton.click();
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

  @Step("Select the Row data item checkbox button")
  public void clickRowDataItem(int index) {
    logger.debug("Click Row checkbox beside data with index: " + index);
    rowCheckbox.get(index).click();
  }

  @Step("Select the Column data item checkbox button")
  public void clickColumnDataItem(int index) {
    logger.debug("Click Column checkbox beside data with index: " + index);
    columnCheckbox.get(index).click();
  }

  public boolean isRowCheckboxSelected(int index) {
    return rowCheckbox.get(index).getAttribute("class").contains("sa-check-button-checked");
  }

  public boolean isRowCheckboxSelected(String dataName) {
    List<WebElement> datas = dataItems.stream()
        .filter(data -> data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText().trim().contains(dataName))
        .collect(Collectors.toList());
        WebElement data = datas.get(0).findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-row-button-wrap .sa-check-button"));
        return data.getAttribute("class").contains("sa-check-button-checked");
  }

  public boolean isColumnCheckboxSelected(int index) {
    System.out.println("The checkbox is checked: ");
    System.out.println(columnCheckbox.get(index).getAttribute("class").contains("sa-check-button-checked"));
    return columnCheckbox.get(index).getAttribute("class").contains("sa-check-button-checked");
  }

  public boolean isColumnCheckboxSelected(String dataName) {
    List<WebElement> datas = dataItems.stream()
        .filter(data -> data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText().trim().contains(dataName))
        .collect(Collectors.toList());
    WebElement data = datas.get(0).findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-column-button-wrap .sa-check-button"));
    return data.getAttribute("class").contains("sa-check-button-checked");
  }
  
  public List<String> getAllData(){
    List<String> dataList = new ArrayList<String>();
    for(WebElement data : dataItems) {
      if(!data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getAttribute("class").contains("x-item-disabled"))
        dataList.add(data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText());
    }
    return dataList;
  }
  
  public List<String> getAllCheckedDataRows(){
    List<String> dataList = new ArrayList<String>();
    for(WebElement data : dataItems) {
      if (data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-row-button-wrap div"))
          .getAttribute("class").contains("sa-check-button-checked")) {
      dataList.add(data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText());
      
      }
    }
    return dataList;
  }
  
  public List<String> getAllCheckedDataColumns(){
    List<String> dataList = new ArrayList<String>();
    for(WebElement data : dataItems) {
      if (data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-column-button-wrap div"))
          .getAttribute("class").contains("sa-check-button-checked")) {
      dataList.add(data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText());
      }
    }
    return dataList;
  }
  
}
