package jmaster.io.restapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="DBCartitem")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CIId;
	@OneToOne
	@JoinColumn(name = "PId")
	private Product product;
	@ManyToOne
	@JoinColumn(name = "CId")
	private Cart cart;
	private int quantity;
	public CartItem(Product product, Cart cart, int quantity) {
		super();
		this.cart = cart;
		this.product = product;
		//this.user.setUsername(user);
		this.quantity = quantity;
	}
	
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getPId() {
		return product.getPId();
	}
	public int getCIId() {
		return CIId;
	}
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void setCIId(int cIId) {
		CIId = cIId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotalPrice() {
		return quantity*product.getPrice();
	}
}
