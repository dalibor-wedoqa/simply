package net.simplyanalytics.pageobjects.pages.main.views;

import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;

import org.openqa.selenium.WebDriver;

public abstract class BaseViewPage extends MainPage {

  public BaseViewPage(WebDriver driver) {
    super(driver);
  }

  public abstract BaseViewToolbar getToolbar();

}
