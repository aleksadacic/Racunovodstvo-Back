package rs.raf.demo.controllers;

import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.services.IKontnaGrupaService;
import rs.raf.demo.services.impl.KontnaGrupaService;
import rs.raf.demo.utils.Constants;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/izvestaji")
public class IzvestajiController {
    private IKontnaGrupaService kontnaGrupaService;

    public IzvestajiController(KontnaGrupaService kontnaGrupaService) {
        this.kontnaGrupaService = kontnaGrupaService;
    }

    @GetMapping(path = "/bruto", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getBrutoBilans(@RequestParam String title,
                                            @RequestParam(required = false) Date datumOd,
                                            @RequestParam(required = false) Date datumDo,
                                            @RequestParam String kontoOd,
                                            @RequestParam String kontoDo,
                                            Principal principal) throws DocumentException {

        byte[] pdf = kontnaGrupaService.makeBilansTableReport(
                principal.getName(), title,
                List.of(datumOd, datumDo), Collections.emptyList(), Collections.emptyList(),
                kontoOd, kontoDo, Constants.BILANS.BRUTO_BILANS
        ).getReport();
        return ResponseEntity.ok(pdf);
    }

    @GetMapping(path = "/stanje", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getBilansStanja(@RequestParam String title,
                                             @RequestParam(required = false) Date datumOd1,
                                             @RequestParam(required = false) Date datumDo1,
                                             @RequestParam(required = false) Date datumOd2,
                                             @RequestParam(required = false) Date datumDo2,
                                             @RequestParam(required = false) Date datumOd3,
                                             @RequestParam(required = false) Date datumDo3,
                                             @RequestParam String kontoOd,
                                             @RequestParam String kontoDo,
                                             Principal principal) throws DocumentException {

        byte[] pdf = kontnaGrupaService.makeBilansTableReport(
                principal.getName(), title,
                List.of(datumOd1, datumDo1), List.of(datumOd2, datumDo2), List.of(datumOd3, datumDo3),
                kontoOd, kontoDo, Constants.BILANS.BILANS_STANJA
        ).getReport();
        return ResponseEntity.ok(pdf);
    }

    @GetMapping(path = "/uspeh", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getBilansUspeha(@RequestParam String title,
                                             @RequestParam(required = false, defaultValue = "01/01/1970") Date datumOd1,
                                             @RequestParam(required = false, defaultValue = "01/01/2070") Date datumDo1,
                                             @RequestParam(required = false, defaultValue = "01/01/1970") Date datumOd2,
                                             @RequestParam(required = false, defaultValue = "01/01/2070") Date datumDo2,
                                             @RequestParam(required = false, defaultValue = "01/01/1970") Date datumOd3,
                                             @RequestParam(required = false, defaultValue = "01/01/2070") Date datumDo3,
                                             @RequestParam String kontoOd,
                                             @RequestParam String kontoDo,
                                             Principal principal) throws DocumentException {

        List<Date> date1 = new ArrayList<>();
        List<Date> date2 = new ArrayList<>();
        List<Date> date3 = new ArrayList<>();

        if (datumOd1 != null && datumDo1 != null) date1 = List.of(datumOd1, datumDo1);
        if (datumOd2 != null && datumDo2 != null) date2 = List.of(datumOd2, datumDo2);
        if (datumOd3 != null && datumDo3 != null) date3 = List.of(datumOd3, datumDo3);

        byte[] pdf = kontnaGrupaService.makeBilansTableReport(
                principal.getName(), title,
                date1, date2, date3,
                kontoOd, kontoDo, Constants.BILANS.BILANS_USPEHA
        ).getReport();
        return ResponseEntity.ok(pdf);
    }
}
