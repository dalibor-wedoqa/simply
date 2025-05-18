package net.simplyanalytics.suites;

import net.simplyanalytics.tests.MinimalTest;
import net.simplyanalytics.tests.support.DataDocumentationTests;
import net.simplyanalytics.tests.support.SupportTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  MinimalTest.class
})
@SuppressWarnings("ucd")
public class MinimalSuite {

}
