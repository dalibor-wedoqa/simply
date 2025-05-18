package net.simplyanalytics.suites;

import net.simplyanalytics.core.parallel.ConcurrentSuiteRunner;
import net.simplyanalytics.tests.view.NewViewTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(ConcurrentSuiteRunner.class)
@Suite.SuiteClasses({
        NewViewTests.class
})

@SuppressWarnings("ucd")
public class NewViewSuite {
}
