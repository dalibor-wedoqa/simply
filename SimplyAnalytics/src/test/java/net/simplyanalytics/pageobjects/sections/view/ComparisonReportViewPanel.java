package net.simplyanalytics.pageobjects.sections.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithLockedColoumnPanel;
import io.qameta.allure.Step;

import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ComparisonReportViewPanel extends TableViewWithLockedColoumnPanel {

  @FindBy(css = "div.sa-report-grid")
  protected WebElement comparisonReportPageElement;

  @FindBy(css = ".sa-project-view:not([class*=\"sa-project-view-hidden\"]) div.sa-report-grid table[id*='lockedTable'].sa-grid-table")
  private WebElement dataVariablesColumn;

  @FindBy(css = ".sa-grid-header-normal")
  private WebElement locationHeader;

  public ComparisonReportViewPanel(WebDriver driver) {
    super(driver, ViewType.COMPARISON_REPORT);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(comparisonReportPageElement, "Comparison report page is not loaded");

    waitForElementToAppear(tableRoot, "Table is not loaded");
  }

  /**
   * Getting data variables.
   * Does not work with transposed report
   * @return list of data variables
   */
  public List<DataVariable> getDataVariables() {
    List<DataVariable> result = new ArrayList<>();
    dataVariablesColumn.findElements(By.cssSelector("td")).stream()
        .forEach(webElement -> 
        result.add(DataVariable.getByFullName(getInnerText(webElement).split("span")[0].split(" Dataset")[0])));
    return result;
  }
  
  /**
   * Getting data variables.
   * Does not work with transposed report
   * @return list of data variables
   */
  public List<String[]> getDataVariablesAndBadges() {
    List<String[]> result = new ArrayList<>();
    dataVariablesColumn.findElements(By.cssSelector("td")).stream()
        .forEach(webElement -> {
          String dataName = webElement.getText().substring(0, webElement.getText().trim().lastIndexOf(" "));
          String badge = webElement.getText().substring(webElement.getText().trim().lastIndexOf(" ") + 1);
          String[] data = {StringEscapeUtils.unescapeHtml4(dataName), badge};
          result.add(data);
        });
    return result;
  }

  //Extract Data Variables and Badges without fix from the original code
  public List<String[]> getDataVariablesAndBadgesWithoutEstimateAndProjData() {
    List<String[]> result = new ArrayList<>();
    dataVariablesColumn.findElements(By.cssSelector("td")).stream()
            .forEach(webElement -> {
              // Extract the full text from the web element
              String fullText = webElement.getText().trim();
              System.out.println("Full text: " + fullText); // Debug logging

              // Extract dataName and badge from the fullText
              int lastSpaceIndex = fullText.lastIndexOf(" ");
              if (lastSpaceIndex == -1) {
                // Handle case where there's no space (unexpected format)
                System.err.println("Unexpected format, no space found in: " + fullText);
                return;
              }
              String dataName = fullText.substring(0, lastSpaceIndex).trim();
              String badge = fullText.substring(lastSpaceIndex + 1).trim();

              // Debug logging
              System.out.println("Before cleaning - Data name: " + dataName + ", Badge: " + badge);

              // Remove "est" or "proj" from dataName using regex
              String cleanedDataName = dataName.replaceAll("\\b(est|proj)\\b", "").trim();

              // Debug logging
              System.out.println("After cleaning - Data name: " + cleanedDataName);

              // Unescape HTML characters and add to result
              String[] data = {StringEscapeUtils.unescapeHtml4(cleanedDataName), badge};
              result.add(data);
            });
    return result;
  }


  //Extract Data Variables and Badges without sole code NB
  public List<String[]> getDataVariablesAndBadgesWithoutEstimateAndProjDataOld() {
    List<String[]> result = new ArrayList<>();
    dataVariablesColumn.findElements(By.cssSelector("td")).forEach(webElement -> {
      // Extract the text from the td element
      String text = webElement.getText().trim();

      // Extract the badge text from the span element
      WebElement badgeElement = webElement.findElement(By.cssSelector("span.sa-attribute-badge"));
      String badge = badgeElement.getText().trim();

      // Clean the text to remove "est" or "proj"
      String cleanedText = text.replaceAll("\\b(est|proj)\\b", "").trim();

      // Extract dataName and badge from cleanedText
      int lastSpaceIndex = cleanedText.lastIndexOf(" ");
      if (lastSpaceIndex != -1) {
        String dataName = cleanedText.substring(0, lastSpaceIndex).trim();
        String[] data = {StringEscapeUtils.unescapeHtml4(dataName), badge};
        result.add(data);
      }
    });
    return result;
  }
  public List<String[]> getDataVariablesAndBadgesWithoutEstimateAndProjDataOldButNew() {
    List<String[]> result = new ArrayList<>();
    dataVariablesColumn.findElements(By.cssSelector("td")).forEach(webElement -> {
      // Extract the text from the td element
      String text = webElement.getText().trim();

      // Clean the text to remove "est" or "proj"
      String cleanedText = text.replaceAll("\\b(est|proj)\\b", "").trim();

      // Extract the badge text from the span element
      WebElement badgeElement = webElement.findElement(By.cssSelector("span.sa-attribute-badge"));
      String badge = badgeElement.getText().trim();

      // Extract dataName from cleanedText
      int lastSpaceIndex = cleanedText.lastIndexOf(" ");
      if (lastSpaceIndex != -1) {
        String dataName = cleanedText.substring(0, lastSpaceIndex).trim();
        String[] data = {StringEscapeUtils.unescapeHtml4(dataName), badge};
        result.add(data);
      }
    });
    return result;
  }



  @Step("Hover the badge of LDB item: \"{0}\"")
  public List<String> getDatasetNameandVendorAfterHovering(String dataVariable) {
    List<String> tooltip = new ArrayList<String>();
    logger.debug("Hover badge of LDB item: " + dataVariable);
    WebElement element = dataVariablesColumn
        .findElement(By.xpath(".//td[contains(normalize-space(.),'" + dataVariable + "')]/span/span"));
    Actions builder = new Actions(driver);
    builder.moveToElement(element).perform();
    sleep(500);
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
    
    return tooltip;
  }

  // Modify the method to handle visibility and wait for tooltip
  @Step("Hover the badge of LDB item: \"{0}\"")
  public List<String> getDatasetNameandVendorAfterHoveringForVBTWHV(String dataVariable) {
    List<String> tooltip = new ArrayList<String>();
    logger.debug("Hover badge of LDB item: " + dataVariable);
    // Locate the element
    WebElement element = dataVariablesColumn.findElement(By.xpath("//div[contains(@class, 'sa-checkbox') and .//span[contains(@class, 'sa-check-field-label-text') and contains(normalize-space(.), '" + dataVariable + "')]]"));

    // Ensure the element is visible
    if (element.isDisplayed()) {
      // Create Actions object
      Actions builder = new Actions(driver);
      // Perform hover action
      builder.moveToElement(element).perform();

      // Wait for tooltip to be visible
      WebDriverWait wait = new WebDriverWait(driver, 10);
      WebElement tooltipElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sa-tooltip-content")));

      // Extract dataset name and vendor information from tooltip
      String text = tooltipElement.getText().trim();
      Pattern pattern = Pattern.compile("Dataset: (.+)");
      Matcher matcher = pattern.matcher(text);
      if (matcher.find()) {
        tooltip.add(matcher.group(1).trim());
      }
      pattern = Pattern.compile("Vendor: (.+)");
      matcher = pattern.matcher(text);
      if (matcher.find()) {
        tooltip.add(matcher.group(1).trim());
      }
    } else {
      logger.error("Element is not visible.");
    }

    return tooltip;
  }

  /**
   * Getting locations.
   * Does not work with transposed report
   * @return list of locations
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

  public boolean isRowStrikeouted(String rowName) {
    WebElement row = getRowElement(rowName);
    return row.getAttribute("class").contains("sa-report-grid-strikeout");
  }

  /**
   * Is cell strikeout?
   * @param row row
   * @param header header
   * @return true if cell is strikeout, false otherwise
   */
  public boolean isCellStrikeout(String row, String header) {
    int x = scrollColumnInView(header);
    int y = scrollToRow(row);

    WebElement cell = getAllCells().stream()
        .filter(webElement -> webElement.getLocation().equals(new Point(x, y))).findAny().get();
    return cell.getCssValue("text-decoration").startsWith("line-through solid");
  }
}
