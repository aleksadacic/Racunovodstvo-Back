package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.TipFakture;
import rs.raf.demo.services.FakturaService;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/faktura")
public class FakturaRestController {
    private final FakturaService fakturaService;

    public FakturaRestController(FakturaService fakturaService) {
        this.fakturaService = fakturaService;
    }

    @GetMapping(value = "/sume", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSume(@RequestParam String tipFakture){
        TipFakture tip = TipFakture.valueOf(tipFakture);
        Double sumaPorez = fakturaService.calculateSumPorez(tip);
        Double sumaProdajnaVrednost = fakturaService.calculateSumProdajnaVrednost(tip);
        Double sumaRabat = fakturaService.calculateSumRabat(tip);
        Double sumaZaNaplatu = fakturaService.calculateSumZaNaplatu(tip);
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
}
