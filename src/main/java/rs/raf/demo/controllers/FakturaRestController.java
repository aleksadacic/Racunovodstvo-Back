package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> getSume(@RequestParam boolean kifKuf){
        Double porez = fakturaService.calculateSumPorez(kifKuf);
        Double prodajnaVrednost = fakturaService.calculateSumProdajnaVrednost(kifKuf);
        Double rabat = fakturaService.calculateSumRabat(kifKuf);
        Double zaNaplatu = fakturaService.calculateSumZaNaplatu(kifKuf);
        Map response = Map.of(
                "porez", porez,
                "prodajnaVrednost", prodajnaVrednost,
                "rabat", rabat,
                "zaNaplatu", zaNaplatu
        );
        if(!response.isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
