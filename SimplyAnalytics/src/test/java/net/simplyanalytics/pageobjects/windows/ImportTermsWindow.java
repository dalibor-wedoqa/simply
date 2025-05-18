package net.simplyanalytics.pageobjects.windows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.header.importpackage.ImportSection;
import io.qameta.allure.Step;

public class ImportTermsWindow extends BasePage {
  
  @FindBy(css = ".sa-import-terms-window")
  private WebElement importTermsWindow;
  
  @FindBy(css = ".sa-import-terms-window-content")
  private WebElement importTermsWindowContent;
  
  @FindBy(css = ".sa-import-terms-window-agree-button")
  private WebElement agreeButton;
  
  @FindBy(xpath = ".//a[span[contains(normalize-space(.), 'Cancel')]]")
  private WebElement cancelButton;
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(importTermsWindow,
        "The welcome screen tutorial dialog should appear");
  }
  
  public ImportTermsWindow(WebDriver driver) {
    super(driver);
  }
  
  @Step("Click on the Cancel button")
  public void clickCancelButton() {
    logger.debug("Click on the Cancel button");
    waitForElementToAppear(cancelButton, "The Cancel button should appear");
    cancelButton.click();
    waitForElementToDisappear(importTermsWindow, "Import Terms Window should disappear");
  }
  
  @Step("Click on the I agree button")
  public ImportSection clickAgreeButton() {
    logger.debug("Click on the I agree button");
    waitForElementToAppear(agreeButton, "The I agree button should appear");
    agreeButton.click();
    waitForElementToDisappear(importTermsWindow, "Import Terms window is still visible");
    return new ImportSection(driver);
  }

  @Step("Click on the I agree button")
  public ImportSection clickAgreeButton2() {
    logger.debug("Click on the I agree button");
    waitForElementToAppear(agreeButton, "The I agree button should appear");
    WebElement agreeLink = driver.findElement(By.xpath("//span[contains(text(), 'I agree')]/.."));
    agreeLink.click();
    waitForElementToDisappear(importTermsWindow, "Import Terms window is still visible");
    return new ImportSection(driver);
  }
  
  public String getWindowTitle() {
    return importTermsWindowContent.findElement(By.cssSelector("h1")).getText().trim() + "\n"
          + importTermsWindowContent.findElement(By.cssSelector("h2")).getText().trim();
  }
  
  public String getIntroNote() {
    return importTermsWindow
              .findElement(By.cssSelector(".sa-import-terms-window-intro-note")).getText().trim();
  }
  
  public String getFirstParagraph() {
    return (new StringBuilder()).append(importTermsWindowContent.findElement(By.cssSelector("h3:nth-child(4)")).getText().trim() + "\n")
        .append(importTermsWindowContent.findElement(By.cssSelector("p:nth-child(5)")).getText().trim()
      ).toString();  
  }
  
  public String getSecondParagraph() {
    return (new StringBuilder())
        .append(importTermsWindowContent.findElement(By.cssSelector("h3:nth-child(6)")).getText().trim() + "\n")
        .append(importTermsWindowContent.findElement(By.cssSelector("p:nth-child(7)")).getText().trim() + "\n")
        .append(importTermsWindowContent.findElement(By.cssSelector("p:nth-child(8)")).getText().trim() + "\n")
        .append(importTermsWindowContent.findElement(By.cssSelector("p:nth-child(9)")).getText().trim()
        ).toString();  
  }
  
  public String getContactUsNote() {
    return importTermsWindow
        .findElement(By.cssSelector(".sa-import-terms-window-contact-us-note")).getText().trim();
  }
  
  public String getMailToLink() {
    return importTermsWindow.findElement(By.cssSelector(".sa-import-terms-window-contact-us-note a")).getAttribute("href").trim();
  }
  
}
