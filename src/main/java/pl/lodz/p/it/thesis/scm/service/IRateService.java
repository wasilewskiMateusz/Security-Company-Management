package pl.lodz.p.it.thesis.scm.service;

import pl.lodz.p.it.thesis.scm.dto.rate.RateWorkplaceDTO;

public interface IRateService {

    void addRate(RateWorkplaceDTO rateWorkplaceDTO, Long id);
}
