package rs.raf.demo.services;

import rs.raf.demo.model.KontnaGrupa;

import java.util.List;

public interface IKontnaGrupaService extends IService<KontnaGrupa, String> {
    List<KontnaGrupa> findAllSorted(String[] sort);
    KontnaGrupa update(String id);
    KontnaGrupa findKontnaGrupaById(String id);
}
