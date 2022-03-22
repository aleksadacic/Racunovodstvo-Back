package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Faktura;
import rs.raf.demo.services.IFakturaService;
import rs.raf.demo.services.impl.FakturaService;

import java.util.List;
import rs.raf.demo.model.TipFakture;

import java.util.Map;

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
        if (izlazneFakture.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(izlazneFakture);
        }
    }

    @GetMapping(value = "/sume", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSume(@RequestParam String tipFakture){
        TipFakture tip = TipFakture.valueOf(tipFakture);
        Double sumaPorez = fakturaService.calculateSumPorez(tip);
        Double sumaProdajnaVrednost = fakturaService.calculateSumProdajnaVrednost(tip);
        Double sumaRabat = fakturaService.calculateSumRabat(tip);
        Double sumaZaNaplatu = fakturaService.calculateSumNaplata(tip);
        Map response = Map.of(
                "sumaPorez", sumaPorez,
                "sumaProdajnaVrednost", sumaProdajnaVrednost,
                "sumaRabat", sumaRabat,
                "sumaZaNaplatu", sumaZaNaplatu
        );
        if(!response.isEmpty()) {
            return ResponseEntity.ok(response);
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

}
