package net.simplyanalytics.suites;

import net.simplyanalytics.tests.exportlimit.BusinessTableExportLimitTests;
import net.simplyanalytics.tests.ldb.data.*;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addcountpercentage.AddCountPercentageCanadaTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addcountpercentage.AddCountPercentageTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addotheryears.AddOtherYearsCanadaTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addotheryears.AddOtherYearsTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder.OpenDataFolderCanadaTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder.OpenDataFolderTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import net.simplyanalytics.core.parallel.ConcurrentSuiteRunner;
import net.simplyanalytics.tests.DefaultDataTests;
import net.simplyanalytics.tests.EditProjectVariablesTests;
import net.simplyanalytics.tests.InstitutionLoginTests;
import net.simplyanalytics.tests.NewProjectTests;
import net.simplyanalytics.tests.SignInTests;
import net.simplyanalytics.tests.WelcomeTourTests;
import net.simplyanalytics.tests.activeview.BarChartActiveViewTests;
import net.simplyanalytics.tests.activeview.CrosstabActiveViewTests;
import net.simplyanalytics.tests.activeview.ScarboroughActiveViewTests;
import net.simplyanalytics.tests.activeview.ScatterPlotActiveViewTests;
import net.simplyanalytics.tests.canada.DataSheetWindowTests;
import net.simplyanalytics.tests.cancelclose.AliasLocationWindowCloseTests;
import net.simplyanalytics.tests.cancelclose.BusinessCloseTests;
import net.simplyanalytics.tests.cancelclose.CloseWelcomeTourTest;
import net.simplyanalytics.tests.cancelclose.ColorSelectionPanelCloseTests;
import net.simplyanalytics.tests.cancelclose.DataCloseTests;
import net.simplyanalytics.tests.cancelclose.DataSheetWindowCloseTests;
import net.simplyanalytics.tests.cancelclose.DeleteAlertCloseTest;
import net.simplyanalytics.tests.cancelclose.ExportCloseSpecialCasesTests;
import net.simplyanalytics.tests.cancelclose.ExportCloseTest;
import net.simplyanalytics.tests.cancelclose.ExportShapefilesWindowCloseTests;
import net.simplyanalytics.tests.cancelclose.FilteringCloseTests;
import net.simplyanalytics.tests.cancelclose.LocationCloseTests;
import net.simplyanalytics.tests.cancelclose.MapExportCloseTests;
import net.simplyanalytics.tests.cancelclose.MapExportLayoutCloseTests;
import net.simplyanalytics.tests.cancelclose.MetadataWindowCloseTest;
import net.simplyanalytics.tests.cancelclose.ResetPasswordPageCancelTest;
import net.simplyanalytics.tests.cancelclose.TextLablePanelCloseTests;
import net.simplyanalytics.tests.color.HslTests;
import net.simplyanalytics.tests.color.OldColorTests;
import net.simplyanalytics.tests.color.PaletteTests;
import net.simplyanalytics.tests.color.RecentColorTests;
import net.simplyanalytics.tests.color.RgbTests;
import net.simplyanalytics.tests.filter.ComparisonReportFilterTests;
import net.simplyanalytics.tests.filter.GeneralFilterTests;
import net.simplyanalytics.tests.filter.MapFilterTests;
import net.simplyanalytics.tests.filter.RankingFilterTests;
import net.simplyanalytics.tests.filter.ScatterPlotFilterTests;
import net.simplyanalytics.tests.importtest.ImportAsGuest;
import net.simplyanalytics.tests.importtest.ImportFavoriteDataTest;
import net.simplyanalytics.tests.importtest.ImportNewViewTests;
import net.simplyanalytics.tests.importtest.ImportTermsWindowTests;
import net.simplyanalytics.tests.importtest.ImportTest;
import net.simplyanalytics.tests.importtest.UseImportedData;
import net.simplyanalytics.tests.ldb.DisabledLDBTabTests;
import net.simplyanalytics.tests.ldb.business.AdvancedBusinessSearchTests;
import net.simplyanalytics.tests.ldb.business.BusinessToolbarTests;
import net.simplyanalytics.tests.ldb.business.RecentBusinessTests;
import net.simplyanalytics.tests.ldb.location.CombinatedCombinationAndRadiusLocationTests;
import net.simplyanalytics.tests.ldb.location.CombinationLocationTests;
import net.simplyanalytics.tests.ldb.location.FavoriteCustomLocationTests;
import net.simplyanalytics.tests.ldb.location.FavoriteLocationTests;
import net.simplyanalytics.tests.ldb.location.RadiusLocationForDifferentViewsTests;
import net.simplyanalytics.tests.ldb.location.RadiusLocationTests;
import net.simplyanalytics.tests.ldb.location.RecentCustomLocationTests;
import net.simplyanalytics.tests.ldb.location.RecentLocationTests;
import net.simplyanalytics.tests.manageproject.ChangeViewOrderTests;
import net.simplyanalytics.tests.manageproject.CheckWarningsTests;
import net.simplyanalytics.tests.manageproject.DeleteLdbTests;
import net.simplyanalytics.tests.manageproject.DeleteProjectTests;
import net.simplyanalytics.tests.manageproject.GeographicalUnitsTests;
import net.simplyanalytics.tests.manageproject.HistoricalViewsTests;
import net.simplyanalytics.tests.manageproject.HistoricalYearDropdownTests;
import net.simplyanalytics.tests.manageproject.RenameViewTests;
import net.simplyanalytics.tests.newproject.location.NewProjectFavoriteLocationTests;
import net.simplyanalytics.tests.newproject.location.NewProjectRecentLocationTests;
import net.simplyanalytics.tests.support.DataDocumentationTests;
import net.simplyanalytics.tests.support.SupportTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.appearance.ComparisonTableAppearanceTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.appearance.QuickReportAppearanceTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.appearance.RankingAppearanceTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.appearance.RelatedDataTableAppearanceTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.appearance.TimeSeriesTableAppearanceTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createbarchart.QuickReportCreateBarChartTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createmap.ComparisonTableCreateMapTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createmap.QuickReportCreateMapTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createmap.RankingCreateMapTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createmap.RelatedDataTableCreateMapTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createmap.TimeSeriesCreateMapTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createranking.ComparisonTableCreateRankingTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createranking.QuickReportCreateRankingTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createranking.RankingCreateRankingTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createranking.RelatedDataTableCreateRankingTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createranking.TimeSeriesCreateRankingTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.alias.ComparisonTableAliasLocationTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.alias.RankingAliasLocationTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.alias.RelatedDataTableAliasLocationTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.createbarchart.ComparisonTableCreateBarChartTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.createbarchart.RelatedDataTableCreateBarChartTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites.ComparisonTableFavoritesTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites.CrosstabFavoritesTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites.RankingFavoritesTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites.RelatedDataTableFavoritesTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites.RingStudyFavoritesTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites.ScarboroughCrosstabFavoritesTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.hide.ComparisonTableHideHeaderTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.hide.CrosstabHideHeaderTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.hide.RankingHideHeaderTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.hide.RelatedDataTableHideHeaderTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.hide.RingStudyHideHeaderTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.hide.ScarboroughCrosstabHideHeaderTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.sort.BusinessesSortingTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.sort.ComparisonReportSortingTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.sort.RankingSortingTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata.ComparisonTableViewMetadataTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata.CrosstabViewMetadataTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata.RankingViewMetadataTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata.RelatedDataTableViewMetadataTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata.RingStudyViewMetadataTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata.ScarboroughCrosstabViewMetadataTests;
import net.simplyanalytics.tests.userbased.EditComboLocationTests;
import net.simplyanalytics.tests.userbased.MissingRequiredDataOpenViewTests;
//import net.simplyanalytics.tests.userbased.SessionExpiredTests;
import net.simplyanalytics.tests.view.NewViewTests;
import net.simplyanalytics.tests.view.NewViewTestsCanada;
import net.simplyanalytics.tests.view.RenameOrDeleteViewTests;
import net.simplyanalytics.tests.view.ViewChooserCensusReleaseBadgeTests;
import net.simplyanalytics.tests.view.actions.BarChartViewActionsTests;
import net.simplyanalytics.tests.view.actions.BusinessViewActionTests;
import net.simplyanalytics.tests.view.actions.ComparisonViewActionTests;
import net.simplyanalytics.tests.view.actions.CrosstabTableActionsTests;
import net.simplyanalytics.tests.view.actions.HistogramViewActionsTests;
import net.simplyanalytics.tests.view.actions.MapViewActionTests;
import net.simplyanalytics.tests.view.actions.QuickViewActionTests;
import net.simplyanalytics.tests.view.actions.RankingToolbarTests;
import net.simplyanalytics.tests.view.actions.RelatedDataToolbarTest;
import net.simplyanalytics.tests.view.actions.RingStudyToolbarTest;
import net.simplyanalytics.tests.view.actions.ScarboroughCrosstabActionsTests;
import net.simplyanalytics.tests.view.actions.ScatterPlotViewActionsTests;
import net.simplyanalytics.tests.view.actions.TimeSeriesViewActionTests;
import net.simplyanalytics.tests.view.barchart.BaseBarChart;
import net.simplyanalytics.tests.view.customtoolbaraction.MapViewToolbarActionTests;
import net.simplyanalytics.tests.view.customtoolbaraction.QuickReportToolbarActionTests;
import net.simplyanalytics.tests.view.edit.EditBarChartTests;
import net.simplyanalytics.tests.view.edit.EditBusinessTests;
import net.simplyanalytics.tests.view.edit.EditComparisonReportTests;
import net.simplyanalytics.tests.view.edit.EditCrosstabTests;
import net.simplyanalytics.tests.view.edit.EditMapTests;
import net.simplyanalytics.tests.view.edit.EditQuickReportTests;
import net.simplyanalytics.tests.view.edit.EditRankingTests;
import net.simplyanalytics.tests.view.edit.EditRelatedDataReportTests;
import net.simplyanalytics.tests.view.edit.EditRingStudyTests;
import net.simplyanalytics.tests.view.edit.EditScarboroughCrosstabTests;
import net.simplyanalytics.tests.view.edit.EditScatterPlotTests;
import net.simplyanalytics.tests.view.edit.EditTimeSeriesTableTests;
import net.simplyanalytics.tests.view.edit.unsupportedvariables.UnsupportedDataVariablesTests;
import net.simplyanalytics.tests.view.export.BarChartExportTests;
import net.simplyanalytics.tests.view.export.BusinessesExportTests;
import net.simplyanalytics.tests.view.export.ComparisonReportExportTests;
import net.simplyanalytics.tests.view.export.CrosstabExportTests;
import net.simplyanalytics.tests.view.export.HistogramExportTests;
import net.simplyanalytics.tests.view.export.LimitMessageExportTests;
import net.simplyanalytics.tests.view.export.MapExportTests;
import net.simplyanalytics.tests.view.export.QuickExportTests;
import net.simplyanalytics.tests.view.export.RankingExportTests;
import net.simplyanalytics.tests.view.export.RankingTopXExportTests;
import net.simplyanalytics.tests.view.export.RelatedDataExportTests;
import net.simplyanalytics.tests.view.export.RingStudyExportTests;
import net.simplyanalytics.tests.view.export.ScarboroughExportTests;
import net.simplyanalytics.tests.view.export.ScatterPlotExportTests;
import net.simplyanalytics.tests.view.export.TimeSeriesExportTests;
import net.simplyanalytics.tests.view.export.custommap.MapExportBackAndForthNavigationTests;
import net.simplyanalytics.tests.view.export.custommap.MapExportCroppingTests;
import net.simplyanalytics.tests.view.export.custommap.MapExportLayoutTests;
import net.simplyanalytics.tests.view.export.custommap.MapExportTextLabelTests;
import net.simplyanalytics.tests.view.histogram.HistogramContentTests;
import net.simplyanalytics.tests.view.map.AutoLocationTypeTests;
//import net.simplyanalytics.tests.view.map.MapBusinessPointsTests;
import net.simplyanalytics.tests.view.map.MapInformationTests;
import net.simplyanalytics.tests.view.map.MapZoomTests;
import net.simplyanalytics.tests.view.map.MiniMapTests;
import net.simplyanalytics.tests.view.map.legend.LegendEditTests;
import net.simplyanalytics.tests.view.map.legend.LegendHeaderMenuPanelTests;
import net.simplyanalytics.tests.view.map.legend.LegendPanelTests;
import net.simplyanalytics.tests.view.quickreport.QuickReportTableContentTests;
import net.simplyanalytics.tests.view.quickreport.QuickReportTableHeaderTests;
import net.simplyanalytics.tests.view.scatterplot.legend.HSLTests;
import net.simplyanalytics.tests.view.scatterplot.legend.RGBTests;
import net.simplyanalytics.tests.view.scatterplot.legend.ScatterPlotLegendTests;
import net.simplyanalytics.tests.view.timeseries.BaseTimeSeriesTable;

@RunWith(ConcurrentSuiteRunner.class)
@SuiteClasses({
        // net.simplyanalytics.tests.view
        NewViewTests.class,
        NewViewTestsCanada.class,

        RenameOrDeleteViewTests.class,
        ViewChooserCensusReleaseBadgeTests.class,

        // net.simplyanalytics.tests.support
        DataDocumentationTests.class,
        SupportTests.class,
        
    // net.simplyanalytics.tests
    DefaultDataTests.class,
    EditProjectVariablesTests.class,
    InstitutionLoginTests.class,
    NewProjectTests.class,
    SignInTests.class,
    WelcomeTourTests.class,
    
    // net.simplyanalitics.tests.activeview
    BarChartActiveViewTests.class,
    CrosstabActiveViewTests.class,
    ScarboroughActiveViewTests.class,
    ScatterPlotActiveViewTests.class,
    VariableBadgesTests.class,
    VariableBadgesWithoutHistoricalViewTests.class,
    
    // net.simplyanalytics.tests.canada
    DataSheetWindowTests.class,
    
    // net.simplyanalytics.tests.cancelclose
    AliasLocationWindowCloseTests.class,
    BusinessCloseTests.class,
    CloseWelcomeTourTest.class,
    ColorSelectionPanelCloseTests.class,
    DataCloseTests.class,
    DataSheetWindowCloseTests.class,
    DeleteAlertCloseTest.class,
    ExportCloseSpecialCasesTests.class,
    ExportCloseTest.class,
    ExportShapefilesWindowCloseTests.class,
    FilteringCloseTests.class,
    LocationCloseTests.class,
    MapExportCloseTests.class,
    MapExportLayoutCloseTests.class,
    MetadataWindowCloseTest.class,
    ResetPasswordPageCancelTest.class,
    TextLablePanelCloseTests.class,
    
    // net.simplyanalytics.tests.color
    HslTests.class,
    OldColorTests.class,
    PaletteTests.class,
    RecentColorTests.class,
    RgbTests.class,
    
    // net.simplyanalytics.tests.filter
    ComparisonReportFilterTests.class,
    GeneralFilterTests.class,
    MapFilterTests.class,
    RankingFilterTests.class,
    ScatterPlotFilterTests.class,
    
    // net.simplyanalytics.tests.importtest
    ImportTest.class,
    UseImportedData.class,
    ImportFavoriteDataTest.class,
    ImportNewViewTests.class,
    ImportTermsWindowTests.class,
    ImportAsGuest.class,
    
    // net.simplyanalytics.tests.ldb
    DisabledLDBTabTests.class,
    
    // net.simplyanalytics.tests.ldb.business
    AdvancedBusinessSearchTests.class,
    BusinessToolbarTests.class,
    RecentBusinessTests.class,
    
    // net.simplyanalytics.tests.ldb.data
    AmericanCommunityDatasetTests.class,
    BrowseDataByDatasetTests.class,
    ChoseDataVariableWithSearchFieldTests.class,
    ConsumerExpeditureExtimatesDatasetTests.class,
    DataByCategoryDropdownTests.class,
    DataPackageTests.class,
    FavoriteDataTests.class,
    FilteredDataDeleteTest.class,
    OpenDataFromGridLDB.class,
    RecentDataTests.class,
    UseDataVariablesFromDifferentCategoriesTest.class,
    
    // net.simplyanalytics.tests.ldb.location
    CombinatedCombinationAndRadiusLocationTests.class,
    CombinationLocationTests.class,
    FavoriteCustomLocationTests.class,
    FavoriteLocationTests.class,
    RadiusLocationForDifferentViewsTests.class,
    RadiusLocationTests.class,
    RecentCustomLocationTests.class,
    RecentLocationTests.class,
    
    // net.simplyanalytics.tests.manageproject
    ChangeViewOrderTests.class,
    CheckWarningsTests.class,
    GeographicalUnitsTests.class,
    DeleteLdbTests.class,
    DeleteProjectTests.class,
    HistoricalViewsTests.class,
    HistoricalYearDropdownTests.class,
    RenameViewTests.class,
    
    // net.simplyanalytics.tests.newproject.location
    NewProjectFavoriteLocationTests.class,
    NewProjectRecentLocationTests.class,
    
    // net.simplyanalytics.tests.tabledropdown.celldropdown.appearance
    ComparisonTableAppearanceTests.class,
    QuickReportAppearanceTests.class,
    RankingAppearanceTests.class,
    RelatedDataTableAppearanceTests.class,
    TimeSeriesTableAppearanceTests.class,
    
    // net.simplyanalytics.tests.tabledropdown.celldropdown.createbarchart
    QuickReportCreateBarChartTests.class,
    
    // net.simplyanalytics.tests.tabledropdown.celldropdown.createmap
    ComparisonTableCreateMapTests.class,
    QuickReportCreateMapTests.class,
    RankingCreateMapTests.class,
    RelatedDataTableCreateMapTests.class,
    TimeSeriesCreateMapTests.class,
    
    // net.simplyanalytics.tests.tabledropdown.celldropdown.createranking
    ComparisonTableCreateRankingTests.class,
    QuickReportCreateRankingTests.class,
    RankingCreateRankingTests.class,
    RelatedDataTableCreateRankingTests.class,
    TimeSeriesCreateRankingTests.class,
    
    // net.simplyanalytics.tests.tabledropdown.headerdropdown.alias
    ComparisonTableAliasLocationTests.class,
    RankingAliasLocationTests.class,
    RelatedDataTableAliasLocationTests.class,
    
    // net.simplyanalytics.tests.tabledropdown.headerdropdown.createbarchart
    ComparisonTableCreateBarChartTests.class,
    RelatedDataTableCreateBarChartTests.class,
    
    // net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites
    ComparisonTableFavoritesTests.class,
    CrosstabFavoritesTests.class,
    RankingFavoritesTests.class,
    RelatedDataTableFavoritesTests.class,
    RingStudyFavoritesTests.class,
    ScarboroughCrosstabFavoritesTests.class,
    
    // net.simplyanalytics.tests.tabledropdown.headerdropdown.hide
    ComparisonTableHideHeaderTests.class,
    CrosstabHideHeaderTests.class,
    RankingHideHeaderTests.class,
    RelatedDataTableHideHeaderTests.class,
    RingStudyHideHeaderTests.class,
    ScarboroughCrosstabHideHeaderTests.class,
    
    // net.simplyanalytics.tests.tabledropdown.headerdropdown.sort
    BusinessesSortingTests.class,
    ComparisonReportSortingTests.class,
    RankingSortingTests.class,
    
    // net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata
    ComparisonTableViewMetadataTests.class,
    CrosstabViewMetadataTests.class,
    RankingViewMetadataTests.class,
    RelatedDataTableViewMetadataTests.class,
    RingStudyViewMetadataTests.class,
    ScarboroughCrosstabViewMetadataTests.class,

    // net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder
        OpenDataFolderTests.class,
        OpenDataFolderCanadaTests.class,

    // net.simplyanalytics.tests.tabledropdown.headerdropdown.addotheryears
        AddOtherYearsTests.class,
        AddOtherYearsCanadaTests.class,

    // net.simplyanalytics.tests.tabledropdown.headerdropdown.addcountpercentage
        AddCountPercentageTests.class,
        AddCountPercentageCanadaTests.class,

        // net.simplyanalytics.tests.userbased
//    ChangeEmailAfterLoginTests.class,
//    ChangePasswordAfterLoginTests.class,
    EditComboLocationTests.class,
    MissingRequiredDataOpenViewTests.class,
//    SessionExpiredTests.class,
    

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
    //MapBusinessPointsTests.class,
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

    // net.simplyanalytics.tests.exportlimit
    BusinessTableExportLimitTests.class,

})

@SuppressWarnings("ucd")
public class AllTests {
  
}
