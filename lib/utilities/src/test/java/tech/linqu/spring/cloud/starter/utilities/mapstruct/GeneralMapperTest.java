package tech.linqu.spring.cloud.starter.utilities.mapstruct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tech.linqu.spring.cloud.starter.utilities.enumeration.Enumeration;
import tech.linqu.spring.cloud.starter.utilities.enumeration.IntegerEnumMapper;

class GeneralMapperTest {

    private enum ErrorEnum implements Enumeration {
        ERROR1;

        @Override
        public int getValue() {
            return 0;
        }
    }

    private enum TestEnum implements Enumeration {
        TEST1(0),
        TEST2(1),
        TEST3(2);

        private final int value;

        private static final IntegerEnumMapper<TestEnum> mapper = new IntegerEnumMapper<>(values());

        public static TestEnum fromValue(Integer value) {
            return mapper.fromValue(value);
        }

        TestEnum(int value) {
            this.value = value;
        }

        @Override
        public int getValue() {
            return value;
        }
    }

    @Test
    void shouldIntegerToEnumSuccess() {
        GeneralMapper mapper = new GeneralMapper();
        assertEquals(TestEnum.TEST1, mapper.integerToEnum(0, TestEnum.class));
    }

    @Test
    void shouldIntegerToEnumThrowException() {
        GeneralMapper mapper = new GeneralMapper();
        assertThrows(NoSuchMethodException.class, () -> mapper.integerToEnum(0, ErrorEnum.class));
    }

    @Test
    void shouldEnumToIntegerSuccess() {
        assertEquals(0, GeneralMapper.enumToInteger(TestEnum.TEST1));
        assertNull(GeneralMapper.enumToInteger(null));
    }
}
