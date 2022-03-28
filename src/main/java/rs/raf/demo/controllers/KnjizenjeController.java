package rs.raf.demo.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.demo.services.IService;
import rs.raf.demo.services.impl.DnevnikKnjizenjaService;

@CrossOrigin
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/knjizenje")
public class KnjizenjeController {

    private final IService<DnevnikKnjizenjaService, Long> dnevnikKnjizenjaService;

    public KnjizenjeController(DnevnikKnjizenjaService dnevnikKnjizenjaService) {
        this.dnevnikKnjizenjaService = dnevnikKnjizenjaService;
    }
}
