package net.simplyanalytics.pageobjects.pages.main.views;

import net.simplyanalytics.pageobjects.sections.toolbar.ringstudy.RingStudyToolbar;
import net.simplyanalytics.pageobjects.sections.view.RingStudyViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.WebDriver;

public class RingStudyPage extends BaseViewPage {
  
  private final RingStudyToolbar ringStudyToolbar;
  private final RingStudyViewPanel ringStudyViewPanel;
  
  /**
   * RingStudyPage constructor.
   * @param driver WebDriver
   */
  public RingStudyPage(WebDriver driver) {
    super(driver);
    ringStudyToolbar 
      = new RingStudyToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    ringStudyViewPanel = new RingStudyViewPanel(driver);
  }
  
  @Override
  public RingStudyToolbar getToolbar() {
    return ringStudyToolbar;
  }
  
  @Override
  public RingStudyViewPanel getActiveView() {
    return ringStudyViewPanel;
  }
}
