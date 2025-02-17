package com.gbs.msauthentication.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {

    @Test
    void testUserDataConstructor() {
        UserData userData = new UserData("John", "Doe", "12345678901", "MG1234567", "01-01-1990", "123456789");
        assertEquals("John", userData.getNome());
        assertEquals("Doe", userData.getSobrenome());
        assertEquals("12345678901", userData.getCpfCnpj());
        assertEquals("MG1234567", userData.getRgIe());
        assertEquals("01-01-1990", userData.getDataNascimento());
        assertEquals("123456789", userData.getTelefone());
    }

    @Test
    void testSettersAndGetters() {
        UserData userData = new UserData();
        userData.setNome("Jane");
        userData.setSobrenome("Smith");
        userData.setCpfCnpj("10987654321");
        userData.setRgIe("SP7654321");
        userData.setDataNascimento("02-02-1992");
        userData.setTelefone("987654321");

        assertEquals("Jane", userData.getNome());
        assertEquals("Smith", userData.getSobrenome());
        assertEquals("10987654321", userData.getCpfCnpj());
        assertEquals("SP7654321", userData.getRgIe());
        assertEquals("02-02-1992", userData.getDataNascimento());
        assertEquals("987654321", userData.getTelefone());
    }
}