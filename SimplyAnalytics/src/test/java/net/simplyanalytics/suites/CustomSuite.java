package net.simplyanalytics.suites;

import net.simplyanalytics.tests.importtest.UseImportedData;
import net.simplyanalytics.tests.ldb.data.OpenDataFromGridLDB;
import net.simplyanalytics.tests.view.map.MiniMapTests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *.
 * @author Wedoqa
 */
@RunWith(Suite.class)
@SuiteClasses({ UseImportedData.class, MiniMapTests.class, OpenDataFromGridLDB.class, })
@SuppressWarnings("ucd")
public class CustomSuite {

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

}
