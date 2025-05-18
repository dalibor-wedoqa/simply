package net.simplyanalytics.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.simplyanalytics.tests.support.DataDocumentationTests;
import net.simplyanalytics.tests.support.SupportTests;

@RunWith(Suite.class)
@SuiteClasses({
  DataDocumentationTests.class,
  SupportTests.class,
})
@SuppressWarnings("ucd")
public class SupportSuite {

}
