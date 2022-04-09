package rs.raf.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.KontnaGrupa;
import rs.raf.demo.model.Konto;
import rs.raf.demo.model.Preduzece;
import rs.raf.demo.reports.ReportsConstants;
import rs.raf.demo.reports.TableReport;
import rs.raf.demo.reports.schemas.BrutoBilansSchema;
import rs.raf.demo.repositories.KontnaGrupaRepository;
import rs.raf.demo.services.IKontnaGrupaService;
import rs.raf.demo.utils.Constants;
import rs.raf.demo.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KontnaGrupaService implements IKontnaGrupaService {
    KontnaGrupaRepository kontnaGrupaRepository;

    @Autowired PreduzeceService preduzeceService;

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
    public List<KontnaGrupa> findAll() {
        return kontnaGrupaRepository.findAll();
    }

    @Override
    public Page<KontnaGrupa> findAll(Pageable sort) {
        return kontnaGrupaRepository.findAll(sort);
    }

    @Override
    public void deleteById(Long id) {
        kontnaGrupaRepository.deleteById(id);
    }

    public TableReport makeBilansTableReport(String user, String title,
                                             List<Date> datum1, List<Date> datum2, List<Date> datum3,
                                             String kontoOd, String kontoDo, Constants.BILANS vrsta) {
        List<BrutoBilansSchema> rows = new ArrayList<>();

        List<Double> dugovanja = new ArrayList<>();
        List<Double> potrazivanja = new ArrayList<>();
        List<Double> salda = new ArrayList<>();
        List<Integer> stavke = new ArrayList<>();

        List<KontnaGrupa> kontneGrupe = new ArrayList<>();

        switch (vrsta) {
            case BRUTO_BILANS:
                kontneGrupe = filterKontneGrupe(findAll(), datum1.get(0), datum1.get(1), kontoOd, kontoDo, kontneGrupe);
                break;
            case BILANS_STANJA:
                List<KontnaGrupa> stanja = findAll().stream()
                        .filter(e -> Constants.BILANS.BILANS_STANJA_VALUES.contains(e.getBrojKonta().substring(0, 1)))
                        .collect(Collectors.toList());
                kontneGrupe = filterKontneGrupe(stanja, datum1.get(0), datum1.get(1), kontoOd, kontoDo, kontneGrupe);
                kontneGrupe.addAll(filterKontneGrupe(stanja, datum2.get(0), datum2.get(1), kontoOd, kontoDo, kontneGrupe));
                kontneGrupe.addAll(filterKontneGrupe(stanja, datum3.get(0), datum3.get(1), kontoOd, kontoDo, kontneGrupe));
                break;
            case BILANS_USPEHA:
                List<KontnaGrupa> uspeh = findAll().stream()
                        .filter(e -> Constants.BILANS.BILANS_USPEHA_VALUES.contains(e.getBrojKonta().substring(0, 1)))
                        .collect(Collectors.toList());
                kontneGrupe = filterKontneGrupe(uspeh, datum1.get(0), datum1.get(1), kontoOd, kontoDo, kontneGrupe);
                kontneGrupe.addAll(filterKontneGrupe(uspeh, datum2.get(0), datum2.get(1), kontoOd, kontoDo, kontneGrupe));
                kontneGrupe.addAll(filterKontneGrupe(uspeh, datum3.get(0), datum3.get(1), kontoOd, kontoDo, kontneGrupe));
                break;
        }

        for (KontnaGrupa grupa : kontneGrupe) {
            Double duguje = grupa.getKonto().stream().map(Konto::getDuguje).reduce(0.0, Double::sum);
            Double potrazuje = grupa.getKonto().stream().map(Konto::getPotrazuje).reduce(0.0, Double::sum);

            BrutoBilansSchema bbs = new BrutoBilansSchema(
                    grupa.getBrojKonta(),
                    grupa.getKonto().size() + "",
                    grupa.getNazivKonta(),
                    duguje + "",
                    potrazuje + "",
                    (duguje - potrazuje) + ""
            );

            rows.add(bbs);
            dugovanja.add(duguje);
            potrazivanja.add(potrazuje);
            stavke.add(grupa.getKonto().size());
            salda.add(duguje - potrazuje);
        }

        Collections.sort(rows);
        List<List<String>> rowValues = new ArrayList<>();
        rows.forEach(r -> rowValues.add(r.getList()));

        String sums = "Ukupno stavki: " + stavke.stream().reduce(0, Integer::sum) + " | "
                + "Duguje ukupno: " + Utils.sum(dugovanja) + " | "
                + "Potrazuje ukupno: " + Utils.sum(potrazivanja) + " | "
                + "Saldo ukupno: " + Utils.sum(salda);

        String preduzece = "";
        if (vrsta.equals(Constants.BILANS.BILANS_STANJA) || vrsta.equals(Constants.BILANS.BILANS_USPEHA)) {
            Preduzece p = preduzeceService.findAll().get(0);
            preduzece = p.getNaziv() + "\n" + p.getAdresa() + ", " + p.getGrad() + "\n";
        }

        String footer = sums + "\n\n\n" + preduzece;

        return new TableReport(user, title, footer, ReportsConstants.BILANS_COLUMNS, rowValues);
    }

    private List<KontnaGrupa> filterKontneGrupe(List<KontnaGrupa> original, Date datumOd, Date datumDo, String kontoOd, String kontoDo, List<KontnaGrupa> target) {

        List<KontnaGrupa> kontneGrupeFiltered = new ArrayList<>();
        for (KontnaGrupa kontnaGrupa : original) {
            List<Konto> kontos = new ArrayList<>();
            if (datumOd != null && datumDo != null) {
                kontos = kontnaGrupa.getKonto().stream().filter(k ->
                        k.getKnjizenje().getDatumKnjizenja().before(datumDo) &&
                                k.getKnjizenje().getDatumKnjizenja().after(datumOd))
                        .collect(Collectors.toList());
            }

            if (!(kontnaGrupa.getBrojKonta().compareTo(kontoOd) >= 0 &&
                    kontnaGrupa.getBrojKonta().compareTo(kontoDo) <= 0) || kontos.isEmpty()) continue;

            KontnaGrupa kg = new KontnaGrupa();
            kg.setBrojKonta(kontnaGrupa.getBrojKonta());
            kg.setNazivKonta(kontnaGrupa.getNazivKonta());
            kg.setKonto(kontos);
            if (target.stream().noneMatch(e -> e.getBrojKonta().equals(kg.getBrojKonta())))
                kontneGrupeFiltered.add(kg);
        }
        return kontneGrupeFiltered;
    }
}
