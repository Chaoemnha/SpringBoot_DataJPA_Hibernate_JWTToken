package jmaster.io.restapi.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jmaster.io.restapi.converter.CustomUserDetails;
import jmaster.io.restapi.model.Cart;
import jmaster.io.restapi.model.CartItem;
import jmaster.io.restapi.model.Product;
import jmaster.io.restapi.service.CartService;
import jmaster.io.restapi.service.ProductService;

@Controller
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@RequestMapping("/listcart")
	public String getListCarts(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		model.addAttribute("carts", cartService.getAllCarts(userDetails.getUser_id()));
		model.addAttribute("username", userDetails.getUsername());
		model.addAttribute("roles", userDetails.getAuthorities());
		return "listCarts";
	}
	@PostMapping("/deleteACart")
	public String delACart(HttpServletRequest req) {
		cartService.delACart(Integer.parseInt(req.getParameter("CId")));
		return "returnCI";
	}
	@PostMapping("/addToACart")
	public String addToACart(HttpServletRequest req) {
		System.out.println("34 CC "+req.getParameter("CId"));
		if(Integer.parseInt(req.getParameter("CId"))<0) {
			Cart newCart = cartService.createNewCart(req.getParameter("username"));
			cartService.addCIToCart(newCart.getCId(),req.getParameter("username"), new CartItem(productService.getProductByPid(Integer.parseInt(req.getParameter("PId"))) , cartService.getCartById(newCart.getCId()),  Integer.parseInt(req.getParameter("quantity"))));
		}
		else
		cartService.addCIToCart(Integer.parseInt(req.getParameter("CId")),req.getParameter("username"), new CartItem(productService.getProductByPid(Integer.parseInt(req.getParameter("PId"))) , cartService.getCartById(Integer.parseInt(req.getParameter("CId"))),  Integer.parseInt(req.getParameter("quantity"))));
		return "deleteProduct";
	}
	@RequestMapping("/cart/{id}")
	public String getListCartItems(Model model, HttpServletRequest req) {
		model.addAttribute("cartItems", cartService.getAllCartItems(Integer.parseInt(req.getParameter("CId"))));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		model.addAttribute("username", userDetails.getUsername());
		model.addAttribute("roles", userDetails.getAuthorities());
		return "listCartItems";
	}
	@PostMapping("/deleteFromACart")
	public String deleteFromACart(HttpServletRequest request) {
		cartService.removeFromCart(Integer.parseInt(request.getParameter("CIId")));
		return "returnCI";
	}
	@PostMapping("/updateCartItem")
	public String updateCartItem(HttpServletRequest request) {
		cartService.updateCartItem(Integer.parseInt(request.getParameter("CIId")), Integer.parseInt(request.getParameter("quantity")));
		return "returnCI";
	}
	@RequestMapping("/paymentsuccess")
	public String success() {
		return "home";
	}
}
