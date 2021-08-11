package tech.linqu.spring.cloud.starter.utilities.jooq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tech.linqu.spring.cloud.starter.tests.schema.Tables.TEST;

import org.junit.jupiter.api.Test;

class ConditionBuilderTest {

    @Test
    void shouldBuildNullableConditionSuccess() {
        assertNull(ConditionBuilder.builder().nullable());
    }

    @Test
    void givenConditionIsNull_whenBuildCondition_shouldBuildSuccess() {
        String sql = ConditionBuilder.builder().and(null).or(null).build().toString();
        assertEquals("true", sql);
    }

    @Test
    void shouldBuildAndConditionSuccess() {
        String sql = ConditionBuilder.builder()
            .and(TEST.TEST1.eq(1L))
            .and(TEST.TEST1.eq(2L))
            .build().toString();
        assertEquals("(\n"
            + "  \"test\".\"test1\" = 1\n"
            + "  and \"test\".\"test1\" = 2\n"
            + ")", sql);
    }

    @Test
    void shouldBuildOrConditionSuccess() {
        String sql = ConditionBuilder.builder()
            .or(TEST.TEST1.eq(1L))
            .or(TEST.TEST1.eq(2L))
            .build().toString();
        assertEquals("(\n"
            + "  \"test\".\"test1\" = 1\n"
            + "  or \"test\".\"test1\" = 2\n"
            + ")", sql);
    }
}
