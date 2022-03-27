package rs.raf.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.Konto;
import rs.raf.demo.repositories.KontoRepository;
import rs.raf.demo.services.IKontoService;

import java.util.*;

@Service
public class KontoService implements IKontoService {
    private final KontoRepository kontoRepository;

    @Autowired
    public KontoService(KontoRepository kontoRepository) {
        this.kontoRepository = kontoRepository;
    }

    @Override
    public Konto save(Konto konto) {
        return kontoRepository.save(konto);
    }

    @Override
    public Optional<Konto> findById(Long id) {
        return kontoRepository.findById(id);
    }

    @Override
    public Konto findKontoById(Long id) {
        return findById(id).orElseThrow();
    }

    @Override
    public Konto update(Long id) {
        return save(findById(id).orElseThrow());
    }

    @Override
    public List<Konto> findAll() {
        List<Konto> kontos = new ArrayList<>(kontoRepository.findAll());
        kontos.sort(Comparator.comparing(Konto::getBrojKonta));
        return kontos;
    }

    @Override
    public List<Konto> findAllSorted(Map<String, String> sort) {
        List<Comparator<Konto>> comparators = new ArrayList<>();
        for (Map.Entry<String, String> criteria : sort.entrySet()) {
            comparators.add((o1, o2) -> compareAnyInKonto(o1, o2, criteria.getValue(), criteria.getKey()));
        }
        Collections.reverse(comparators);
        List<Konto> all = new ArrayList<>(kontoRepository.findAll());
        for (Comparator<Konto> comp : comparators) {
            all.sort(comp);
        }
        return all;
    }

    @Override
    public void deleteById(Long id) {
        kontoRepository.deleteById(id);
    }

    private int compareAnyInKonto(Konto k1, Konto k2, String field, String order) {
        int orderValue = order.equals("+")? 1 : order.equals("-")? -1 : 1;
        if (field.equals("naziv")) {
            return orderValue * k1.getNaziv().compareTo(k2.getNaziv());
        }
        if (field.equals("brojKonta")) {
            return orderValue * k1.getBrojKonta().compareTo(k2.getBrojKonta());
        }
        if (field.equals("kontoId")) {
            return orderValue * k1.getKontoId().compareTo(k2.getKontoId());
        }
        throw new IllegalArgumentException();
    }
}
