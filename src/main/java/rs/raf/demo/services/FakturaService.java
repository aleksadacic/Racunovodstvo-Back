package rs.raf.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.TipFakture;
import rs.raf.demo.repositories.FakturaRepository;
import rs.raf.demo.utils.Utils;

import java.util.List;

@Service
public class FakturaService {
    private final FakturaRepository fakturaRepository;

    public FakturaService(@Autowired FakturaRepository fakturaRepository) {
        this.fakturaRepository = fakturaRepository;
    }

    public Double calculateSumProdajnaVrednost(TipFakture tipFakture) {
        List<Double> fakture = fakturaRepository.findProdajnaVrednostByTipFakture(tipFakture);
        return Utils.sum(fakture);
    }

    public Double calculateSumRabat(TipFakture tipFakture) {
        List<Double> fakture = fakturaRepository.findRabatByTipFakture(tipFakture);
        return Utils.sum(fakture);
    }

    public Double calculateSumPorez(TipFakture tipFakture) {
        List<Double> fakture = fakturaRepository.findPorezByTipFakture(tipFakture);
        return Utils.sum(fakture);
    }

    public Double calculateSumZaNaplatu(TipFakture tipFakture) {
        List<Double> fakture = fakturaRepository.findZaNaplatuByTipFakture(tipFakture);
        return Utils.sum(fakture);
    }
}
