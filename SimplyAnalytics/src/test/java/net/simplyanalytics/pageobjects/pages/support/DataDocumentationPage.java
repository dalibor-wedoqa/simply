package net.simplyanalytics.pageobjects.pages.support;

import net.simplyanalytics.enums.DataDocumentationLinkEnum;
import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataDocumentationPage extends BasePage {
  
  @FindBy(css = ".sa-data-docs-window")
  private WebElement root;

  @FindBy(css = ".sa-data-docs-window-header")
  private WebElement supportDataDocumentationTitle;
  
  @Override
  public void isLoaded() {
    
    waitForElementToBeClickable(supportDataDocumentationTitle,
        "Data Documentation title didnt appear");
  }
  
  public DataDocumentationPage(WebDriver driver) {
    super(driver);
  }
  
  public String getDataDocumentationTitle() {
    logger.debug("Get title");
    return supportDataDocumentationTitle.getText();
  }
  
  public String clickOnTheLink(DataDocumentationLinkEnum document) {
    logger.debug("Click on the link: " + document.getName());
    WebElement linkElement = root.findElement(By.xpath(".//a[@href ='" + document.getRef() + "']"));
    //String address = linkElement.getAttribute("href").trim();
    linkElement.click();
    String address = document.getVerification_ref();
    return address;
  }
  
}
