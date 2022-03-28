package rs.raf.demo.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.KontnaGrupa;
import rs.raf.demo.services.IKontnaGrupaService;
import rs.raf.demo.services.impl.KontnaGrupaService;
import rs.raf.demo.utils.Utils;

import javax.validation.Valid;
import java.util.ArrayList;

@CrossOrigin
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/konto")
public class KontnaGrupaController {

    private final IKontnaGrupaService kontnaGrupaService;

    public KontnaGrupaController(KontnaGrupaService kontnaGrupaService) {
        this.kontnaGrupaService = kontnaGrupaService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getKontnaGrupa(@PathVariable("id") String id){
        try {
            return ResponseEntity.ok(kontnaGrupaService.findKontnaGrupaById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getKontneGrupe(@RequestParam("sort") String sort) {
        try {
            if (Utils.Strings.isEmpty(sort))
                return ResponseEntity.ok(kontnaGrupaService.findAll());
            else {
                String[] params = sort.split(",");
                return ResponseEntity.ok(kontnaGrupaService.findAllSorted(params));
            }
        } catch (Exception e) {
            // #19 aleksadacic - vracamo praznu listu kako kaze dokumentacija ruta.
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createKontnaGrupa(@Valid @RequestBody KontnaGrupa kontnaGrupa) {
        try {
            return ResponseEntity.ok(kontnaGrupaService.save(kontnaGrupa));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateKontnaGrupa(@Valid @RequestBody KontnaGrupa kontnaGrupa) {
        try {
            return ResponseEntity.ok(kontnaGrupaService.update(kontnaGrupa.getBrojKonta()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteKontnaGrupa(@PathVariable String id) {
        try {
            kontnaGrupaService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
