package net.simplyanalytics.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.simplyanalytics.tests.view.actions.CrosstabTableActionsTests;
import net.simplyanalytics.tests.view.edit.EditCrosstabTests;
import net.simplyanalytics.tests.view.export.CrosstabExportTests;

@RunWith(Suite.class)
@SuiteClasses({
    CrosstabExportTests.class,
    CrosstabTableActionsTests.class,
    EditCrosstabTests.class,
})
@SuppressWarnings("ucd")
public class CrosstabSuite {

}
