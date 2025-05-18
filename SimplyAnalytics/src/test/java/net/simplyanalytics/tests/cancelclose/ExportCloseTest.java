package net.simplyanalytics.tests.cancelclose;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.core.parallel.ConcurrentParameterizedTestRunner;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

@RunWith(ConcurrentParameterizedTestRunner.class)
public class ExportCloseTest extends TestBase {

  private NewViewPage newViewPage;
  private ViewType view;

  private ExportWindow exportWindow;

  /**
   * .
   * @return 
   */
  @Parameters(name = "{index}: view {0}")
  public static List<ViewType[]> data() {
    List<ViewType[]> list = new ArrayList<>();
    for (ViewType viewType : ViewType.values()) {
      if (!(viewType.equals(ViewType.BAR_CHART) || viewType.equals(ViewType.MAP)
          || viewType.equals(ViewType.QUICK_REPORT) || viewType.equals(ViewType.RANKING) || viewType.equals(ViewType.HISTOGRAM))) {
        list.add(new ViewType[] { viewType });
      }
    }
    return list;
  }

  public ExportCloseTest(ViewType view) {
    this.view = view;
  }

  @Before
  public void before() {
    driver.manage().window().maximize();
    newViewPage = createOneViewFromEachTypeFix();
  }

  @Test
  public void testExportClose() {
    BaseViewPage viewPage = newViewPage.getViewChooserSection().clickView(view.getDefaultName());
    exportWindow = (ExportWindow) viewPage.getToolbar().clickExport();

    exportWindow.clickCancel();

    verificationStep("Verify that the Export dialog is disappeared");
    Assert.assertFalse("The Export dialog should be disappeared", exportWindow.isDisplayed());

  }

}
