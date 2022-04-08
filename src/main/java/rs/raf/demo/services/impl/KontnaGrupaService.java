package rs.raf.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.KontnaGrupa;
import rs.raf.demo.reports.TableReport;
import rs.raf.demo.repositories.KontnaGrupaRepository;
import rs.raf.demo.services.IKontnaGrupaService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Optional<KontnaGrupa> findById(Long id) {
        return kontnaGrupaRepository.findById(id);
    }

    @Override
    public List<KontnaGrupa> findAll() {return kontnaGrupaRepository.findAll(); }

    @Override
    public Page<KontnaGrupa> findAll(Pageable sort) {
        return kontnaGrupaRepository.findAll(sort);
    }

    @Override
    public void deleteById(Long id) {
        kontnaGrupaRepository.deleteById(id);
    }

    public TableReport makeBrutoBilansTableReport(String user) {
        // TODO ovo bi trebalo da se izvuce od nekud verovatno
        List<String> columns = List.of("Konto", "Stavki", "Naziv", "Duguje", "Potrazuje", "Saldo");
        List<List<String>> rows = new ArrayList<>();

        String footer = "Duguje ukupno: " + "\n" + "Potrazuje ukupno: ";

        return new TableReport(user, "Bruto bilans " + user, footer, columns, rows);
    }
}
