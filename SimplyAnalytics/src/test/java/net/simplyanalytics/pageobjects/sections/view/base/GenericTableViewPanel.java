package net.simplyanalytics.pageobjects.sections.view.base;

import net.simplyanalytics.enums.ViewType;

import org.openqa.selenium.WebDriver;

public class GenericTableViewPanel extends ViewPanel {

  protected ViewType viewType;
  
  public GenericTableViewPanel(WebDriver driver, ViewType viewType) {
    super(driver);
    this.viewType = viewType;
  }
  
}
