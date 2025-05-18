package net.simplyanalytics.suites;

import net.simplyanalytics.core.parallel.ConcurrentSuiteRunner;
import net.simplyanalytics.tests.view.export.BarChartExportTests;
import net.simplyanalytics.tests.view.export.BusinessesExportTests;
import net.simplyanalytics.tests.view.export.ComparisonReportExportTests;
import net.simplyanalytics.tests.view.export.CrosstabExportTests;
import net.simplyanalytics.tests.view.export.MapExportTests;
import net.simplyanalytics.tests.view.export.QuickExportTests;
import net.simplyanalytics.tests.view.export.RankingExportTests;
import net.simplyanalytics.tests.view.export.RankingTopXExportTests;
import net.simplyanalytics.tests.view.export.RelatedDataExportTests;
import net.simplyanalytics.tests.view.export.RingStudyExportTests;
import net.simplyanalytics.tests.view.export.ScarboroughExportTests;
import net.simplyanalytics.tests.view.export.ScatterPlotExportTests;
import net.simplyanalytics.tests.view.export.TimeSeriesExportTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *Export suite.
 * @author wedoqa
 */
@RunWith(ConcurrentSuiteRunner.class)
@Suite.SuiteClasses({
    BarChartExportTests.class,
    BusinessesExportTests.class,
    ComparisonReportExportTests.class,
    CrosstabExportTests.class,
    MapExportTests.class,
    QuickExportTests.class,
    RankingExportTests.class,
    RelatedDataExportTests.class,
    RingStudyExportTests.class,
    ScatterPlotExportTests.class,
    ScarboroughExportTests.class,
    TimeSeriesExportTests.class,
    RankingTopXExportTests.class })
@SuppressWarnings("ucd")
public class ExportSuite {

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
