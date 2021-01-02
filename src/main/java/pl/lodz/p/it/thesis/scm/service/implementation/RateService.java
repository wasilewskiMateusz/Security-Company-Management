package pl.lodz.p.it.thesis.scm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.thesis.scm.dto.rate.RateWorkplaceDTO;
import pl.lodz.p.it.thesis.scm.exception.RestException;
import pl.lodz.p.it.thesis.scm.model.Rate;
import pl.lodz.p.it.thesis.scm.model.User;
import pl.lodz.p.it.thesis.scm.model.Workplace;
import pl.lodz.p.it.thesis.scm.repository.RateRepository;
import pl.lodz.p.it.thesis.scm.repository.UserRepository;
import pl.lodz.p.it.thesis.scm.repository.WorkplaceRepository;
import pl.lodz.p.it.thesis.scm.service.IRateService;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class RateService implements IRateService {

    private final RateRepository rateRepository;
    private final UserRepository userRepository;
    private final WorkplaceRepository workplaceRepository;

    @Autowired
    public RateService(RateRepository rateRepository, UserRepository userRepository, WorkplaceRepository workplaceRepository) {
        this.rateRepository = rateRepository;
        this.userRepository = userRepository;
        this.workplaceRepository = workplaceRepository;
    }

    @Override
    public void addRate(RateWorkplaceDTO rateWorkplaceDTO, Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<Workplace> workplaceOptional = workplaceRepository.findById(rateWorkplaceDTO.getWorkplaceId());

        if(userOptional.isEmpty()){
            throw new RestException("Exception.workplace.owner.id.not.found");
        }

        if(workplaceOptional.isEmpty()){
            throw new RestException("Exception.job.workplace.id.not.found");
        }

        User user = userOptional.get();
        Workplace workplace = workplaceOptional.get();
        Rate rate;

        Optional<Rate> previousRateOptional = user.getRates().stream().filter(r -> r.getWorkplace().getId().equals(rateWorkplaceDTO.getWorkplaceId())).findFirst();
        if(previousRateOptional.isPresent()){
            Rate previousRate = previousRateOptional.get();

            double sumOfRates = workplace.getAverageRate()*workplace.getRates().size();
            sumOfRates-=previousRate.getValue();
            sumOfRates+=rateWorkplaceDTO.getRate();
            sumOfRates/=workplace.getRates().size();

            workplace.setAverageRate(sumOfRates);

            previousRate.setValue(rateWorkplaceDTO.getRate());
            rate = previousRate;

        }
        else{
            rate = new Rate(rateWorkplaceDTO.getRate(), workplace, user);

            double sumOfRates = workplace.getAverageRate()*workplace.getRates().size();
            sumOfRates+=rateWorkplaceDTO.getRate();
            sumOfRates/=workplace.getRates().size()+1;
            workplace.setAverageRate(sumOfRates);
        }


        workplaceRepository.save(workplace);
        rateRepository.save(rate);
    }
}
