package raf.si.racunovodstvo.nabavka.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import raf.si.racunovodstvo.nabavka.model.Artikal;
import raf.si.racunovodstvo.nabavka.model.Kalkulacija;
import raf.si.racunovodstvo.nabavka.model.TroskoviNabavke;
import raf.si.racunovodstvo.nabavka.model.KalkulacijaArtikal;
import raf.si.racunovodstvo.nabavka.repositories.KalkulacijaRepository;
import raf.si.racunovodstvo.nabavka.services.IKalkulacijaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

@Service
public class KalkulacijaService implements IKalkulacijaService {

    private final KalkulacijaRepository kalkulacijaRepository;

    public KalkulacijaService(KalkulacijaRepository kalkulacijaRepository) {
        this.kalkulacijaRepository = kalkulacijaRepository;
    }

    @Override
    public Page<Kalkulacija> findAll(Specification<Kalkulacija> spec, Pageable pageSort) {
        return kalkulacijaRepository.findAll(spec, pageSort);
    }

    @Override
    public Kalkulacija increaseNabavnaAndProdajnaCena(Long kalkulacijaId, Double nabavnaCena, Double prodajnaCena) {
        Optional<Kalkulacija> optionalKalkulacija = findById(kalkulacijaId);
        if (optionalKalkulacija.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Kalkulacija kalkulacija = optionalKalkulacija.get();

        kalkulacija.setProdajnaCena(kalkulacija.getProdajnaCena() + prodajnaCena);
        Double ukupnaFakturnaCena = kalkulacija.getFakturnaCena() + nabavnaCena;
        kalkulacija.setFakturnaCena(ukupnaFakturnaCena);
        Double ukupniTroskoviNabavke = kalkulacija.getTroskoviNabavke().stream().mapToDouble(TroskoviNabavke::getCena).sum();
        kalkulacija.setNabavnaCena(ukupniTroskoviNabavke + ukupnaFakturnaCena);
        return kalkulacijaRepository.save(kalkulacija);
    }

    @Override
    public <S extends Kalkulacija> S save(S var1) {
        return kalkulacijaRepository.save(var1);
    }

    @Override
    public Optional<Kalkulacija> findById(Long var1) {
        return kalkulacijaRepository.findById(var1);
    }

    @Override
    public List<Kalkulacija> findAll() {
        return kalkulacijaRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        kalkulacijaRepository.deleteById(var1);
    }

    @Override
    public Map<String, Number> getTotalKalkulacije(List<Kalkulacija> kalkulacije) {
        Map<String, Number> values = new HashMap<>();
        Integer totalKolicina = 0;
        Double totalRabat = 0.0;
        Double totalNabavnaCena = 0.0;
        Double totalNabavnaCenaPosleRabata = 0.0;
        Double totalNabavnaVrednost = 0.0;
        Double totalMarza = 0.0;
        Double totalOsnovicaZaProdaju = 0.0;
        Double totalPorez = 0.0;
        Double totalProdajnaCena = 0.0;
        Double totalPoreskaOsnovica = 0.0;
        Double totalProdajnaVrednost = 0.0;
        for (Kalkulacija kalkulacija : kalkulacije) {
            Stream<KalkulacijaArtikal>  artikliStream= kalkulacija.getArtikli().stream();

            totalKolicina += artikliStream.map(Artikal::getKolicina).reduce(0, Integer::sum);
            totalRabat += artikliStream.map(Artikal::getRabat).reduce(0.0, Double::sum);
            totalNabavnaCena += artikliStream.map(Artikal::getNabavnaCena).reduce(0.0, Double::sum);
            totalNabavnaCenaPosleRabata += artikliStream.map(Artikal::getNabavnaCenaPosleRabata).reduce(0.0, Double::sum);
            totalNabavnaVrednost += artikliStream.map(Artikal::getUkupnaNabavnaVrednost).reduce(0.0, Double::sum);
            totalMarza += artikliStream.map(KalkulacijaArtikal::getMarza).reduce(0.0, Double::sum);
            totalOsnovicaZaProdaju += artikliStream.map(KalkulacijaArtikal::getProdajnaOsnovica).reduce(0.0, Double::sum);
            totalPorez += artikliStream.map(KalkulacijaArtikal::getPorez).reduce(0.0, Double::sum);
            totalProdajnaCena += artikliStream.map(KalkulacijaArtikal::getProdajnaCena).reduce(0.0, Double::sum);
            totalPoreskaOsnovica += artikliStream.map(KalkulacijaArtikal::getOsnovica).reduce(0.0, Double::sum);
            totalProdajnaVrednost += artikliStream.map(KalkulacijaArtikal::getUkupnaProdajnaVrednost).reduce(0.0, Double::sum);
        }
        values.put("totalKolicina", totalKolicina);
        values.put("totalRabat", totalRabat);
        values.put("totalNabavnaCena", totalNabavnaCena);
        values.put("totalNabavnaCenaPosleRabata", totalNabavnaCenaPosleRabata);
        values.put("totalNabavnaVrednost", totalNabavnaVrednost);
        values.put("totalMarza", totalMarza);
        values.put("totalOsnovicaZaProdaju", totalOsnovicaZaProdaju);
        values.put("totalPorez", totalPorez);
        values.put("totalProdajnaCena", totalProdajnaCena);
        values.put("totalPoreskaOsnovica", totalPoreskaOsnovica);
        values.put("totalProdajnaVrednost", totalProdajnaVrednost);
        return values;
    }
}
