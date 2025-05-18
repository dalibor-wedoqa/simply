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
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class CheckboxContainerPanel extends BaseContainerPanel {
  
  @FindBy(xpath = ".//div[normalize-space(.)='Clear']")
  private WebElement clearButton;
  
  @FindBy(xpath = ".//div[normalize-space(.)='Select all']")
  private WebElement selectAllButton;
  
  private String panelName;
  
  public CheckboxContainerPanel(WebDriver driver, By root, String panelName) {
    super(driver, root);
    this.panelName = panelName;
  }
  
  @Override
  public void isLoaded() {
    //waitForElementToAppear(selectAllButton, "Select all button should appear");
    //waitForElementToAppear(clearButton, "Clear button should appear");
  }
  
  /**
   * Getting selected elements.
   * @return list of selected elements
   */
  public List<String> getSelectedElements() {
    List<String> result = new ArrayList<>();
    root.findElements(By.cssSelector(".sa-checkbox-checked .sa-check-field-label-text")).stream()
        .forEach(element -> {
          Pattern pattern = Pattern.compile("<span>(.+)<span>");
          Matcher matcher = pattern.matcher(element.getAttribute("outerHTML"));
          if(matcher.find()) {
            result.add(StringEscapeUtils.unescapeHtml4(matcher.group(1).trim()));
          }
          else {
          result.add(element.getText());
          }
        });
    return result;
  }
  
  public List<String[]> getAllElementsAndBadges() {
		List<WebElement> elements = root.findElements(By.cssSelector(".sa-radio-label-text"));
		return elements.stream().map(element -> {
			Pattern pattern = Pattern.compile("<span>(.+)<span>");
			Matcher matcher = pattern.matcher(element.getAttribute("outerHTML"));
			String badge = element.findElement(By.cssSelector(".sa-attribute-badge")).getText().trim();
			if (matcher.find()) {
				String[] dataArray = { StringEscapeUtils.unescapeHtml4(matcher.group(1).replace("  ", " ").trim()),
						badge };
				return dataArray;
			} else {
				logger.info(element.getText().trim());
				String[] dataArray = { element.getText().trim(), badge };
				return dataArray;
			}
		}).collect(Collectors.toList());
	}
  
  public List<String[]> getSelectedElementsWithBadges() {
    List<String[]> result = new ArrayList<>();
    root.findElements(By.cssSelector(".sa-checkbox-checked .sa-check-field-label-text")).stream()
        .forEach(element -> {
          Pattern pattern = Pattern.compile("<span>(.+)<span>");
          Matcher matcher = pattern.matcher(element.getAttribute("outerHTML"));
          String badge = element.findElement(By.cssSelector(".sa-attribute-badge ")).getText().trim();
          if(matcher.find()) {
            String[] dataArray = {StringEscapeUtils.unescapeHtml4(matcher.group(1).replace("  ", " ").trim()), badge};
            result.add(dataArray);
          }
          else {
            String[] dataArray = {element.getText(), badge};
          result.add(dataArray);
          }
        });
    return result;
  }

    public List<String[]> getSelectedElementsWithBadgesNewCssLocator() {
        List<String[]> result = new ArrayList<>();
        root.findElements(By.cssSelector("label>span>span")).stream()
                .forEach(element -> {
                    Pattern pattern = Pattern.compile("<span>(.+)<span>");
                    Matcher matcher = pattern.matcher(element.getAttribute("outerHTML"));
                    String badge = element.findElement(By.cssSelector(".sa-attribute-badge ")).getText().trim();
                    if(matcher.find()) {
                        String[] dataArray = {StringEscapeUtils.unescapeHtml4(matcher.group(1).replace("  ", " ").trim()), badge};
                        result.add(dataArray);
                    }
                    else {
                        String[] dataArray = {element.getText(), badge};
                        result.add(dataArray);
                    }
                });
        return result;
    }
  
  /**
   * Click on select all.
   */
  public void clickSelectAll() {
    //allureStep("Click on the Select All button on panel " + panelName);
    logger.debug("Click on the Select All button on panel " + panelName);
    selectAllButton.click();
  }
  
  /**
   * Click on clear.
   */
  public void clickClear() {
    //allureStep("Click on the Clear button on panel " + panelName);
    logger.debug("Click on the Clear button on panel " + panelName);
    //waitForElementToAppear(clearButton, "The Clear button didn't appear");
    clearButton.click();
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
    WebElement dataElement = root
    .findElements(By.cssSelector(".sa-checkbox-checked .sa-check-field-label-text")).stream()
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
    
    return tooltip;
  }
}
