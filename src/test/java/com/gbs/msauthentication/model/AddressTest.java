package com.gbs.msauthentication.model;

import com.gbs.msauthentication.ModelFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void testSettersAndGetters() {
        Address address = new Address();
        address.setId("2");
        address.setCep("87654-321");
        address.setLogradouro("Rua B");
        address.setNumero("321");
        address.setComplemento("Apto 2");
        address.setBairro("Bairro C");
        address.setLocalidade("Cidade D");
        address.setUf("UF2");
        address.setEstado("Estado F");
        address.setRegiao("Região S");
        address.setIbge("7654321");
        address.setGia("GIA2");
        address.setDdd("22");
        address.setSiafi("SIAFI2");

        assertEquals("2", address.getId());
        assertEquals("87654-321", address.getCep());
        assertEquals("Rua B", address.getLogradouro());
        assertEquals("321", address.getNumero());
        assertEquals("Apto 2", address.getComplemento());
        assertEquals("Bairro C", address.getBairro());
        assertEquals("Cidade D", address.getLocalidade());
        assertEquals("UF2", address.getUf());
        assertEquals("Estado F", address.getEstado());
        assertEquals("Região S", address.getRegiao());
        assertEquals("7654321", address.getIbge());
        assertEquals("GIA2", address.getGia());
        assertEquals("22", address.getDdd());
        assertEquals("SIAFI2", address.getSiafi());
    }

}