package net.simplyanalytics.pageobjects.sections.view.editview.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.qameta.allure.Step;

public class RadioButtonContainerPanel extends BaseContainerPanel {

  public RadioButtonContainerPanel(WebDriver driver, By root, String panelName) {
    super(driver, root);
  }

  @Override
  public void isLoaded() {

  }

  /**
   * Getting selected element.
   * 
   * @return Selected element name
   */
  public String getSelectedElement() {
    WebElement selectedElement = waitForElementToAppear(root.findElement(By.cssSelector(".sa-radio-checked .sa-radio-label-text")),
    "The selected element has not appeared");
    Pattern pattern = Pattern.compile("<span>(.+)<span>");
    Matcher matcher = pattern.matcher(selectedElement.getAttribute("outerHTML"));
    if(matcher.find()) {
      return StringEscapeUtils.unescapeHtml4(matcher.group(1).trim());
    }
    else {
      logger.info(selectedElement.getText().trim());
      return selectedElement.getText().trim();
    }
  }
  
  public List<String[]> getAllElementsAndBadges() {
    List<WebElement> elements = root.findElements(By.cssSelector(".sa-radio-label-text"));
    return elements.stream()
        .map(element -> {
            Pattern pattern = Pattern.compile("<span>(.+)<span>");
            Matcher matcher = pattern.matcher(element.getAttribute("outerHTML"));
            String badge = element.findElement(By.cssSelector(".sa-attribute-badge")).getText().trim();
            if(matcher.find()) {
              String[] dataArray = {StringEscapeUtils.unescapeHtml4(matcher.group(1).replace("  ", " ").trim()), badge};
              return dataArray;
            }
            else {
              logger.info(element.getText().trim());
              String[] dataArray = {element.getText().trim(), badge};
              return dataArray;
            }
        }).collect(Collectors.toList());
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
    WebElement dataElement = root.findElements(By.cssSelector(".sa-radio-label-text")).stream()
    .filter(element -> element.getText().trim().contains(itemName))
    .collect(Collectors.toList()).get(0);
    Actions builder = new Actions(driver);
    builder.moveToElement(dataElement.findElement(By.cssSelector(".sa-attribute-badge "))).perform();
    sleep(200);
    String text = driver.findElement(By.cssSelector(".sa-tooltip-content")).getText().trim();
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
    sleep(200);
    return tooltip;
  }
}
