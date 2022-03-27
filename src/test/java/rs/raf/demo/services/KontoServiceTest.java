package rs.raf.demo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.raf.demo.controllers.KontoController;
import rs.raf.demo.model.Konto;
import rs.raf.demo.repositories.KontoRepository;
import rs.raf.demo.services.impl.KontoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class KontoServiceTest {

    @InjectMocks
    private KontoService kontoService;

    @Mock
    private KontoRepository kontoRepository;

    private List<Konto> kontos;

    @BeforeEach
    public void setup () {
        kontos = new ArrayList<>();
    }

    @Test
    void testFindAll() {
        Konto k1 = new Konto();
        Konto k2 = new Konto();
        Konto k3 = new Konto();
        Konto k4 = new Konto();
        Konto k5 = new Konto();

        k1.setBrojKonta("00");
        k2.setBrojKonta("000");
        k3.setBrojKonta("0001");
        k4.setBrojKonta("001");
        k5.setBrojKonta("0100");

        kontos = List.of(k5, k2, k3, k1, k4);
        when(kontoService.findAll()).thenReturn(kontos);
        List<Konto> rezultat = kontoService.findAll();

        List<Konto> ks = List.of(k1,k2,k3,k4,k5);
        assertEquals(ks, rezultat);
    }

    @Test
    void testGetKontneGrupe() {
        Konto k1 = new Konto();
        Konto k2 = new Konto();
        Konto k3 = new Konto();
        Konto k4 = new Konto();
        Konto k5 = new Konto();

        k1.setBrojKonta("00");
        k1.setNaziv("a");
        k2.setBrojKonta("000");
        k2.setNaziv("b");
        k3.setBrojKonta("0001");
        k3.setNaziv("c");
        k4.setBrojKonta("001");
        k4.setNaziv("d");
        k5.setBrojKonta("0100");
        k5.setNaziv("e");

        Map<String, String> sort = new HashMap<>();
        sort.put("-", "brojKonta");
        sort.put("+", "naziv");

        kontos = List.of(k5, k2, k3, k1, k4);
        when(kontoService.findAllSorted(sort)).thenReturn(kontos);
        List<Konto> rezultat = kontoService.findAllSorted(sort);

        List<Konto> ks = List.of(k1,k2,k3,k4,k5);
        assertEquals(ks, rezultat);
    }

}
