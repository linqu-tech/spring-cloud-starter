package tech.linqu.spring.cloud.starter.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.TruncateIdentityStep;
import org.junit.jupiter.api.Test;
import tech.linqu.spring.cloud.starter.tests.model.TestClass1;
import tech.linqu.spring.cloud.starter.tests.model.TestClass2;
import tech.linqu.spring.cloud.starter.tests.model.TestClass3;

class DefaultDataSetProviderTest {

    @Test
    void shouldSetupSuccess() throws NoSuchMethodException {
        DSLContext dsl = mock(DSLContext.class);
        DefaultDataSetProvider provider = new DefaultDataSetProvider(dsl);
        provider.setup(TestClass1.class, TestClass1.class.getDeclaredMethod("test1"));
        verify(dsl, times(1)).executeInsert(any());

        provider.setup(TestClass1.class, TestClass1.class.getDeclaredMethod("test2"));
        provider.setup(TestClass1.class, TestClass1.class.getDeclaredMethod("test3"));
        provider.setup(TestClass1.class, TestClass1.class.getDeclaredMethod("test4"));
        provider.setup(TestClass1.class, TestClass1.class.getDeclaredMethod("test5"));
        provider.setup(TestClass1.class, TestClass1.class.getDeclaredMethod("test6"));
        provider.setup(TestClass1.class, TestClass1.class.getDeclaredMethod("test7"));
        provider.setup(TestClass2.class, TestClass2.class.getDeclaredMethod("test1"));
        provider.setup(TestClass3.class, TestClass3.class.getDeclaredMethod("test1"));
    }

    @Test
    void shouldSetupThrowException() {
        DSLContext dsl = mock(DSLContext.class);
        DefaultDataSetProvider provider = new DefaultDataSetProvider(dsl);
        when(dsl.executeInsert(any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class,
            () -> provider.setup(TestClass1.class, TestClass1.class.getDeclaredMethod("test1")));
    }

    @SuppressWarnings("unchecked")
    @Test
    <T extends TableRecord<?>> void shouldTeardownSuccess() throws NoSuchMethodException {
        DSLContext dsl = mock(DSLContext.class);
        TruncateIdentityStep<T> step = mock(TruncateIdentityStep.class);
        when(dsl.truncate((Table<T>) any())).thenReturn(step);
        DefaultDataSetProvider provider = new DefaultDataSetProvider(dsl);
        provider.teardown(TestClass1.class, TestClass1.class.getDeclaredMethod("test1"));
        verify(dsl, times(2)).truncate((Table<?>) any());

        provider.teardown(TestClass1.class, TestClass1.class.getDeclaredMethod("test2"));
        provider.teardown(TestClass1.class, TestClass1.class.getDeclaredMethod("test3"));
        provider.teardown(TestClass1.class, TestClass1.class.getDeclaredMethod("test4"));
        provider.teardown(TestClass1.class, TestClass1.class.getDeclaredMethod("test5"));
        provider.teardown(TestClass1.class, TestClass1.class.getDeclaredMethod("test6"));
        provider.teardown(TestClass1.class, TestClass1.class.getDeclaredMethod("test7"));
        provider.teardown(TestClass2.class, TestClass2.class.getDeclaredMethod("test1"));
        provider.teardown(TestClass3.class, TestClass3.class.getDeclaredMethod("test1"));
    }

    @Test
    void shouldTeardownThrowException() {
        DSLContext dsl = mock(DSLContext.class);
        DefaultDataSetProvider provider = new DefaultDataSetProvider(dsl);
        when(dsl.truncate((Table<?>) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class,
            () -> provider.teardown(TestClass1.class, TestClass1.class.getDeclaredMethod("test1")));
    }
}
