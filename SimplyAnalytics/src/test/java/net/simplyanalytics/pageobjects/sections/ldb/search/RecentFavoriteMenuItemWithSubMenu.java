package net.simplyanalytics.pageobjects.sections.ldb.search;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RecentFavoriteMenuItemWithSubMenu extends RecentFavoriteMenuItem {
  
  @FindBy(xpath = ".//a[contains(@class,'sa-recent-list-item-button')]")
  private WebElement moreOptionsLDB;
  
  public RecentFavoriteMenuItemWithSubMenu(WebDriver driver, WebElement root) {
    super(driver, root);
  }
  
  @Override
  protected void isLoaded() {
    super.isLoaded();
    
    waitForElementToAppear(moreOptionsLDB, "The more options didn't appear");
  }
  
  /**
   * Click on more options.
   * 
   * @return RecentFavoriteSubMenu
   */
  public RecentFavoriteSubMenu clickOnMoreOptions() {
    logger.debug("Click on the More Option button of element with name " + getTitle());
    moreOptionsLDB.click();
    return new RecentFavoriteSubMenu(driver);
  }
}
