package rs.raf.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.demo.model.KontnaGrupa;
import rs.raf.demo.reports.TableReport;

public interface IKontnaGrupaService extends IService<KontnaGrupa, Long> {
    Page<KontnaGrupa> findAll(Pageable sort);
    TableReport makeBrutoBilansTableReport(String user);
}
