package net.simplyanalytics.pageobjects.sections.ldb.data.filter;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class FilterData extends BasePage {

  private String type = " ";
  private String content = " ";

  public FilterData(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void isLoaded() {
  }

  /**
   * .
   * @param allFilterElements allFilterElements
   * @param choose            1-Get the type and the content of the data as a
   *                          String list 2-Get the content of the data as a Sting
   *                          list
   * @return
   */
  @Step("Filter the Data")
  public List<String> filterDataClass(List<WebElement> allFilterElements, int choose) {
    List<String> filteredElements = new ArrayList<String>();
    for (int i = 0; i < allFilterElements.size(); i++) {
      try {
        if (allFilterElements.get(i).getAttribute("data-type").isEmpty()) {
          if (allFilterElements.get(i).getText().contains(" = ")) {
            type = allFilterElements.get(i).getText().split(" = ")[0];
            content = allFilterElements.get(i).getText().split(" = ")[1];
            if (choose == 1) {
              filteredElements.add(type + " = " + content);
            } else if (choose == 2) {
              filteredElements.add(content);
            } else {
              logger.error("Invalid \"choose\" parameter is entered! 1 and 2 is enabled!");
              break;
            }
          } else {
            content = allFilterElements.get(i).getText().split(" = ")[0];
            filteredElements.add(content);
          }
        }
      } catch (StaleElementReferenceException x) {
        if (allFilterElements.get(i).getAttribute("data-type").isEmpty()) {
          if (allFilterElements.get(i).getText().contains(" = ")) {
            type = allFilterElements.get(i).getText().split(" = ")[0];
            content = allFilterElements.get(i).getText().split(" = ")[1];
            if (choose == 1) {
              filteredElements.add(type + " = " + content);
            } else if (choose == 2) {
              filteredElements.add(content);
            } else {
              logger.error("Invalid \"choose\" parameter is entered! 1 and 2 is enabled!");
              break;
            }
          } else {
            content = allFilterElements.get(i).getText().split(" = ")[0];
            filteredElements.add(content);
          }
        }
      }
    }
    return filteredElements;
  }

}