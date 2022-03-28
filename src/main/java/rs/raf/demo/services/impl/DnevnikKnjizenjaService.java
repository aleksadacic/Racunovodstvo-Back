package rs.raf.demo.services.impl;

import org.springframework.stereotype.Service;
import rs.raf.demo.services.IService;

import java.util.List;
import java.util.Optional;

@Service
public class DnevnikKnjizenjaService implements IService<DnevnikKnjizenjaService, Long> {

    @Override
    public <S extends DnevnikKnjizenjaService> S save(S var1) {
        return null;
    }

    @Override
    public Optional<DnevnikKnjizenjaService> findById(Long var1) {
        return Optional.empty();
    }

    @Override
    public List<DnevnikKnjizenjaService> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long var1) {

    }
}
