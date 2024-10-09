package jmaster.io.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jmaster.io.restapi.model.Cart;
import jmaster.io.restapi.model.User;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	List<Cart> findAllByUser(User user);
}
