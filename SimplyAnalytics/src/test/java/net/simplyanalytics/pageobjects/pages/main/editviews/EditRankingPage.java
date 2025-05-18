package net.simplyanalytics.pageobjects.pages.main.editviews;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.EditRankingPanel;

import org.openqa.selenium.WebDriver;

public class EditRankingPage extends BaseEditViewPage {
  
  private final EditRankingPanel editRankingPanel;
  
  public EditRankingPage(WebDriver driver) {
    super(driver, ViewType.RANKING);
    editRankingPanel = new EditRankingPanel(driver);
  }
  
  @Override
  public EditRankingPanel getActiveView() {
    return editRankingPanel;
  }
}
