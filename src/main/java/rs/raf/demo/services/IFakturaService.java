package rs.raf.demo.services;

import rs.raf.demo.model.Faktura;
import rs.raf.demo.model.TipFakture;

import java.util.List;

public interface IFakturaService extends IService<Faktura, Long>{

    public List<Faktura> findUlazneFakture();

    public List<Faktura> findIzlazneFakture();

    Double calculateSumPorez(TipFakture tipFakture);
    Double calculateSumProdajnaVrednost(TipFakture tipFakture);
    Double calculateSumRabat(TipFakture tipFakture);
    Double calculateSumNaplata(TipFakture tipFakture);
}
