package rs.raf.demo.services.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.Staz;
import rs.raf.demo.model.Zaposleni;
import rs.raf.demo.model.enums.StatusZaposlenog;
import rs.raf.demo.repositories.ZaposleniRepository;
import rs.raf.demo.services.IZaposleniService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ZaposleniService implements IZaposleniService{


        private final ZaposleniRepository zaposleniRepository;
        private final StazService stazService;


        public ZaposleniService(ZaposleniRepository zaposleniRepository, StazService stazService) {
            this.zaposleniRepository = zaposleniRepository;
            this.stazService = stazService;
        }

        @Override
        public <S extends Zaposleni> S save(S zaposleni) {

            Staz newStaz = new Staz();
            newStaz.setPocetakRada(new Date());
            newStaz.setKrajRada(null);
            newStaz.setZaposleni(zaposleni);
            stazService.save(newStaz);

            zaposleni.getStaz().add(newStaz);
            zaposleni.setStatusZaposlenog(StatusZaposlenog.ZAPOSLEN);

            return zaposleniRepository.save(zaposleni);
        }

        @Override
        public Optional<Zaposleni> findById(Long id) {
            return zaposleniRepository.findById(id);
        }

        @Override
        public List<Zaposleni> findAll() {
            return zaposleniRepository.findAll();
        }

        @Override
        public void deleteById(Long id) {
            zaposleniRepository.deleteById(id);
        }

        @Override
        public List<Zaposleni> findAll(Specification<Zaposleni> spec) {
            return zaposleniRepository.findAll(spec);
        }

        @Override
        public Zaposleni otkazZaposleni(Zaposleni zaposleni) {
            Zaposleni newZaposleni = new Zaposleni();
            if (zaposleni != null) {
                newZaposleni = zaposleni;
                newZaposleni.setStatusZaposlenog(StatusZaposlenog.NEZAPOSLEN);
                for(Staz staz : zaposleni.getStaz()){
                    if(staz.getKrajRada() == null){
                        staz.setKrajRada(new Date());
                        stazService.save(staz);
                    }
                }
            }
            return zaposleniRepository.save(newZaposleni);
        }

}