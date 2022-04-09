package rs.raf.demo.controllers;

import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.services.IKontnaGrupaService;
import rs.raf.demo.services.impl.KontnaGrupaService;
import rs.raf.demo.utils.ApiUtil;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;

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
    public ResponseEntity<?> getBrutoBilans(@RequestParam(defaultValue = ApiUtil.DEFAULT_PAGE) @Min(ApiUtil.MIN_PAGE) Integer page,
                                         @RequestParam(defaultValue = ApiUtil.DEFAULT_SIZE) @Min(ApiUtil.MIN_SIZE) @Max(ApiUtil.MAX_SIZE) Integer size,
                                         @RequestParam(defaultValue = "") String[] sort,
                                         @RequestParam(defaultValue = "") String title,
                                         Principal principal) throws DocumentException {

        Pageable pageSort = ApiUtil.resolveSortingAndPagination(page, size, sort);
        byte[] pdf = kontnaGrupaService.makeBrutoBilansTableReport(principal.getName(), title, pageSort).getReport();
        return ResponseEntity.ok(pdf);
    }

    //0-4
    @GetMapping(path = "/stanje", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getBilansStanja(@RequestParam(defaultValue = ApiUtil.DEFAULT_PAGE) @Min(ApiUtil.MIN_PAGE) Integer page,
                                         @RequestParam(defaultValue = ApiUtil.DEFAULT_SIZE) @Min(ApiUtil.MIN_SIZE) @Max(ApiUtil.MAX_SIZE) Integer size,
                                         @RequestParam(defaultValue = "") String[] sort,
                                         @RequestParam(defaultValue = "") String title,
                                         Principal principal) throws DocumentException {

        Pageable pageSort = ApiUtil.resolveSortingAndPagination(page, size, sort);
        byte[] pdf = kontnaGrupaService.makeBrutoBilansTableReport(principal.getName(), title, pageSort).getReport();
        return ResponseEntity.ok(pdf);
    }

    //5,6
    @GetMapping(path = "/uspeh", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getBilansUspeha(@RequestParam(defaultValue = ApiUtil.DEFAULT_PAGE) @Min(ApiUtil.MIN_PAGE) Integer page,
                                         @RequestParam(defaultValue = ApiUtil.DEFAULT_SIZE) @Min(ApiUtil.MIN_SIZE) @Max(ApiUtil.MAX_SIZE) Integer size,
                                         @RequestParam(defaultValue = "") String[] sort,
                                         @RequestParam(defaultValue = "") String title,
                                         Principal principal) throws DocumentException {

        Pageable pageSort = ApiUtil.resolveSortingAndPagination(page, size, sort);
        byte[] pdf = kontnaGrupaService.makeBrutoBilansTableReport(principal.getName(), title, pageSort).getReport();
        return ResponseEntity.ok(pdf);
    }
}
