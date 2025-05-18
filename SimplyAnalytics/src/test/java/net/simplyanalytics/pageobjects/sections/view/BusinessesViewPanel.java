package net.simplyanalytics.pageobjects.sections.view;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;

import org.openqa.selenium.WebDriver;

public class BusinessesViewPanel extends TableViewWithoutDataVariableColoumnPanel {
  
  public BusinessesViewPanel(WebDriver driver) {
    super(driver, ViewType.BUSINESSES);
  }
  
}
