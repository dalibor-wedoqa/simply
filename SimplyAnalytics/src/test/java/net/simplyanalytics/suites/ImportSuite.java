package net.simplyanalytics.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import net.simplyanalytics.core.parallel.ConcurrentSuiteRunner;
import net.simplyanalytics.tests.importtest.ImportAsGuest;
import net.simplyanalytics.tests.importtest.ImportFavoriteDataTest;
import net.simplyanalytics.tests.importtest.ImportNewViewTests;
import net.simplyanalytics.tests.importtest.ImportTermsWindowTests;
import net.simplyanalytics.tests.importtest.ImportTest;
import net.simplyanalytics.tests.importtest.UseImportedData;

/**
 *Import suite.
 * @author wedoqa
 */
@RunWith(ConcurrentSuiteRunner.class)
@Suite.SuiteClasses({
//  ImportAsGuest.class,
//  ImportFavoriteDataTest.class,
//  ImportNewViewTests.class,
//  ImportTermsWindowTests.class,
  ImportTest.class,
//  UseImportedData.class,
})
@SuppressWarnings("ucd")
public class ImportSuite {

}
