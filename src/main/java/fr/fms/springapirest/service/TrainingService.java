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

    @Transactional
    public Optional<Training> update(Long id,Training training){
        return trainingRepository.findById(id)
                .map(currentTraining -> {
                    currentTraining.setName(training.getName());
                    currentTraining.setDescription(training.getDescription());
                    currentTraining.setPrice(training.getPrice());
                    currentTraining.setQuantity(training.getQuantity());

                    return trainingRepository.save(currentTraining);
                });
    }

    @Transactional
    public boolean deleteById(Long id){
        if(trainingRepository.existsById(id)){
            trainingRepository.deleteById(id);
            return true;
        }
        else return false;
    }
}
