package net.simplyanalytics.pageobjects.sections.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;
import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class RankingViewPanel extends TableViewWithoutDataVariableColoumnPanel {
  
  @FindBy(css = "div.sa-grid-header-normal")
  private WebElement dataVariablesRow;
  
  @FindBy(css = ".sa-project-view:not([class*= 'sa-project-view-hidden']) .sa-report-grid")
  private WebElement rankingContainerElement;
  
  public RankingViewPanel(WebDriver driver) {
    super(driver, ViewType.RANKING);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(rankingContainerElement, "Ranking Page is not loaded");
  }
  
  /**
   * Getting data variables.
   * @return data variables
   */
  public List<DataVariable> getDataVariables() {
    return getNormalHeaderValues().stream().map(text -> DataVariable.getByFullName(text)).collect(Collectors.toList());
  }
  
  public List<String[]> getDataVariablesWithBadges() {
    return getNormalHeaderValuesAndBadges();
  }
  public List<String[]> getDataVariablesWithBadgesWithoutEstimateAndProjData() {
    return getDataVariablesAndBadgesWithoutEstimateAndProjData();
  }
  public List<String[]> getColumnDataVariables() {
    return getNormalHeaderValuesArray();
  }

  public boolean isRowStrikeouted(String rowName) {
    WebElement row = getRowElement(rowName);
    return row.getAttribute("class").contains("sa-report-grid-strikeout");
  }
  
  /**
   * Is cell strikeout.
   * @param row row
   * @param header header
   * @return cell strikeout
   */
  public boolean isCellStrikeout(String row, String header) {
    int x = scrollColumnInView(header);
    int y = scrollToRow(row);
    
    WebElement cell = getAllCells().stream()
        .filter(webElement -> webElement.getLocation().equals(new Point(x, y))).findAny().get();
    return cell.getCssValue("text-decoration").startsWith("line-through solid");
  }
  
  @Step("Hover the badge of LDB item: \"{0}\"")
  public List<String> getDatasetNameandVendorAfterHovering(String dataVariable) {
    scrollColumnInView(dataVariable);
    List<String> tooltip = new ArrayList<String>();
    logger.debug("Hover badge of LDB item: " + dataVariable);
    WebElement element = rankingContainerElement.findElements(By.xpath(".//div[@class='sa-grid-header-normal']/div[contains(@class,'sa-grid-column')]")).stream()
        .filter(dataElement -> {
          if(dataElement.getAttribute("innerHTML").contains("sa-report-grid-column-text-first")) {
            return dataElement.findElement(By.cssSelector(".sa-report-grid-column-text-first")).getText().trim().contains(dataVariable);
          }
          else {
            return dataElement.getText().trim().contains(dataVariable);
          }
        }).collect(Collectors.toList()).get(0);
    
    Actions builder = new Actions(driver);
    if(!element.getAttribute("innerHTML").contains("sa-report-grid-column-text-last")) {
      builder.moveToElement(element.findElement(By.cssSelector(".sa-attribute-badge"))).perform();
    }
    else {
      builder.moveToElement(element.findElement(By.cssSelector(".sa-report-grid-column-text-last .sa-attribute-badge"))).perform();
    }
    sleep(200);
    String text = waitForElementToAppear(driver.findElement(By.cssSelector(".sa-tooltip-content")), "Tooltip is not visible").getText().trim();
    Pattern pattern = Pattern.compile("Dataset: (.+)");
    Matcher matcher = pattern.matcher(text);
    if(matcher.find()) {
      tooltip.add(matcher.group(1).trim());
    }
    pattern = Pattern.compile("Vendor: (.+)");
    matcher = pattern.matcher(text);
    if(matcher.find()) {
      tooltip.add(matcher.group(1).trim());
    }
    
    builder.moveByOffset(0, 400).build().perform();
    waitForElementToDisappear(By.cssSelector(".sa-tooltip-content"));
    
    return tooltip;
  }
  @Step("Hover the badge of LDB item: \"{0}\"")
  public List<String> getDatasetNameandVendorAfterHoveringFix(String dataVariable) {
    scrollColumnInView(dataVariable);
    List<String> tooltip = new ArrayList<>();
    logger.debug("Hover badge of LDB item: " + dataVariable);

    // Find the element that matches the dataVariable
    List<WebElement> elements = rankingContainerElement.findElements(By.xpath(".//div[@class='sa-grid-header-normal']/div[contains(@class,'sa-grid-column')]"))
            .stream()
            .filter(dataElement -> {
              boolean matches;
              if (dataElement.getAttribute("innerHTML").contains("sa-report-grid-column-text-first")) {
                matches = dataElement.findElement(By.cssSelector(".sa-report-grid-column-text-first")).getText().trim().contains(dataVariable);
              } else {
                matches = dataElement.getText().trim().contains(dataVariable);
              }
              logger.debug("Element HTML: " + dataElement.getAttribute("innerHTML") + " | Matches: " + matches);
              return matches;
            })
            .collect(Collectors.toList());

    // Ensure that we have at least one matching element
    if (elements.isEmpty()) {
      logger.error("No element found containing the data variable: " + dataVariable);
      return tooltip;  // Return an empty list to handle the case gracefully
    }

    WebElement element = elements.get(0);

    Actions builder = new Actions(driver);
    if (!element.getAttribute("innerHTML").contains("sa-report-grid-column-text-last")) {
      builder.moveToElement(element.findElement(By.cssSelector(".sa-attribute-badge"))).perform();
    } else {
      builder.moveToElement(element.findElement(By.cssSelector(".sa-report-grid-column-text-last .sa-attribute-badge"))).perform();
    }
    sleep(200);

    // Extract tooltip text
    String text = waitForElementToAppear(driver.findElement(By.cssSelector(".sa-tooltip-content")), "Tooltip is not visible").getText().trim();
    Pattern datasetPattern = Pattern.compile("Dataset: (.+)");
    Matcher matcher = datasetPattern.matcher(text);
    if (matcher.find()) {
      tooltip.add(matcher.group(1).trim());
    }
    Pattern vendorPattern = Pattern.compile("Vendor: (.+)");
    matcher = vendorPattern.matcher(text);
    if (matcher.find()) {
      tooltip.add(matcher.group(1).trim());
    }

    builder.moveByOffset(0, 400).build().perform();
    waitForElementToDisappear(By.cssSelector(".sa-tooltip-content"));

    return tooltip;
  }

}
