package net.simplyanalytics.suites;

import net.simplyanalytics.core.parallel.ConcurrentSuiteRunner;
import net.simplyanalytics.tests.SignInTests;
import net.simplyanalytics.tests.activeview.BarChartActiveViewTests;
import net.simplyanalytics.tests.activeview.ScatterPlotActiveViewTests;
import net.simplyanalytics.tests.cancelclose.ExportCloseTest;
import net.simplyanalytics.tests.filter.ScatterPlotFilterTests;
import net.simplyanalytics.tests.importtest.ImportAsGuest;
import net.simplyanalytics.tests.importtest.ImportNewViewTests;
import net.simplyanalytics.tests.ldb.DisabledLDBTabTests;
import net.simplyanalytics.tests.ldb.business.AdvancedBusinessSearchTests;
import net.simplyanalytics.tests.ldb.business.RecentBusinessTests;
import net.simplyanalytics.tests.ldb.data.VariableBadgesTests;
import net.simplyanalytics.tests.manageproject.RenameViewTests;
import net.simplyanalytics.tests.newproject.location.NewProjectFavoriteLocationTests;
import net.simplyanalytics.tests.support.SupportTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.sort.BusinessesSortingTests;
import net.simplyanalytics.tests.view.NewViewTests;
import net.simplyanalytics.tests.view.RenameOrDeleteViewTests;
import net.simplyanalytics.tests.view.actions.TimeSeriesViewActionTests;
import net.simplyanalytics.tests.view.barchart.BaseBarChart;
import net.simplyanalytics.tests.view.edit.EditBarChartTests;
import net.simplyanalytics.tests.view.edit.EditQuickReportTests;
import net.simplyanalytics.tests.view.edit.unsupportedvariables.UnsupportedDataVariablesTests;
import net.simplyanalytics.tests.view.export.BarChartExportTests;
import net.simplyanalytics.tests.view.export.HistogramExportTests;
import net.simplyanalytics.tests.view.export.ScatterPlotExportTests;
import net.simplyanalytics.tests.view.export.custommap.MapExportCroppingTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(ConcurrentSuiteRunner.class)
@Suite.SuiteClasses({
        BarChartActiveViewTests.class,
        ScatterPlotActiveViewTests.class,
        ExportCloseTest.class,
        ImportNewViewTests.class,
        DisabledLDBTabTests.class,
        RenameOrDeleteViewTests.class,
        BaseBarChart.class,
        BarChartExportTests.class,
        HistogramExportTests.class,
        ScatterPlotExportTests.class,
        EditBarChartTests.class,
        UnsupportedDataVariablesTests.class,
        NewViewTests.class


})
@SuppressWarnings("ucd")

public class ElementClickInterceptedException {
}
