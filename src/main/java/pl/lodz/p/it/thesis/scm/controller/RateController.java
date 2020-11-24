package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import pl.lodz.p.it.thesis.scm.dto.rate.RateWorkplaceDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.CreateWorkplaceDTO;
import pl.lodz.p.it.thesis.scm.service.implementation.RateService;
import pl.lodz.p.it.thesis.scm.util.JwtUtil;
import pl.lodz.p.it.thesis.scm.util.RestMessage;

import javax.validation.Valid;

@RequestMapping("rates")
@RestController
public class RateController {

    private final JwtUtil jwtUtil;
    private final RateService rateService;

    @Autowired
    public RateController(JwtUtil jwtUtil, RateService rateService) {
        this.jwtUtil = jwtUtil;
        this.rateService = rateService;
    }

    @PostMapping
    public ResponseEntity<RestMessage> addWorkplace(@Valid @RequestBody RateWorkplaceDTO rateWorkplaceDTO,
                                                    WebRequest request){
        String tokenHeader = request.getHeader("Authorization");
        String token = tokenHeader.substring(7);
        Long id = jwtUtil.getIdFromToken(token);
        rateService.addRate(rateWorkplaceDTO, id);
        return ResponseEntity.ok(new RestMessage("Success"));
    }
}
