package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.demo.model.Faktura;
import rs.raf.demo.model.TipFakture;

import java.util.List;

public interface FakturaRepository extends JpaRepository<Faktura, Long> {
    List<Double> findProdajnaVrednostByTipFakture(TipFakture tipFakture);
    List<Double> findRabatByTipFakture(TipFakture tipFakture);
    List<Double> findPorezByTipFakture(TipFakture tipFakture);
    List<Double> findZaNaplatuByTipFakture(TipFakture tipFakture);
}
