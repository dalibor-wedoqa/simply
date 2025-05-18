package net.simplyanalytics.tests.manageproject;

import java.util.ArrayList;
import java.util.List;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.parallel.ConcurrentParameterizedTestRunner;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ManageViewsPanel;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.view.manageproject.ViewDropdown;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

@RunWith(ConcurrentParameterizedTestRunner.class)
public class RenameViewTests extends TestBase {
  
  private ProjectSettingsPage projectSettingsPage;
  private ViewType view;
  
  /**
   * .
   * @return
   */
  @Parameters(name = "{index}: view {0}")
  public static List<ViewType[]> data() {
    List<ViewType[]> list = new ArrayList<>();
    for (ViewType viewType : ViewType.values()) {
      list.add(new ViewType[] { viewType });
    }
    return list;
  }
  
  public RenameViewTests(ViewType view) {
    this.view = view;
  }
  
  @Before
  public void createProjectWithOneOfEachView() {
    driver.manage().window().maximize();
    NewViewPage newViewPage = createOneViewFromEachTypeFix();
    projectSettingsPage = newViewPage.getHeaderSection().clickProjectSettings();
  }

  @Test
  @DisplayName("Verify that renaming all 12 views displays the new name")
  @Description("The test creates 12 different views and renames them by adding 'Edited ' to the beginning of each name. Then it verifies if the name has changed in the View Chooser and Toolbar.")
  @Tag("TCE/Fix")
  public void testRenameView() {
    String newName = "Edited " + view.getDefaultName();
    
    ManageViewsPanel manageViewsPanel =
        projectSettingsPage.getProjectSettingsHeader().clickManageViewsButton();
    ViewDropdown viewDropdown = manageViewsPanel.clickViewMenu(view.getDefaultName());
    viewDropdown.clickRename().enterText(newName);

    verificationStep("Verify that the new view name appears");
    Assert.assertTrue("The new name is not present",
        manageViewsPanel.getAllViews().contains(newName));
    verificationStep("Verify that the view icon is the expected");
    Assert.assertEquals("The icon is not the expected", view,
        manageViewsPanel.getViewIcon(newName));
  }
  
}
