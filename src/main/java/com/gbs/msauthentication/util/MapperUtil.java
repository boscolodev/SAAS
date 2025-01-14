package com.gbs.msauthentication.util;

import org.modelmapper.ModelMapper;

public class MapperUtil {
    private static ModelMapper mapper = null;

    private MapperUtil() {
    }

    private static ModelMapper getMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
        }
        return mapper;
    }

    public static <T> T converte(Object origin, Class<T> destiny) {
        return MapperUtil.getMapper().map(origin, destiny);
    }

    public static void copyEntity(Object origin, Object destiny) {
        MapperUtil.getMapper().map(origin, destiny);
    }

}
