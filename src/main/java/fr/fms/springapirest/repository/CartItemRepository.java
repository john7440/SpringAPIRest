package fr.fms.springapirest.repository;

import fr.fms.springapirest.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByTrainingId(Long trainingId);
}
