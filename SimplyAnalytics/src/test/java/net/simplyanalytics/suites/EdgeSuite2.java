package net.simplyanalytics.suites;

import net.simplyanalytics.core.parallel.ConcurrentSuiteRunner;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addcountpercentage.AddCountPercentageCanadaTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addcountpercentage.AddCountPercentageTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addotheryears.AddOtherYearsCanadaTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addotheryears.AddOtherYearsTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder.OpenDataFolderCanadaTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder.OpenDataFolderTests;
import net.simplyanalytics.tests.view.NewViewTests;
import net.simplyanalytics.tests.view.NewViewTestsCanada;
import net.simplyanalytics.tests.view.RenameOrDeleteViewTests;
import net.simplyanalytics.tests.view.ViewChooserCensusReleaseBadgeTests;
import net.simplyanalytics.tests.view.actions.*;
import net.simplyanalytics.tests.view.barchart.BaseBarChart;
import net.simplyanalytics.tests.view.customtoolbaraction.MapViewToolbarActionTests;
import net.simplyanalytics.tests.view.customtoolbaraction.QuickReportToolbarActionTests;
import net.simplyanalytics.tests.view.edit.*;
import net.simplyanalytics.tests.view.edit.unsupportedvariables.UnsupportedDataVariablesTests;
import net.simplyanalytics.tests.view.export.*;
import net.simplyanalytics.tests.view.export.custommap.MapExportBackAndForthNavigationTests;
import net.simplyanalytics.tests.view.export.custommap.MapExportCroppingTests;
import net.simplyanalytics.tests.view.export.custommap.MapExportLayoutTests;
import net.simplyanalytics.tests.view.export.custommap.MapExportTextLabelTests;
import net.simplyanalytics.tests.view.histogram.HistogramContentTests;
import net.simplyanalytics.tests.view.map.*;
import net.simplyanalytics.tests.view.map.legend.LegendEditTests;
import net.simplyanalytics.tests.view.map.legend.LegendHeaderMenuPanelTests;
import net.simplyanalytics.tests.view.map.legend.LegendPanelTests;
import net.simplyanalytics.tests.view.quickreport.QuickReportTableContentTests;
import net.simplyanalytics.tests.view.quickreport.QuickReportTableHeaderTests;
import net.simplyanalytics.tests.view.scatterplot.legend.HSLTests;
import net.simplyanalytics.tests.view.scatterplot.legend.RGBTests;
import net.simplyanalytics.tests.view.scatterplot.legend.ScatterPlotLegendTests;
import net.simplyanalytics.tests.view.timeseries.BaseTimeSeriesTable;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(ConcurrentSuiteRunner.class)
@Suite.SuiteClasses({
        // net.simplyanalytics.tests.view
        NewViewTests.class,
        NewViewTestsCanada.class,
        RenameOrDeleteViewTests.class,
        ViewChooserCensusReleaseBadgeTests.class,

        // net.simplyanalytics.tests.view.unsupportedvariables
        UnsupportedDataVariablesTests.class,

        // net.simplyanalytics.tests.view.action
        BarChartViewActionsTests.class,
        BusinessViewActionTests.class,
        ComparisonViewActionTests.class,
        CrosstabTableActionsTests.class,
        HistogramViewActionsTests.class,
        QuickViewActionTests.class,
        MapViewActionTests.class,
        RankingToolbarTests.class,
        RelatedDataToolbarTest.class,
        RingStudyToolbarTest.class,
        ScarboroughCrosstabActionsTests.class,
        ScatterPlotViewActionsTests.class,
        TimeSeriesViewActionTests.class,

        // net.simplyanalytics.tests.view.barchart
        BaseBarChart.class,

        // net.simplyanalytics.tests.view.timeseries
        BaseTimeSeriesTable.class,

        //net.simplyanalytics.tests.view.customtoolbaraction
        QuickReportToolbarActionTests.class,
        MapViewToolbarActionTests.class,

        // net.simplyanalytics.tests.view.edit
        EditBarChartTests.class,
        EditBusinessTests.class,
        EditComparisonReportTests.class,
        EditCrosstabTests.class,
        EditMapTests.class,
        EditQuickReportTests.class,
        EditRankingTests.class,
        EditRelatedDataReportTests.class,
        EditRingStudyTests.class,
        EditScarboroughCrosstabTests.class,
        EditScatterPlotTests.class,
        EditTimeSeriesTableTests.class,

        // net.simplyanalytics.tests.view.export
        BarChartExportTests.class,
        BusinessesExportTests.class,
        ComparisonReportExportTests.class,
        CrosstabExportTests.class,
        HistogramExportTests.class,
        LimitMessageExportTests.class,
        MapExportTests.class,
        QuickExportTests.class,
        RankingExportTests.class,
        RankingTopXExportTests.class,
        RelatedDataExportTests.class,
        RingStudyExportTests.class,
        ScatterPlotExportTests.class,
        ScarboroughExportTests.class,
        TimeSeriesExportTests.class,

        // net.simplyanalitics.tests.view.export.custommap
        MapExportCroppingTests.class,
        MapExportLayoutTests.class,
        MapExportBackAndForthNavigationTests.class,
        MapExportTextLabelTests.class,

        // net.simplyanalytics.tests.view.histogram
        HistogramContentTests.class,

        // net.simplyanalytics.tests.view.map
        AutoLocationTypeTests.class,
        MapBusinessPointsTests.class,
        MapZoomTests.class,
        MiniMapTests.class,
        MapInformationTests.class,

        // net.simplyanalytics.tests.view.map.legend
        LegendEditTests.class,
        LegendHeaderMenuPanelTests.class,
        LegendPanelTests.class,

        // net.simplyanalitics.tests.view.quickreport
        QuickReportTableContentTests.class,
        QuickReportTableHeaderTests.class,

        // net.simplyanalytics.tests.view.scatterplot.legend
        HSLTests.class,
        RGBTests.class,
        ScatterPlotLegendTests.class,

        // net.simplyanalitics.tests.view.export.timeseries
        BaseTimeSeriesTable.class,

        // net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder	
        OpenDataFolderTests.class,	
        OpenDataFolderCanadaTests.class,

        // net.simplyanalytics.tests.tabledropdown.headerdropdown.addotheryears	
        AddOtherYearsTests.class,	
        AddOtherYearsCanadaTests.class,	
        
        // net.simplyanalytics.tests.tabledropdown.headerdropdown.addcountpercentage	
        AddCountPercentageTests.class,	
        AddCountPercentageCanadaTests.class,
})

@SuppressWarnings("ucd")
public class EdgeSuite2 {
}
