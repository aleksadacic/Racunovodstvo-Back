package raf.si.racunovodstvo.preduzece.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.si.racunovodstvo.preduzece.jobs.ObracunZaradeJob;
import raf.si.racunovodstvo.preduzece.services.impl.ObracunZaradeService;

import java.util.Date;
import java.util.Map;

@CrossOrigin
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/obracun_zarade")
public class ObracunZaradeRestController {
    private final ObracunZaradeService obracunZaradeService;
    private final ObracunZaradeJob obracunZaradeJob;

    public ObracunZaradeRestController(ObracunZaradeService obracunZaradeService, ObracunZaradeJob obracunZaradeJob) {
        this.obracunZaradeJob = obracunZaradeJob;
        this.obracunZaradeService = obracunZaradeService;
    }

    @PostMapping(value = "/{dan}")
    public ResponseEntity<?> changeJobDayOfMonth(@PathVariable Integer dan) {
        obracunZaradeJob.setDayOfMonth(dan);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<?> setObracunZaradeNaziv(@RequestBody Map<String, String> request) {
        obracunZaradeService.updateObracunZaradeNaziv(Long.parseLong(request.get("obracunZaradeId")), request.get("naziv"));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{datum}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getObracunZarade(@PathVariable Date datum) {
        return ResponseEntity.ok(obracunZaradeService.findByDate(datum));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getObracunZarade() {
        return ResponseEntity.ok(obracunZaradeService.findAll());
    }
}

