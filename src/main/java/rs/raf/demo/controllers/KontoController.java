package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Konto;
import rs.raf.demo.services.IKontoService;
import rs.raf.demo.services.impl.KontoService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/konto")
public class KontoController {

    private final IKontoService kontoService;

    public KontoController(KontoService kontoService) {
        this.kontoService = kontoService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getKontnaGrupa(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok(kontoService.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getKontneGrupe(@RequestParam("sort") String sort) {
        try {
            if (sort == null || sort.isEmpty())
                return ResponseEntity.ok(kontoService.findAll());
            else {
                // #19 @aleksadacic - priprema i parsiranje queryja za koriscenje u servisu.
                String[] params = sort.split(",");
                Map<String, String> map = new HashMap<>();
                for (String item : params) {
                    String sign = item.substring(0,1);
                    String field = item.substring(1);
                    map.put(sign, field);
                }
                return ResponseEntity.ok(kontoService.findAllSorted(map));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createKontnaGrupa(@Valid @RequestBody Konto konto) {
        try {
            return ResponseEntity.ok(kontoService.save(konto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateKontnaGrupa(@Valid @RequestBody Konto konto) {
        try {
            return ResponseEntity.ok(kontoService.update(konto.getKontoId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteKontnaGrupa(@PathVariable Long id) {
        try {
            kontoService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}
