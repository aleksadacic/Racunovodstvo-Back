package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.demo.model.Faktura;

import java.util.List;

public interface FakturaRepository extends JpaRepository<Faktura, Long> {
    List<Double> findProdajnaVrednostByTipFakture(boolean tipFakture);
    List<Double> findRabatByTipFakture(boolean tipFakture);
    List<Double> findPorezByTipFakture(boolean tipFakture);
    List<Double> findZaNaplatuByTipFakture(boolean tipFakture);
}
