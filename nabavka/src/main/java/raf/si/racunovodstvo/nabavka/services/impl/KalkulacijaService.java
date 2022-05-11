package raf.si.racunovodstvo.nabavka.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import raf.si.racunovodstvo.nabavka.model.Artikal;
import raf.si.racunovodstvo.nabavka.model.Kalkulacija;
import raf.si.racunovodstvo.nabavka.model.KalkulacijaArtikal;
import raf.si.racunovodstvo.nabavka.repositories.KalkulacijaRepository;
import raf.si.racunovodstvo.nabavka.services.IKalkulacijaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class KalkulacijaService implements IKalkulacijaService {

    private KalkulacijaRepository kalkulacijaRepository;

    public KalkulacijaService(KalkulacijaRepository kalkulacijaRepository) {
        this.kalkulacijaRepository = kalkulacijaRepository;
    }

    @Override
    public Object save(Object var1) {
        return null;
    }

    @Override
    public Optional findById(Object var1) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void deleteById(Object var1) {

    }

    @Override
    public Page<Kalkulacija> findAll(Specification<Kalkulacija> spec, Pageable pageSort) {
        return kalkulacijaRepository.findAll(spec, pageSort);
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
