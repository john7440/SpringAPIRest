package fr.fms.springapirest.config;

import fr.fms.springapirest.entities.Training;
import fr.fms.springapirest.service.TrainingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TrainingService trainingService;

    public DataInitializer(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(trainingService.findAll().isEmpty()){
            trainingService.create(new Training("Java", "java Standard Edition", 3500, 10));
            trainingService.create(new Training("Spring", "Creation d'une Api Rest avec Spring Boot", 3000, 12));
            trainingService.create(new Training("Angular", "Devenez un pro du frontend avec Angular", 4000, 20));
            trainingService.create(new Training("Python", "Initiation au dev avec Pyhton", 2500, 16));
        }
    }
}
