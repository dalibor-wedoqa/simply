package net.simplyanalytics.pageobjects.sections.ldb.search;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class RecentFavoriteMenuItem extends BasePage {
  
  protected WebElement root;
  
  @FindBy(xpath = ".//span[contains(@class,'sa-recent-list-item-name')]")
  private WebElement title;
  
  @FindBy(xpath = ".//span[contains(@class,'name')]//span[contains(@class, 'sa-attribute-badge')]")
  private WebElement badge;
  
  protected RecentFavoriteMenuItem(WebDriver driver, WebElement root) {
    super(driver, root);
    this.root = root;
  }
  
  @Override
  protected void isLoaded() {
    waitForElementToAppear(title, "The title of the menu item didn't appear");
  }
  
  public String getTitle() {
    //Recent Data
    Pattern pattern = Pattern.compile("<span class=\"sa-recent-list-item-name\">(.+)<span>");
    Matcher matcher = pattern.matcher(title.getAttribute("outerHTML"));
    if(matcher.find()) {
      return StringEscapeUtils.unescapeHtml4(matcher.group(1).replace("  ", " ").trim());
    }
    else {
      //Recent location
      return title.getText().trim();
    }
  }
  
  public String getBadge() {
    return badge.getText().trim();
  }
  
  public void clickToSelect() {
    allureStep("Click on the menuitem with name \"" + getTitle() + "\" to select it");
    root.click();
  }
  
  public boolean isSelected() {
    return root.getAttribute("class").contains("sa-recent-list-item-selected");
  }
  
  public void waitForNameToChange(String name) {
    int i = 10;
    while(i > 0) {
      if(title.getText().contains(name)) {
        break;
      }
      else {
        sleep(500);
        i--;
      }
    }
    if (i == 0)
      throw new AssertionError("Element with name: '" + name + "' is not present");
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
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", badge);
    Actions builder = new Actions(driver);
    builder.moveToElement(badge).perform();
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
    builder.moveByOffset(400, 0).build().perform();
    waitForElementToDisappear(By.cssSelector(".sa-tooltip-content"));

    return tooltip;
  }
}
