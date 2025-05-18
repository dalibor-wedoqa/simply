package net.simplyanalytics.tests.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

import net.simplyanalytics.core.HeadlessIssue;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.parallel.ConcurrentParameterizedTestRunner;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;

@RunWith(ConcurrentParameterizedTestRunner.class)
public class RenameOrDeleteViewTests extends TestBase {
  
  private final List<ViewType> views = Arrays.asList(ViewType.values());
  
  private NewViewPage newViewPage;
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
  
  public RenameOrDeleteViewTests(ViewType view) {
    this.view = view;
  }
  
  @Before
  public void createProjectWithOneOfEachView() {
    driver.manage().window().maximize();
    newViewPage = createOneViewFromEachTypeFix();
  }
  
  @Test
  public void testEditViewWithToolAction() {
    newViewPage.getViewChooserSection().clickView(view.getDefaultName()).getToolbar().clickViewActions().clickEditView();
    
    verificationStep("Verify that the edit view page appears properly");
  }


  @Test
  @HeadlessIssue
  public void testDeleteViewWithToolAction() {
    System.out.println("Step: Navigate to view using the default name of the view");
    BaseViewPage viewPage = newViewPage.getViewChooserSection().clickView(view.getDefaultName());
    System.out.println("Navigated to view with name: " + view.getDefaultName());

    System.out.println("Step: Delete the view using toolbar actions");
    ProjectSettingsPage projectSettingsPage = ((BaseViewPage) viewPage.getToolbar()
            .clickViewActions()
            .clickDeleteView())
            .getHeaderSection()
            .clickProjectSettings();
    System.out.println("View deleted and navigated to Project Settings Page");

    System.out.println("Step: Create a list of expected views after deletion");
    List<String> expectedViews = new ArrayList<>();
    views.stream()
            .filter(v -> !v.equals(view))
            .forEach(v -> expectedViews.add(v.getDefaultName()));
    System.out.println("Expected views after deletion: " + expectedViews);

    System.out.println("Step: Retrieve the actual list of views from the project settings");
    List<String> actualViews = projectSettingsPage
            .getProjectSettingsHeader()
            .clickManageViewsButton()
            .getAllViews();
    System.out.println("Actual views retrieved from project settings: " + actualViews);

    System.out.println("Step: Verify that the deleted view and only the deleted view disappears");
    verificationStep("Verify that the deleted view and only the deleted view disappears");
    boolean isMatch = expectedViews.containsAll(actualViews) && actualViews.containsAll(expectedViews);
    System.out.println("Verification result: " + isMatch);
    Assert.assertTrue("The name of views is not the expected", isMatch);
  }

  
  @Test
  public void testRenameViewWithClickingOnViewName() {
    String newName = "Edited " + view.getDefaultName();
    
    MainPage mainPage = newViewPage.getViewChooserSection().clickView(view.getDefaultName());
    mainPage.getViewChooserSection().clickActualViewName().enterText(newName);
    
    verificationStep("Verify that the new view name appears");
    Assert.assertEquals("The new name is not present", newName,
        mainPage.getViewChooserSection().getActiveViewName());
    verificationStep("Verify that the view icon is the expected");
    Assert.assertEquals("The icon is not the expected", view,
        mainPage.getViewChooserSection().getViewType(newName));
  }
  
  @Test
  @HeadlessIssue
  public void testDeleteViewWithViewChooserActionMenu() {
    
    MainPage mainPage = newViewPage.getViewChooserSection().clickView(view.getDefaultName());
    ProjectSettingsPage projectSettingsPage = ((BaseViewPage) mainPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickDelete()).getHeaderSection().clickProjectSettings();
    
    List<String> expectedViews = new ArrayList<>();
    views.stream().filter(v -> !v.equals(view)).forEach(v -> expectedViews.add(v.getDefaultName()));
    
    List<String> actualViews = new ArrayList<>();
    actualViews = projectSettingsPage.getProjectSettingsHeader().clickManageViewsButton().getAllViews();
    
    verificationStep("Verify that the deleted view and only the deleted view disappears");
    Assert.assertTrue("The name of views is not the expected", expectedViews.containsAll(actualViews) && actualViews.containsAll(expectedViews));
  }
  
  @Test
  public void testRenameViewWithViewChooserActionMenu() {
    String newName = "Edited " + view.getDefaultName();
    
    MainPage mainPage = newViewPage.getViewChooserSection().clickView(view.getDefaultName());
    mainPage.getViewChooserSection().openViewMenu(view.getDefaultName()).clickRename()
        .enterText(newName);
    
    verificationStep("Verify that the new view name appears");
    Assert.assertEquals("The new name is not present", newName,
        mainPage.getViewChooserSection().getActiveViewName());
    verificationStep("Verify that the view icon is the expected");
    Assert.assertEquals("The icon is not the expected", view,
        mainPage.getViewChooserSection().getViewType(newName));
  }
}
