package net.simplyanalytics.suites;

import net.simplyanalytics.tests.filter.ScatterPlotFilterTests;
import net.simplyanalytics.tests.ldb.business.AdvancedBusinessSearchTests;
import net.simplyanalytics.tests.ldb.data.VariableBadgesTests;
import net.simplyanalytics.tests.ldb.data.VariableBadgesWithoutHistoricalViewTests;
import net.simplyanalytics.tests.manageproject.HistoricalViewsTests;
import net.simplyanalytics.tests.support.DataDocumentationTests;
import net.simplyanalytics.tests.support.SupportTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.addotheryears.AddOtherYearsCanadaTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder.OpenDataFolderCanadaTests;
import net.simplyanalytics.tests.tabledropdown.headerdropdown.opendatafolder.OpenDataFolderTests;
import net.simplyanalytics.tests.view.actions.CrosstabTableActionsTests;
import net.simplyanalytics.tests.view.edit.EditCrosstabTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        AdvancedBusinessSearchTests.class,
        EditCrosstabTests.class,
        ScatterPlotFilterTests.class
})
@SuppressWarnings("ucd")
public class LatestFixesSuite {
    }