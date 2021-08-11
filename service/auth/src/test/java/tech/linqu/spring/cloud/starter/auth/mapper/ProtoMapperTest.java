package tech.linqu.spring.cloud.starter.auth.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import tech.linqu.spring.cloud.starter.auth.domain.UserStatus;
import tech.linqu.spring.cloud.starter.auth.schema.tables.records.UserRecord;
import tech.linqu.spring.cloud.starter.proto.imports.model.UserPb;

class ProtoMapperTest {

    @Test
    void shouldMappingUserRecordToPbSuccess() {
        ProtoMapper protoMapper = new ProtoMapperImpl();
        assertNull(protoMapper.mapping(null));

        UserRecord record = new UserRecord();
        UserPb userPb = protoMapper.mapping(record);
        assertNull(userPb.getStatus());
        assertNull(userPb.getGender());

        record.setStatus(UserStatus.INACTIVE);
        record.setGender((byte) 1);
        userPb = protoMapper.mapping(record);
        assertEquals(2, userPb.getStatus());
        assertEquals(1, userPb.getGender());
    }
}
