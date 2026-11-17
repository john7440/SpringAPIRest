package fr.fms.springapirest.controller;

import fr.fms.springapirest.entities.CartItem;
import fr.fms.springapirest.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItem> findAll() {
        return cartService.findAll();
    }

    @PostMapping("/{trainingId}")
    public CartItem addToCart(@PathVariable Long trainingId, @RequestParam(defaultValue = "1") int quantity) {
        return cartService.addToCart(trainingId, quantity);
    }

    @DeleteMapping("/{trainingId}")
    public void removeFromCart(@PathVariable Long trainingId) {
        cartService.removeFromCart(trainingId);
    }
}
