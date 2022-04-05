package rs.raf.demo.services.impl;

import org.springframework.stereotype.Service;
import rs.raf.demo.model.Koeficijent;
import rs.raf.demo.repositories.KoeficijentRepository;
import rs.raf.demo.services.IService;

import java.util.List;
import java.util.Optional;

@Service
public class KoeficijentService implements IService<Koeficijent, Long> {
    private KoeficijentRepository koeficijentRepository;

    public KoeficijentService(KoeficijentRepository koeficijentRepository) {
        this.koeficijentRepository = koeficijentRepository;
    }

    @Override
    public <S extends Koeficijent> S save(S var1) {
        return koeficijentRepository.save(var1);
    }

    @Override
    public Optional<Koeficijent> findById(Long var1) {
        return koeficijentRepository.findById(var1);
    }

    @Override
    public List<Koeficijent> findAll() {
        return koeficijentRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        koeficijentRepository.deleteById(var1);
    }
}
