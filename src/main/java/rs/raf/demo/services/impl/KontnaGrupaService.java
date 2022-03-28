package rs.raf.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.KontnaGrupa;
import rs.raf.demo.repositories.KontnaGrupaRepository;
import rs.raf.demo.services.IKontnaGrupaService;

import java.util.*;

import static rs.raf.demo.utils.ApiUtil.*;

@Service
public class KontnaGrupaService implements IKontnaGrupaService {
    KontnaGrupaRepository kontnaGrupaRepository;

    @Autowired
    public KontnaGrupaService(KontnaGrupaRepository kontnaGrupaRepository) {
        this.kontnaGrupaRepository = kontnaGrupaRepository;
    }

    @Override
    public KontnaGrupa save(KontnaGrupa kontnaGrupa) {
        return kontnaGrupaRepository.save(kontnaGrupa);
    }

    @Override
    public Optional<KontnaGrupa> findById(String id) {
        return kontnaGrupaRepository.findById(id);
    }

    public KontnaGrupa findKontnaGrupaById(String id) {
        return findById(id).orElseThrow(NoSuchElementException::new);
    }

    public KontnaGrupa update(String id) {
        return save(findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<KontnaGrupa> findAll() {
        List<KontnaGrupa> kontos = new ArrayList<>(kontnaGrupaRepository.findAll());
        kontos.sort(Comparator.comparing(KontnaGrupa::getBrojKonta));
        return kontos;
    }
    @Override
    public List<KontnaGrupa> findAllSorted(String[] sort) {
        return kontnaGrupaRepository.findAll(resolveSortingAndPagination(MIN_PAGE, MAX_SIZE, sort)).getContent();
    }

    @Override
    public void deleteById(String id) {
        kontnaGrupaRepository.deleteById(id);
    }
}
