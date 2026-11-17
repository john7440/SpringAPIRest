package fr.fms.springapirest.service;

import fr.fms.springapirest.entities.CartItem;
import fr.fms.springapirest.entities.Training;
import fr.fms.springapirest.repository.CartItemRepository;
import fr.fms.springapirest.repository.TrainingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final TrainingRepository trainingRepository;

    public CartService(CartItemRepository cartItemRepository, TrainingRepository trainingRepository) {
        this.cartItemRepository = cartItemRepository;
        this.trainingRepository = trainingRepository;
    }

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Transactional
    public CartItem add(Long trainingId, int quantity) {
        Training t = trainingRepository.findById(trainingId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Formation introuvable"));

        if (quantity < 1 || quantity > t.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Stock insuffisant");
        }

        t.setQuantity(t.getQuantity() - quantity);

        CartItem item = cartItemRepository.findByTrainingId(trainingId).orElseGet(()-> new CartItem(null,t,0));
        item.setQuantity(item.getQuantity() + quantity);
        return cartItemRepository.save(item);
    }
}
