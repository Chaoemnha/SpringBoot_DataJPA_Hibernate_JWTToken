package jmaster.io.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jmaster.io.restapi.model.Cart;
import jmaster.io.restapi.model.CartItem;
import jmaster.io.restapi.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
	boolean existsByProduct(Product product);
	Optional<CartItem> findByProduct(Product product);
	List<CartItem> findByCart(Cart cart);
}
