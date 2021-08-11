package tech.linqu.spring.cloud.starter.tests.model;

import tech.linqu.spring.cloud.starter.tests.annotation.DataProvider;
import tech.linqu.spring.cloud.starter.tests.annotation.TeardownTables;

/**
 * Class for test.
 */
@DataProvider(value = { TestDataProvider.Test1.class })
@TeardownTables(value = TestTablesProvider.class)
public class TestClass1 {

    void test1() {
    }

    @DataProvider
    void test2() {
    }

    @DataProvider(value = { TestDataProvider.Test1.class })
    void test3() {
    }

    @DataProvider(value = { TestDataProvider.Test1.class }, merge = true)
    void test4() {
    }

    @DataProvider(value = { TestDataProvider.Test1.class }, ignored = true)
    void test5() {
    }

    @DataProvider(provider = TestDataProvider.Test2.class)
    void test6() {
    }

    @DataProvider(provider = TestDataProvider.Test3.class)
    void test7() {
    }
}
