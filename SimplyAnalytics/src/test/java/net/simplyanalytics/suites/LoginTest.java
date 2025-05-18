package net.simplyanalytics.suites;

import net.simplyanalytics.core.parallel.ConcurrentSuiteRunner;
import net.simplyanalytics.tests.DefaultDataTests;
import net.simplyanalytics.tests.EditProjectVariablesTests;
import net.simplyanalytics.tests.InstitutionLoginTests;
import net.simplyanalytics.tests.NewProjectTests;
import net.simplyanalytics.tests.SignInTests;
import net.simplyanalytics.tests.activeview.BarChartActiveViewTests;
import net.simplyanalytics.tests.canada.DataSheetWindowTests;
import net.simplyanalytics.tests.cancelclose.AliasLocationWindowCloseTests;
import net.simplyanalytics.tests.cancelclose.BusinessCloseTests;
import net.simplyanalytics.tests.filter.GeneralFilterTests;
import net.simplyanalytics.tests.importtest.ImportTest;
import net.simplyanalytics.tests.ldb.DisabledLDBTabTests;
import net.simplyanalytics.tests.ldb.business.AdvancedBusinessSearchTests;
import net.simplyanalytics.tests.ldb.data.BrowseDataByDatasetTests;
import net.simplyanalytics.tests.ldb.data.DataByCategoryDropdownTests;
import net.simplyanalytics.tests.ldb.location.RadiusLocationTests;
import net.simplyanalytics.tests.manageproject.ChangeViewOrderTests;
import net.simplyanalytics.tests.manageproject.RenameViewTests;
import net.simplyanalytics.tests.newproject.location.NewProjectRecentLocationTests;
import net.simplyanalytics.tests.support.DataDocumentationTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.appearance.RankingAppearanceTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createbarchart.QuickReportCreateBarChartTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createmap.ComparisonTableCreateMapTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createranking.QuickReportCreateRankingTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addcountpercentage.AddCountPercentageTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addotheryears.AddOtherYearsTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.alias.ComparisonTableAliasLocationTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.createbarchart.ComparisonTableCreateBarChartTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites.ComparisonTableFavoritesTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.hide.ComparisonTableHideHeaderTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder.OpenDataFolderTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.sort.BusinessesSortingTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata.ComparisonTableViewMetadataTests;
import net.simplyanalytics.tests.view.NewViewTests;
import net.simplyanalytics.tests.view.RenameOrDeleteViewTests;
import net.simplyanalytics.tests.view.actions.MapViewActionTests;
import net.simplyanalytics.tests.view.barchart.BaseBarChart;
import net.simplyanalytics.tests.view.customtoolbaraction.MapViewToolbarActionTests;
import net.simplyanalytics.tests.view.edit.EditBarChartTests;

import net.simplyanalytics.tests.view.edit.unsupportedvariables.UnsupportedDataVariablesTests;
import net.simplyanalytics.tests.view.export.TimeSeriesExportTests;
import net.simplyanalytics.tests.view.export.custommap.MapExportCroppingTests;
import net.simplyanalytics.tests.view.map.MapInformationTests;
import net.simplyanalytics.tests.view.map.legend.LegendPanelTests;
import net.simplyanalytics.tests.view.quickreport.QuickReportTableHeaderTests;
import net.simplyanalytics.tests.view.timeseries.BaseTimeSeriesTable;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ConcurrentSuiteRunner.class)
@SuiteClasses({ 
    SignInTests.class,
    
})
@SuppressWarnings("ucd")
public class LoginTest {
  
}
