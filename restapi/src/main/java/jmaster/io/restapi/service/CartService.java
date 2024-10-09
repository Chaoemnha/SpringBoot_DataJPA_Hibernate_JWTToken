package jmaster.io.restapi.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jmaster.io.restapi.model.Cart;
import jmaster.io.restapi.model.CartItem;
import jmaster.io.restapi.model.Product;
import jmaster.io.restapi.model.User;
import jmaster.io.restapi.repository.CartItemRepository;
import jmaster.io.restapi.repository.CartRepository;
import jmaster.io.restapi.repository.ProductRepository;
import jmaster.io.restapi.repository.UserRepository;

@Service
public class CartService {
	//EnityM cung cấp merge giúp chống tách rời user với cart
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	public Cart addCIToCart(int CId, String username, CartItem cartItem) {
		int exitsCIId=findCIIdExistPId(CId, cartItem.getPId());
		if(exitsCIId>0) {
			Cart cart = cartRepository.findById(CId).orElseThrow(()->new EntityNotFoundException());
			CartItem cartItem2 = cartItemRepository.findById(exitsCIId).orElseThrow(()->new EntityNotFoundException());
			cartItem2.setQuantity(cartItem.getQuantity()+cartItem2.getQuantity());
			cart.setACartItemByPId(cartItem2);
			cartItemRepository.save(cartItem2);
			return cartRepository.save(cart);
		}
		else {
		Cart cart = cartRepository.findById(CId).orElseThrow(()->new EntityNotFoundException());
		cart.addItem(cartItem);
		cartItemRepository.save(cartItem);
		return cartRepository.save(cart);
		}
	}
	
	public Cart createNewCart(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(()-> new EntityNotFoundException("CS 60"));
		Cart cart = new Cart(user);
		//Rất ức chế, nhớ kiểm tra xem id của Generate đã null chưa rồi mới save, làm t sửa bao tiếng
		cart.setCId(null);
		return cartRepository.save(cart);
	}
	public void removeFromCart(int CIId) {
		cartItemRepository.deleteById(CIId);
	}
	public List<Cart> getAllCarts(int user_id) {
		User user = userRepository.findById(user_id).orElseThrow(()-> new EntityNotFoundException());
		return cartRepository.findAllByUser(user);
	}
	public List<CartItem> getAllCartItems(int CId){
		Cart cart = cartRepository.findById(CId).orElseThrow(()->new EntityNotFoundException("CS 71"));
		return cartItemRepository.findByCart(cart);
	}
	public CartItem updateCartItem(int CIId, int quantity) {
		CartItem cartItem = cartItemRepository.findById(CIId).orElseThrow(()->new EntityNotFoundException());
		cartItem.setQuantity(quantity);
		return cartItemRepository.save(cartItem);
	}
	public void delACart(int CId) {
		cartRepository.deleteById(CId);
	}

	public int findCIIdExistPId(int CId, int PId) {
		Cart cart = cartRepository.findById(CId).orElseThrow(()->new EntityNotFoundException());
		Set<CartItem> cartItems = cart.getCartItems();
		for(CartItem item : cartItems) {
			if(item.getPId()==PId) {
				return item.getCIId();
			}
		}
		return -1;
	}
	
	public Cart getCartById(int CId) {
		return cartRepository.findById(CId).orElse(new Cart());
	}
}
