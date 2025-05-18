package net.simplyanalytics.tests.view;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.parallel.ConcurrentParameterizedTestRunner;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.BaseEditViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import java.util.ArrayList;
import java.util.List;

@RunWith(ConcurrentParameterizedTestRunner.class)
public class NewViewTestsCanada extends TestBase {

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
      //for Canada Crosstab is not enabled
      if (viewType != ViewType.CROSSTAB_TABLE && viewType != ViewType.SCARBOROUGH_CROSSTAB_TABLE)
        list.add(new ViewType[] { viewType });
    }
    return list;
  }

  public NewViewTestsCanada(ViewType view) {
    this.view = view;
  }

  /**
   * Signing in, creating new project, deleting all views and create new view.
   */
  @Before
  public void createProjectWithNoView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    createNewProjectWindow.selectCountry("Canada");
    MapPage mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.TORONTO_ON_CD);
    mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();

    ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    projectSettingsPage.getProjectSettingsHeader().clickManageViewsButton().deleteAllViews();

    newViewPage = projectSettingsPage.getViewChooserSection().clickNewView();
  }

  @Test
  public void testCreateView() {
    BaseEditViewPage editViewPage = newViewPage.getActiveView().clickCreate(view);
    MainPage viewPage = editViewPage.clickDone();

    verificationStep("Verify that the view name is the default: " + view.getDefaultName());
    Assert.assertEquals("The default view name is incorrect", view.getDefaultName(),
        viewPage.getViewChooserSection().getActiveViewName());
    verificationStep("Verify that the correct icon appears for the new view");
    Assert.assertEquals("The icon is incorrect", view,
        viewPage.getViewChooserSection().getViewType(view.getDefaultName()));
  }
}
