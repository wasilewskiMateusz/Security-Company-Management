package pl.lodz.p.it.thesis.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.lodz.p.it.thesis.scm.dto.contract.ContractDTO;
import pl.lodz.p.it.thesis.scm.dto.contract.CreateContractDTO;
import pl.lodz.p.it.thesis.scm.dto.job.CreateJobDTO;
import pl.lodz.p.it.thesis.scm.dto.job.JobDTO;
import pl.lodz.p.it.thesis.scm.dto.workplace.WorkplaceDTO;
import pl.lodz.p.it.thesis.scm.model.Contract;
import pl.lodz.p.it.thesis.scm.model.Job;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.service.IContractService;
import pl.lodz.p.it.thesis.scm.util.JwtUtil;
import pl.lodz.p.it.thesis.scm.util.RestMessage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("contracts")
public class ContractController {

    private final JwtUtil jwtUtil;
    private final IContractService contractService;

    @Autowired
    public ContractController(JwtUtil jwtUtil, IContractService contractService) {
        this.jwtUtil = jwtUtil;
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<ContractDTO> addContract(@Valid @RequestBody CreateContractDTO createContractDTO, WebRequest request){
        String tokenHeader = request.getHeader("Authorization");
        String token = tokenHeader.substring(7);
        Long userId = jwtUtil.getIdFromToken(token);

        Contract createdContract = contractService.createContract(createContractDTO, userId);

        return ResponseEntity.ok(new ContractDTO(createdContract));

    }
    @DeleteMapping("{id}")
    public ResponseEntity<RestMessage> deleteContract(@PathVariable Long id) {

        contractService.deleteContract(id);
        return ResponseEntity.ok(new RestMessage("Success"));
    }
}
