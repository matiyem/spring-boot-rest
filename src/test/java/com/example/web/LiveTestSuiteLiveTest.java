package com.example.web;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
// @formatter:off
    FooDiscoverabilityLiveTest.class,
    com.example.web.FooLiveTest.class,
    com.example.web.FooPageableLiveTest.class
}) //
public class LiveTestSuiteLiveTest {

}
