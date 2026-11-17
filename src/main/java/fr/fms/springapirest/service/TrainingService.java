package fr.fms.springapirest.service;

import fr.fms.springapirest.entities.Training;
import fr.fms.springapirest.repository.TrainingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public List<Training> findAll(){
        return trainingRepository.findAll();
    }

    public Optional<Training> findById(Long id){
        return trainingRepository.findById(id);
    }

    @Transactional
    public Training create(Training training){
        return trainingRepository.save(training);
    }
}
