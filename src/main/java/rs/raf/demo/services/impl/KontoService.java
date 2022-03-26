package rs.raf.demo.services.impl;

import org.springframework.stereotype.Service;
import rs.raf.demo.model.Konto;
import rs.raf.demo.services.IService;

import java.util.List;
import java.util.Optional;

@Service
public class KontoService implements IService<Konto, Long> {

    @Override
    public <S extends Konto> S save(S var1) {
        return null;
    }

    @Override
    public Optional<Konto> findById(Long var1) {
        return Optional.empty();
    }

    @Override
    public List<Konto> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long var1) {

    }
}
