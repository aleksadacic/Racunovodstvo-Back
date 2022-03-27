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
        Optional<Konto> optionalKonto = kontoRepository.findById(id);
        optionalKonto.orElseThrow();
        return optionalKonto;
    }

    @Override
    public Konto update(Long id) {
        return save(findById(id).orElseThrow());
    }

    @Override
    public List<Konto> findAll() {
        return kontoRepository.findAll(Sort.by("brojKonta").ascending());
    }

    @Override
    public List<Konto> findAllSorted(Map<String, String> sort) throws Exception {
        List<Sort.Order> orders = new ArrayList<>();
        for (Map.Entry<String, String> criteria : sort.entrySet()) {
            if (criteria.getKey().equals("-")) {
                orders.add(new Sort.Order(Sort.Direction.DESC, criteria.getValue()));
            }
            else if (criteria.getKey().equals("+")) {
                orders.add(new Sort.Order(Sort.Direction.ASC, criteria.getValue()));
            }
            else throw new Exception("Undefined sort parameter!");
        }
        return kontoRepository.findAll(Sort.by(orders));
    }

    @Override
    public void deleteById(Long id) {
        kontoRepository.deleteById(id);
    }
}
