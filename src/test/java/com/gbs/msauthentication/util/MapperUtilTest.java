package com.gbs.msauthentication.util;

import com.gbs.msauthentication.ModelFactory;
import com.gbs.msauthentication.api.dto.response.UserResponseDTO;
import com.gbs.msauthentication.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperUtilTest {

    @Test
    void testConverteUserRequestToUserResponse() {


        User origin = ModelFactory.createUser();
        origin.getRoles().add(ModelFactory.createRole());
        UserResponseDTO desiny = MapperUtil.converte(origin, UserResponseDTO.class);

        assertEquals(origin.getEmail(), desiny.getEmail());
        assertEquals(origin.getRoles().size(), desiny.getRoles().size());
    }

    @Test
    void testCopyEntityUserRequestToUserResponse() {

        User origin = ModelFactory.createUser();
        User destiny = new User();
        MapperUtil.copyEntity(origin, destiny);

        assertEquals(origin.getEmail(), destiny.getEmail());
        assertEquals(origin.getUserData(), destiny.getUserData());
    }
}