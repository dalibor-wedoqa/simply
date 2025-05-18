package net.simplyanalytics.suites;


import net.simplyanalytics.core.parallel.ConcurrentSuiteRunner;
import net.simplyanalytics.tests.*;
import net.simplyanalytics.tests.activeview.BarChartActiveViewTests;
import net.simplyanalytics.tests.activeview.CrosstabActiveViewTests;
import net.simplyanalytics.tests.activeview.ScarboroughActiveViewTests;
import net.simplyanalytics.tests.activeview.ScatterPlotActiveViewTests;
import net.simplyanalytics.tests.canada.DataSheetWindowTests;
import net.simplyanalytics.tests.cancelclose.*;
import net.simplyanalytics.tests.color.*;
import net.simplyanalytics.tests.filter.*;
import net.simplyanalytics.tests.importtest.*;
import net.simplyanalytics.tests.ldb.DisabledLDBTabTests;
import net.simplyanalytics.tests.ldb.business.AdvancedBusinessSearchTests;
import net.simplyanalytics.tests.ldb.business.BusinessToolbarTests;
import net.simplyanalytics.tests.ldb.business.RecentBusinessTests;
import net.simplyanalytics.tests.ldb.data.*;
import net.simplyanalytics.tests.ldb.location.*;
import net.simplyanalytics.tests.manageproject.*;
import net.simplyanalytics.tests.newproject.location.NewProjectFavoriteLocationTests;
import net.simplyanalytics.tests.newproject.location.NewProjectRecentLocationTests;
import net.simplyanalytics.tests.support.DataDocumentationTests;
import net.simplyanalytics.tests.support.SupportTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.appearance.*;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createbarchart.QuickReportCreateBarChartTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createmap.*;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createranking.*;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.alias.ComparisonTableAliasLocationTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.alias.RankingAliasLocationTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.alias.RelatedDataTableAliasLocationTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.createbarchart.ComparisonTableCreateBarChartTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.createbarchart.RelatedDataTableCreateBarChartTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites.*;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.hide.*;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.sort.BusinessesSortingTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.sort.ComparisonReportSortingTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.sort.RankingSortingTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata.*;
import net.simplyanalytics.tests.userbased.EditComboLocationTests;
import net.simplyanalytics.tests.userbased.MissingRequiredDataOpenViewTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(ConcurrentSuiteRunner.class)
@Suite.SuiteClasses({
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

        // net.simplyanalytics.tests.support
        DataDocumentationTests.class,
        SupportTests.class,

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

        // net.simplyanalytics.tests.userbased
//    ChangeEmailAfterLoginTests.class,
//    ChangePasswordAfterLoginTests.class,
        EditComboLocationTests.class,
        MissingRequiredDataOpenViewTests.class,
//    SessionExpiredTests.class,

})

public class EdgeSuite1 {
}
