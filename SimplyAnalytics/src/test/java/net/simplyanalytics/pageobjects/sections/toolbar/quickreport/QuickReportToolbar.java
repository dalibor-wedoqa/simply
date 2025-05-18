package net.simplyanalytics.pageobjects.sections.toolbar.quickreport;

import net.simplyanalytics.enums.ReportContent;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.quickexport.QuickExportWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class QuickReportToolbar extends BaseViewToolbar {

  @FindBy(css = ".sa-button-underline")
  protected WebElement reportContent;

  public QuickReportToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.QUICK_REPORT, nextViewType);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(reportContent, "The report content chooser is missing");
  }

  public ReportContent getActiveReportContent() {
    return ReportContent.getByName(reportContent.getText());
  }

  /**
   * Open report content list menu.
   * @return ReportContentComboElements
   */
  @Step("Open report content list menu")
  public ReportContentComboElements openReportContentListMenu() {
    logger.debug("Open report content list menu");
    reportContent.click();
    return new ReportContentComboElements(driver);
  }

  public QuickViewActionMenu clickViewActions() {
    return (QuickViewActionMenu) super.clickViewActions();
  }

  @Override
  public QuickExportWindow clickExport() {
    return (QuickExportWindow) clickExportButton(ViewType.QUICK_REPORT);
  }
}
