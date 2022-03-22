package rs.raf.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.Faktura;
import rs.raf.demo.model.TipFakture;
import rs.raf.demo.repositories.FakturaRepository;
import rs.raf.demo.services.IFakturaService;
import rs.raf.demo.utils.Utils;

import java.util.*;

@Service
public class FakturaService implements IFakturaService {

    private final FakturaRepository fakturaRepository;

    @Autowired
    public FakturaService(FakturaRepository fakturaRepository) {
        this.fakturaRepository = fakturaRepository;
    }

    public List<Faktura> findAll(){
        return fakturaRepository.findAll();
    }

    public List<Faktura> findUlazneFakture(){
        List<Faktura> ulazneFakture = new ArrayList<>();
        for(Faktura f : fakturaRepository.findAll()){
            if(f.getTipFakture().equals(TipFakture.ULAZNA_FAKTURA)){
                ulazneFakture.add(f);
            }
        }
        return ulazneFakture;
    }

    public List<Faktura> findIzlazneFakture(){
        List<Faktura> izlacneFakture = new ArrayList<>();
        for(Faktura f : fakturaRepository.findAll()){
            if(f.getTipFakture().equals(TipFakture.IZLAZNA_FAKTURA)){
                izlacneFakture.add(f);
            }
        }
        return izlacneFakture;
    }

    @Override
    public Double calculateSumPorez(TipFakture tipFakture) {
        List<Double> fakture = fakturaRepository.findPorezForTipFakture(tipFakture);
        return Utils.sum(fakture);
    }

    @Override
    public Double calculateSumProdajnaVrednost(TipFakture tipFakture) {
        List<Double> fakture = fakturaRepository.findProdajnaVrednostForTipFakture(tipFakture);
        return Utils.sum(fakture);
    }

    @Override
    public Double calculateSumRabat(TipFakture tipFakture) {
        List<Double> fakture = fakturaRepository.findRabatForTipFakture(tipFakture);
        return Utils.sum(fakture);
    }

    @Override
    public Double calculateSumNaplata(TipFakture tipFakture) {
        List<Double> fakture = fakturaRepository.findNaplataForTipFakture(tipFakture);
        return Utils.sum(fakture);
    }

    public Optional<Faktura> findById(Long id){
        return fakturaRepository.findByFakturaId(id);
    }

    public Faktura save(Faktura faktura){
        return fakturaRepository.save(faktura);
    }

    @Override
    public void deleteById(Long id) {
        fakturaRepository.deleteById(id);
    }
}
