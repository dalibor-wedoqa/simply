package net.simplyanalytics.pageobjects.pages.support;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReplayTutorialPage extends BasePage {

  @FindBy(xpath = "//div[@class='sa-tutorial-slide sa-tutorial-slide-2 sa-tutorial-slide-current "
      + "sa-tutorial-slide-active']/h3")
  private WebElement supportReplayTutorialTitle;

  @Override
  public void isLoaded() {
    waitForElementToAppearWithCustomTime(supportReplayTutorialTitle,
        "Replay Tutorial title didnt appear", 10);
  }

  public ReplayTutorialPage(WebDriver driver) {
    super(driver);
  }

  public String getReplayTutorialTitle() {
    logger.debug("Get title");
    return supportReplayTutorialTitle.getAttribute("innerText");
  }

}
