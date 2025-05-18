package net.simplyanalytics.suites;

import net.simplyanalytics.tests.ldb.data.FavoriteDataTests;
import net.simplyanalytics.tests.manageproject.RenameViewTests;
import net.simplyanalytics.tests.view.RenameOrDeleteViewTests;
import net.simplyanalytics.tests.view.barchart.BaseBarChart;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    //FavoriteDataTests.class,

    BaseBarChart.class,
    RenameOrDeleteViewTests.class,
    RenameViewTests.class,

})

@SuppressWarnings("ucd")
public class RecentAndFavoriteTestsSuite {
  
}
