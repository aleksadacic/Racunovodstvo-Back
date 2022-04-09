package rs.raf.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.demo.model.KontnaGrupa;
import rs.raf.demo.reports.TableReport;
import rs.raf.demo.utils.Constants;

import java.util.Date;
import java.util.List;

public interface IKontnaGrupaService extends IService<KontnaGrupa, Long> {
    Page<KontnaGrupa> findAll(Pageable sort);
    TableReport makeBilansTableReport(String user, String title,
                                      List<Date> datum1, List<Date> datum2, List<Date> datum3,
                                      String kontoOd, String kontoDo, Constants.BILANS vrsta);

}
