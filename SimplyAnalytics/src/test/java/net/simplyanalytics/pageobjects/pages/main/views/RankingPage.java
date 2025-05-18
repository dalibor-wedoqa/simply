package net.simplyanalytics.pageobjects.pages.main.views;

import net.simplyanalytics.pageobjects.sections.toolbar.ranking.RankingToolbar;
import net.simplyanalytics.pageobjects.sections.view.RankingViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.WebDriver;

public class RankingPage extends BaseViewPage {

  private final RankingToolbar rankingToolbar;
  private final RankingViewPanel rankingViewPanel;

  /**
   * RankingPage constructor.
   * 
   * @param driver WebDriver
   */
  public RankingPage(WebDriver driver) {
    super(driver);
    rankingToolbar 
      = new RankingToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    rankingViewPanel = new RankingViewPanel(driver);
  }

  @Override
  public RankingToolbar getToolbar() {
    return rankingToolbar;
  }

  @Override
  public RankingViewPanel getActiveView() {
    return rankingViewPanel;
  }

}
