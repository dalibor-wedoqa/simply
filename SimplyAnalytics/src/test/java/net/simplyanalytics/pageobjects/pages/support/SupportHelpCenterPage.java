package net.simplyanalytics.pageobjects.pages.support;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SupportHelpCenterPage extends BasePage {
  
  @FindBy(xpath = ".//div[@class='wf-td hgroup']/h1/span")
  private WebElement supportHelpCenterTitle;

  @FindBy(css = "input[placeholder='What can we help you with?']")
  private WebElement searchField;
  
  @Override
  public void isLoaded() {
    waitForElementToBeClickable(supportHelpCenterTitle, "Help Support Center title didnt appear");
  }
  
  public SupportHelpCenterPage(WebDriver driver) {
    super(driver);
  }
  
  public String getSupportHelpCenterTitle() {
    logger.debug("Support Help Center loaded.");
    waitForElementToAppearWithCustomTime(searchField, "Search field did not appear after 10 seconds of wait.", 10);
    logger.debug("Get title");
    waitForElementToAppear(supportHelpCenterTitle, " The Help Center Title didn't appear");
    return supportHelpCenterTitle.getText();
  }
}
