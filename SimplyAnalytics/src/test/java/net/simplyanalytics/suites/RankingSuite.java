package net.simplyanalytics.suites;

import net.simplyanalytics.core.parallel.ConcurrentSuiteRunner;
import net.simplyanalytics.tests.filter.RankingFilterTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.appearance.RankingAppearanceTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createmap.RankingCreateMapTests;
import net.simplyanalytics.tests.tabledropdown.celldropdown.createranking.*;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.alias.RankingAliasLocationTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.favorites.RankingFavoritesTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.hide.RankingHideHeaderTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.sort.RankingSortingTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata.RankingViewMetadataTests;
import net.simplyanalytics.tests.view.actions.RankingToolbarTests;
import net.simplyanalytics.tests.view.edit.EditRankingTests;
import net.simplyanalytics.tests.view.export.RankingExportTests;
import net.simplyanalytics.tests.view.export.RankingTopXExportTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(ConcurrentSuiteRunner.class)
@Suite.SuiteClasses({
        RankingFilterTests.class,
        RankingAppearanceTests.class,
        RankingCreateMapTests.class,
        ComparisonTableCreateRankingTests.class,
        QuickReportCreateRankingTests.class,
        RankingCreateRankingTests.class,
        RelatedDataTableCreateRankingTests.class,
        TimeSeriesCreateRankingTests.class,
        RankingAliasLocationTests.class,
        RankingFavoritesTests.class,
        RankingHideHeaderTests.class,
        RankingSortingTests.class,
        RankingViewMetadataTests.class,
        RankingToolbarTests.class,
        EditRankingTests.class,
        RankingExportTests.class,
        RankingTopXExportTests.class
})

public class RankingSuite {
}
