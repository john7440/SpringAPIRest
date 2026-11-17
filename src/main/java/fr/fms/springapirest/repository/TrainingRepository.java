package fr.fms.springapirest.repository;

import fr.fms.springapirest.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training,Long> {
}
