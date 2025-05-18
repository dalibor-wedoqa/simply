package net.simplyanalytics.pageobjects.pages.main.export;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.simplyanalytics.enums.RankingExportOptions;
import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class RankingChooseExportDropdown extends BasePage {
  
  protected static final By ROOT_LOCATOR = By
      .cssSelector(".x-panel:not([style*='visibility: hidden']) .x-panel-body.x-menu-body");
  
  private WebElement root;
  
  public RankingChooseExportDropdown(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
    waitForAllElementsToAppearByLocator(ROOT_LOCATOR, "Export dropdown root is not present");
  }
  
  @Step("Click on the export option")
  public ExportWindowRanking clickExport(RankingExportOptions option) {
    logger.info("Click on the export option: " + option.getName());
    root.findElement(By.xpath("//a[.//span[contains(normalize-space(.), '" + option.getName() + "')]]")).click();
    return new ExportWindowRanking(driver);
  }
  
  public boolean isPresent(RankingExportOptions option) {
    return !root.findElement(By.xpath("//a[.//span[contains(normalize-space(.), '" + option.getName() + "')]]/..")).getAttribute("style").contains("display: none;");
  }
  
}
