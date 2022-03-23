package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Faktura;
import rs.raf.demo.services.IFakturaService;
import rs.raf.demo.services.impl.FakturaService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/faktura")
public class FakturaRestController {

    private final IFakturaService fakturaService;

    public FakturaRestController(FakturaService fakturaService) {
        this.fakturaService = fakturaService;
    }

    @GetMapping(value = "/ulazneFakture", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUlazneFakture() {
        List<Faktura> ulazneFakture = fakturaService.findUlazneFakture();
        if(ulazneFakture.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(ulazneFakture);
        }
    }

    @GetMapping(value = "/izlazneFakture", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getIzlazneFakture() {
        List<Faktura> izlazneFakture = fakturaService.findIzlazneFakture();
        if(izlazneFakture.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(izlazneFakture);
        }
    }

    @GetMapping(value = "/sume", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSume(@RequestParam String tipFakture){
        Map<String, Double> sume = fakturaService.getSume(tipFakture);
        if(!sume.isEmpty()) {
            return ResponseEntity.ok(sume);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFakture(){
        if(fakturaService.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(fakturaService.findAll());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createFaktura(@Valid @RequestBody Faktura faktura){
        return ResponseEntity.ok(fakturaService.save(faktura));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFaktura(@Valid @RequestBody Faktura faktura){
        Optional<Faktura> optionalFaktura = fakturaService.findById(faktura.getFakturaId());
        if(optionalFaktura.isPresent()) {
            return ResponseEntity.ok(fakturaService.save(faktura));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteFaktura(@PathVariable("id") Long id){
        fakturaService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
