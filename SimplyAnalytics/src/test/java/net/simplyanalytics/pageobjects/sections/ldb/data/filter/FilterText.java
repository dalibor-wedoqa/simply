package net.simplyanalytics.pageobjects.sections.ldb.data.filter;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class FilterText extends BasePage {

  private String content = " ";

  public FilterText(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void isLoaded() {
  }

  @Step("Filter the Text")
  public List<String> filterTextClass(List<WebElement> allFilterElements) {
    List<String> filteredElements = new ArrayList<String>();
    for (int i = 0; i < allFilterElements.size(); i++) {
      try {
        if (allFilterElements.get(i).getAttribute("data-type").equals("text")
            || allFilterElements.get(i).getAttribute("data-type").equals("filterText")) {
          content = allFilterElements.get(i).getText().split(" = ")[0];
          filteredElements.add(content);
        }
      } catch (StaleElementReferenceException x) {
        if (allFilterElements.get(i).getAttribute("data-type").equals("text")
            || allFilterElements.get(i).getAttribute("data-type").equals("filterText")) {
          content = allFilterElements.get(i).getText().split(" = ")[0];
          filteredElements.add(content);
        }
      }
    }
    return filteredElements;
  }

}
