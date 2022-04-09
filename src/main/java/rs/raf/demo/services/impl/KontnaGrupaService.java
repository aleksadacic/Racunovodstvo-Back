package rs.raf.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.KontnaGrupa;
import rs.raf.demo.model.Konto;
import rs.raf.demo.reports.ReportsConstants;
import rs.raf.demo.reports.TableReport;
import rs.raf.demo.repositories.KontnaGrupaRepository;
import rs.raf.demo.services.IKontnaGrupaService;
import rs.raf.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * aleksadacic
     * Trenutno je bitan redosled kako pravimo listu stringova koji ce da se prikazuju u tabeli.
     * Nekada treba da se sredi ovo za pravljenje tabela da programer ne razmislja o tome.
     */
    public TableReport makeBrutoBilansTableReport(String user, String title, Pageable sort) {
        List<List<String>> rows = new ArrayList<>();

        List<Double> dugovanja = new ArrayList<>();
        List<Double> potrazivanja = new ArrayList<>();
        List<Double> salda = new ArrayList<>();
        List<Integer> stavke = new ArrayList<>();

        List<KontnaGrupa> kontneGrupe = findAll(sort).getContent();
        for (KontnaGrupa grupa : kontneGrupe) {
            Double duguje = grupa.getKonto().stream().map(Konto::getDuguje).reduce(0.0, Double::sum);
            Double potrazuje = grupa.getKonto().stream().map(Konto::getPotrazuje).reduce(0.0, Double::sum);
            List<String> row = List.of(
                    grupa.getBrojKonta(),
                    grupa.getKonto().size() + "",
                    grupa.getNazivKonta(),
                    duguje + "",
                    potrazuje + "",
                    (duguje - potrazuje) + ""
            );

            rows.add(row);
            dugovanja.add(duguje);
            potrazivanja.add(potrazuje);
            stavke.add(grupa.getKonto().size());
            salda.add(duguje - potrazuje);
        }

        String footer = "Ukupno stavki: " + stavke.stream().reduce(0, Integer::sum) + " | "
                + "Duguje ukupno: " + Utils.sum(dugovanja) + " | "
                + "Potrazuje ukupno: " + Utils.sum(potrazivanja) + " | "
                + "Saldo ukupno: " + Utils.sum(salda);

        return new TableReport(user, title, footer, ReportsConstants.BRUTO_BILANS_COLUMNS, rows);
    }
}
