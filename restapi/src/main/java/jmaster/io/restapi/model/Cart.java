package jmaster.io.restapi.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "DBCart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer CId;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", fetch = FetchType.LAZY)
	private Set<CartItem> cartItems = new HashSet<CartItem>();
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	private User user;
	
	public Cart(User user) {
		super();
		this.cartItems = new HashSet<CartItem>();
		this.user = user;
	}
	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public void setACartItemByPId(CartItem updatedCartItem) {
        // Duyệt qua các CartItem trong cartItems
        for (CartItem cartItem : cartItems) {
            // Nếu tìm thấy CartItem có PId trùng khớp
            if (cartItem.getPId() == updatedCartItem.getPId()) {
                // Xóa CartItem cũ
                this.cartItems.remove(cartItem);
                // Thêm CartItem mới đã được cập nhật
                this.cartItems.add(updatedCartItem);
                break; // Thoát vòng lặp sau khi tìm thấy
            }
        }
    }
	public Set<CartItem> getCartItems() {
		return cartItems;
	}
	public Integer getCId() {
		return CId;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
		this.cartItems = new HashSet<CartItem>();
		this.CId=-1;
	}
	public void setCId(Integer cid) {
		CId = cid;
	}
	
	public void addItem(CartItem item) {
		this.cartItems.add(item);
	}
	
	public void removeItem(CartItem items) {
		this.cartItems.add(items);
	}
}
