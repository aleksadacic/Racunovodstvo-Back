package rs.raf.demo.services;

import rs.raf.demo.model.Konto;

import java.util.List;
import java.util.Map;

public interface IKontoService extends IService<Konto, Long> {
    List<Konto> findAllSorted(Map<String, String> sort) throws Exception;
    Konto update(Long id) throws Exception;
}
