package net.simplyanalytics.pageobjects.sections.ldb.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;

public class DataResultItem extends BasePage {
  
  protected WebElement root;
  
  @FindBy(xpath = ".//span[@class='sa-data-search-results-attribute-name']")
  private WebElement title;
  
  @FindBy(xpath = ".//span[@class='sa-attribute-badge']")
  private WebElement badge;
  
  @FindBy(xpath = ".//button[@class='sa-data-search-results-attribute-button']")
  private WebElement moreOptions;
  
  public DataResultItem(WebDriver driver, WebElement root) {
    super(driver, root);
    this.root = root;
  }
  
  @Override
  protected void isLoaded() {
    waitForElementToAppear(title, "The title of the menu item didn't appear");
  }
  
  public RecentFavoriteSubMenu clickOnMoreOptions() {
	    logger.debug("Click on the More Option button of element with name " + getTitle());
	    moreOptions.click();
	    return new RecentFavoriteSubMenu(driver);
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
    return title.findElement(By.cssSelector(".sa-attribute-badge")).getText().trim();
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
}
