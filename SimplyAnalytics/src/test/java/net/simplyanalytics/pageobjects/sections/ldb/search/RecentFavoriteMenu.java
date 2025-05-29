package net.simplyanalytics.pageobjects.sections.ldb.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.simplyanalytics.enums.Ldb;
import net.simplyanalytics.enums.SearchSubMenuType;
import net.simplyanalytics.pageobjects.base.BasePage;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.interactions.Actions;


import io.qameta.allure.Step;

public class RecentFavoriteMenu extends BasePage {
  
  private static final By ROOT = By.xpath(
      "//div[contains(@class, 'sa-recent-list-default')][not(contains(@style, 'display: none;'))]");
  
  private WebElement root;
  
  @FindBy(xpath = "div/div[contains(@class, 'sa-recent-list-title')]")
  private WebElement title;
  
  @FindBy(xpath = "(//button[contains(@class, 'sa-close')])")
  private List <WebElement> close;

  @FindBy(css = ".sa-recent-list-item-name")
  private List<WebElement> items;
  
  protected SearchSubMenuType searchSubMenuType;
  
  /**
   * RecentFavoriteMenu constructor.
   * 
   * @param driver WebDriver
   */
  public RecentFavoriteMenu(WebDriver driver) {
    super(driver, ROOT, true);
    
    this.root = driver.findElement(ROOT);
  }
  
  @Override
  protected void isLoaded() {
    waitForElementToAppear(title, "The title didn't appear");
    waitForElementToAppear(close.get(close.size()-1), "The close didn't appear");
    searchSubMenuType = SearchSubMenuType.getSubMenuTypeByTitle(title.getText());
  }
  
  /**
   * Getting recent favorite menu item list.
   * @return recent favorite menu item list
   */
  public List<RecentFavoriteMenuItem> getMenuItems() {
    System.out.println("Method getMenuItems() called");

    System.out.println("Finding elements with XPath 'ul/li' under root element");
    List<WebElement> items = root.findElements(By.xpath("ul/li"));
    System.out.println("Number of elements found: " + items.size());

    System.out.println("Initializing list for RecentFavoriteMenuItem objects");
    List<RecentFavoriteMenuItem> recentFavoriteMenuItems = new ArrayList<RecentFavoriteMenuItem>();

    for (int i = 0; i < items.size(); i++) {
      WebElement item = items.get(i);
      System.out.println("Processing item " + i + ": " + item.toString());

      if (searchSubMenuType.getDataType().equals(Ldb.BUSINESS)) {
        System.out.println("Data type is BUSINESS, creating RecentFavoriteMenuItem");
        RecentFavoriteMenuItem menuItem = new RecentFavoriteMenuItem(driver, item);
        recentFavoriteMenuItems.add(menuItem);
        System.out.println("Added RecentFavoriteMenuItem to the list");
      } else {
        System.out.println("Data type is not BUSINESS, creating RecentFavoriteMenuItemWithSubMenu"+searchSubMenuType.getDataType() );
        RecentFavoriteMenuItemWithSubMenu menuItemWithSub = new RecentFavoriteMenuItemWithSubMenu(driver, item);
        System.out.println("The Burger Menu Button:");
        recentFavoriteMenuItems.add(menuItemWithSub);
        System.out.println("Added RecentFavoriteMenuItemWithSubMenu to the list");
      }
    }

    System.out.println("Returning list of menu items. Total items: " + recentFavoriteMenuItems.size());
    return recentFavoriteMenuItems;
  }


  public List<RecentFavoriteMenuItem> getMenuItemsFromRecent() {
    List<WebElement> items = driver.findElements(By.cssSelector("ul>li>a>span[class=\"sa-recent-list-item-name\"]"));
    List<RecentFavoriteMenuItem> recentFavoriteMenuItems = new ArrayList<RecentFavoriteMenuItem>();
    Actions actions = new Actions(driver);
    JavascriptExecutor js = (JavascriptExecutor) driver;

    for (WebElement item : items) {
      // Click on the element
      item.click();
      // Scroll down a little bit after clicking
      js.executeScript("window.scrollBy(0, 100);");

      if (searchSubMenuType.getDataType().equals(Ldb.BUSINESS)) {
        recentFavoriteMenuItems.add(new RecentFavoriteMenuItem(driver, item));
      } else {
        recentFavoriteMenuItems.add(new RecentFavoriteMenuItemWithSubMenu(driver, item));
      }
    }
    return recentFavoriteMenuItems;
  }

  /**
   * Geting nth menu item.
   * @param n n index
   * @return nth menu item
   */
  public RecentFavoriteMenuItem getNthMenuItem(int n) {
    WebElement item = root.findElement(By.xpath("ul/li[" + n + "]"));
    if (searchSubMenuType.getDataType().equals(Ldb.BUSINESS)) {
      return new RecentFavoriteMenuItem(driver, item);
    } else {
      return new RecentFavoriteMenuItemWithSubMenu(driver, item);
    }
  }
  
  /**
   * Geting menu item.
   * @param name menu item name
   * @return menu item
   */
  public RecentFavoriteMenuItem getMenuItem(String name) {
    WebElement item = root
        .findElement(By.xpath("ul/li[a/span[contains(.," + xpathSafe(name) + ")]]"));
    if (searchSubMenuType.getDataType().equals(Ldb.BUSINESS)) {
      return new RecentFavoriteMenuItem(driver, item);
    } else {
      return new RecentFavoriteMenuItemWithSubMenu(driver, item);
    }
  }
  
  public void waitForLocationToAppear(String name) {
    waitForElementToAppearWithCustomTime(root.findElement(By.xpath("ul/li[a/span[contains(.," + xpathSafe(name) + ")]]")), 
        "Element with name is not appeared", 6);
  }
  
  public boolean isItemPresent(String name) {
    return isChildPresent(root, By.xpath("ul/li[a/span[contains(.," + xpathSafe(name) + ")]]"));
  }
  
  /**
   * Getting selected recent favorite menu item.
   * @return selected recent favorite menu item
   */
  public RecentFavoriteMenuItem getSelected() {
    WebElement item = root
        .findElement(By.xpath("ul/li[contains(@class, 'sa-recent-list-item-selected')]"));
    if (searchSubMenuType.getDataType().equals(Ldb.BUSINESS)) {
      return new RecentFavoriteMenuItem(driver, item);
    } else {
      return new RecentFavoriteMenuItemWithSubMenu(driver, item);
    }
  }
  
  @Step("Click on the close button (x icon in the right upper corner)")
  public void clickCloseButton() {
    logger.debug("Click on the close button (x icon in the right upper corner)");
    close.get(close.size()-1).click();
  }
  
  public boolean isDisplayed() {
    return isPresent(root);
  }
  
}
