package net.simplyanalytics.suites;

import net.simplyanalytics.tests.filter.ComparisonReportFilterTests;
import net.simplyanalytics.tests.filter.GeneralFilterTests;
import net.simplyanalytics.tests.filter.MapFilterTests;
import net.simplyanalytics.tests.filter.RankingFilterTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    GeneralFilterTests.class,
    ComparisonReportFilterTests.class,
    MapFilterTests.class,
    RankingFilterTests.class,
})
@SuppressWarnings("ucd")
public class FilterTests {
  
}
