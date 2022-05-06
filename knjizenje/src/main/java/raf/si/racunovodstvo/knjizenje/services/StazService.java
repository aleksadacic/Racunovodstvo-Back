package rs.raf.demo.services.impl;

import org.springframework.stereotype.Service;
import rs.raf.demo.model.Staz;
import rs.raf.demo.repositories.StazRepository;
import rs.raf.demo.services.IStazService;

import java.util.List;
import java.util.Optional;

@Service
public class StazService implements IStazService {

    private final StazRepository stazRepository;

    public StazService(StazRepository stazRepository) {
        this.stazRepository = stazRepository;
    }

    @Override
    public <S extends Staz> S save(S staz) {
        return stazRepository.save(staz);
    }

    @Override
    public Optional<Staz> findById(Long id) {
        return stazRepository.findById(id);
    }

    @Override
    public List<Staz> findAll() {
        return stazRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        stazRepository.deleteById(id);
    }
}
