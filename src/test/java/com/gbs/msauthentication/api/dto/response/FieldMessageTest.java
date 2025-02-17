package com.gbs.msauthentication.api.dto.response;

import com.gbs.msauthentication.api.FieldMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldMessageTest {
    @Test
    void testFieldMessage() {
        FieldMessage fieldMessage = new FieldMessage("field1", "must not be null");

        assertEquals("field1", fieldMessage.getFieldName());
        assertEquals("must not be null", fieldMessage.getMessage());

        fieldMessage.setFieldName("field2");
        fieldMessage.setMessage("must be a valid email");

        assertEquals("field2", fieldMessage.getFieldName());
        assertEquals("must be a valid email", fieldMessage.getMessage());
    }
}
