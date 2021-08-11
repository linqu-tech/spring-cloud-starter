package tech.linqu.spring.cloud.starter.utilities.jooq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static tech.linqu.spring.cloud.starter.tests.schema.Tables.TEST;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConnectByStep;
import org.jooq.impl.DefaultDSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import tech.linqu.spring.cloud.starter.tests.schema.tables.records.TestRecord;

class PageableQueryTest {

    private final DSLContext dsl = new DefaultDSLContext(JooqConfig.configuration(null));

    @SuppressWarnings("unchecked")
    private Page<?> fetch(Pageable pageable, int total) {
        SelectConnectByStep<TestRecord> dataQuery = spy(dsl.selectFrom(TEST));
        Result<TestRecord> result = mock(Result.class);
        when(result.toArray()).thenReturn(new Object[] {});
        doReturn(result).when(dataQuery).fetch();
        SelectConnectByStep<? extends Record> countQuery = spy(dsl.selectCount().from(TEST));
        doReturn(total).when(countQuery).fetchOne(0, Integer.class);
        return PageableQuery.of(TEST, pageable, dataQuery, countQuery).fetch();
    }

    @Test
    void shouldFetchPageableSuccess() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<?> page = fetch(pageable, 123);
        assertEquals(10, page.getSize());
    }

    @Test
    void shouldFetchUnpagedSuccess() {
        Page<?> page = fetch(Pageable.unpaged(), 123);
        assertEquals(123, page.getTotalElements());
    }

    @Test
    void shouldFetchPageableWithSortSuccess() {
        Pageable pageable =
            PageRequest.of(1, 10, Sort.by("test1").ascending().and(Sort.by("test2").descending()));
        Page<?> page = fetch(pageable, 123);
        assertEquals(10, page.getSize());
    }

    @Test
    void shouldFetchPageableWithValidSortSuccess() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("not_exists").ascending());
        Page<?> page = fetch(pageable, 123);
        assertEquals(10, page.getSize());
    }

    @Test
    void givenTotalLessThanPageSize_shouldFetchSuccess() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<?> page = fetch(pageable, 2);
        assertEquals(10, page.getSize());
    }
}
