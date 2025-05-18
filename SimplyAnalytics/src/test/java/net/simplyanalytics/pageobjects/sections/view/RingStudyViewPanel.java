package net.simplyanalytics.pageobjects.sections.view;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithLockedColoumnPanel;

import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RingStudyViewPanel extends TableViewWithLockedColoumnPanel {
  
  @FindBy(css = "div.sa-report-grid")
  protected WebElement reportGridElement;
  
  @FindBy(css = "div.sa-report-grid table[id*='lockedTable'].sa-grid-table")
  private WebElement dataVariablesColumn;
  
  public RingStudyViewPanel(WebDriver driver) {
    super(driver, ViewType.RING_STUDY);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(reportGridElement, "Report grid is not loaded");
  }
  
  /**
   * Getting data variables.
   * @return list of data variables
   */
  public List<DataVariable> getDataVariables() {
    List<DataVariable> result = new ArrayList<>();
    dataVariablesColumn.findElements(By.cssSelector(".sa-report-grid-row-header")).stream()
        .forEach(webElement -> 
        result
          .add(DataVariable.getByFullName(getInnerText(webElement).split("span")[0].split(" Dataset")[0])));
    return result;
  }
  
  public List<String[]> getDataVariablesAndBadges(){
    List<String[]> result = new ArrayList<>();
    dataVariablesColumn.findElements(By.cssSelector(".sa-report-grid-row-header")).stream()
        .forEach(webElement -> {
          String dataName = StringEscapeUtils.unescapeHtml4(getInnerText(webElement).split("<span")[0].split(" Dataset")[0]);       
          String badge = StringEscapeUtils.unescapeHtml4(getInnerText(webElement.findElement(By.cssSelector(".sa-attribute-badge"))));
          String[] data = {dataName, badge};
          result.add(data);
        });
    return result;
  }

  public List<String[]> getDataVariablesAndBadgesWithoutEstimateAndProjData(){
    List<String[]> result = new ArrayList<>();
    dataVariablesColumn.findElements(By.cssSelector(".sa-report-grid-row-header")).stream()
            .forEach(webElement -> {
              String dataName = StringEscapeUtils.unescapeHtml4(getInnerText(webElement).split("<span")[0].split(" Dataset")[0]);
              // Remove "est" or "proj" from dataName using regex
              String cleanedText = dataName.replaceAll("\\b(est|proj)\\b", "").trim();
              String badge = StringEscapeUtils.unescapeHtml4(getInnerText(webElement.findElement(By.cssSelector(".sa-attribute-badge"))));
              String[] data = {cleanedText, badge};
              result.add(data);
            });
    return result;
  }
}
