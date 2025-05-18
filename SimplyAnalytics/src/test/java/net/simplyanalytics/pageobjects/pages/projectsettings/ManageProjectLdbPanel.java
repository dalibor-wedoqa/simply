package net.simplyanalytics.pageobjects.pages.projectsettings;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ManageProjectLdbPanel extends BasePage {

  private static final By ROOT = By.cssSelector(".sa-remove-building-blocks-list:not([style='display: none;'])");

  private final WebElement rootElement;

  @FindBy(css = ".sa-remove-building-blocks-item-name")
  private List<WebElement> items;
  
  @FindBy(css = ".sa-remove-building-blocks-list-empty")
  private WebElement emptyListMessage;

  public ManageProjectLdbPanel(WebDriver driver) {
    super(driver, ROOT);
    rootElement = driver.findElement(ROOT);
  }

  @Override
  public void isLoaded() {

  }

  /**
   * Getting item names.
   * 
   * @return list of item names
   */
  public List<String> getItemsName() {
    List<String> result = new ArrayList<>();
    waitForAllElementsToAppear(items, "Data Panel is not loaded").stream()
        .forEach(webElement -> {
          Pattern pattern = Pattern.compile("<div class=\"sa-remove-building-blocks-item-name\">(.+)<span>");
          Matcher matcher = pattern.matcher(webElement.getAttribute("outerHTML"));
          if(matcher.find()) {
            result.add(matcher.group(1).trim());
          }
          else {
            //Recent location
          result.add(webElement.getText().trim());
          }
        }
        );
    return result;
  }
  
  /**
   * Getting item names.
   * 
   * @return list of item names
   */
  public List<String[]> getItemNamesAndBadges() {
    List<String[]> dataList = new ArrayList<String[]>();
    waitForAllElementsToAppear(items, "Data Panel is not loaded").stream()
        .forEach(webElement -> {
          Actions actions = new Actions(driver);
          actions.moveToElement(webElement);
          actions.perform();
          Pattern pattern = Pattern.compile("<div class=\"sa-remove-building-blocks-item-name\">(.+)<span>");
          Matcher matcher = pattern.matcher(webElement.getAttribute("outerHTML"));
          List<String> badge = webElement.findElements(By.cssSelector(".sa-attribute-badge")).stream()
              .map(element -> element.getText().trim())
              .collect(Collectors.toList());
          String[] badgeArray = new String[badge.size()];
          badgeArray = badge.toArray(badgeArray);
          if(matcher.find()) {
            String[] dataArray = {StringEscapeUtils.unescapeHtml4(matcher.group(1).trim())};
            dataArray = ArrayUtils.addAll(dataArray, badgeArray);
            dataList.add(dataArray);
          }
          else {
            //Recent location
            String[] dataArray = {webElement.getText().trim()};
            dataArray = ArrayUtils.addAll(dataArray, badgeArray);
            dataList.add(dataArray);
          }
        }
        );
    return dataList;
  }

  /**
   * Click on the delete X button of LDB item.
   * 
   * @param itemName item name
   */
  @Step("Click on the delete X button of LDB item: \"{0}\"")
  public void clickX(String itemName) {
    logger.debug("Click on the delete X button of LDB item: " + itemName);
    WebElement xButton = waitForElementToAppear(
        rootElement
            .findElement(By.xpath(".//div[@class='sa-remove-building-blocks-item-name' and contains(text(),"
                + xpathSafe(itemName) + ")]/parent::div/a")),
        "the delete X button of LDB item has not appeared");
    xButton.click();
    waitForElementToDisappear(xButton);
  }
  
  public String getEmptyListMessage() {
    return waitForElementToAppear(emptyListMessage, "Message did not appear").getText().trim();
  }
  
  /**
   * Hover a badge of LDB item.
   * 
   * @param itemName item name
   */
  @Step("Hover the badge of LDB item: \"{0}\"")
  public List<String> getDatasetNameandVendorAfterHovering(String itemName) {
    List<String> tooltip = new ArrayList<String>();
    logger.debug("Hover badge of LDB item: " + itemName);
    WebElement element = rootElement
    .findElement(By.xpath(".//div[@class='sa-remove-building-blocks-item-name' and contains(text(),"
        + xpathSafe(itemName) + ")]/span/span"));
    Actions builder = new Actions(driver);
    builder.moveToElement(element).perform();
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
    
    return tooltip;
  }
  
}
