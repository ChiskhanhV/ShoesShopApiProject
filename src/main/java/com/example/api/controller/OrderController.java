package com.example.api.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.Cart;
import com.example.api.entity.Order;
import com.example.api.entity.Order_Item;
import com.example.api.entity.Product_Size;
import com.example.api.entity.User;
import com.example.api.model.OrderDto;
import com.example.api.service.CartService;
import com.example.api.service.OrderService;
import com.example.api.service.Order_ItemService;
import com.example.api.service.ProductService;
import com.example.api.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
	@Autowired
	OrderService orderService;

	@Autowired
	CartService cartService;

	@Autowired
	UserService userService;

	@Autowired
	Order_ItemService order_ItemService;

	@Autowired
	ProductService productService;

	@Autowired
	ModelMapper modelMapper;
	
//	@GetMapping
//	public ResponseEntity<List<Order>> getAllOrder(){
//		List<Order> orders = orderService.getAllOrder();
//		return new ResponseEntity<>(orders, HttpStatus.OK);
//	}
	
	@GetMapping(path = "/orderdetail/{id}")
	public ResponseEntity<OrderDto> getOrderById(@PathVariable int id){
		Order order = orderService.findById(id);
		OrderDto dto = modelMapper.map(order, OrderDto.class);
		return new ResponseEntity<OrderDto>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/ordertoday")
	public ResponseEntity<List<OrderDto>> getOrderToday(){
		List<Order> orders = orderService.getOrderToday();
		List<OrderDto> listOrderDto = new ArrayList<>();
		for (Order o : orders) {
			OrderDto orderDto = modelMapper.map(o, OrderDto.class);
			listOrderDto.add(orderDto);
		}
		return new ResponseEntity<>(listOrderDto, HttpStatus.OK);
	}

	@PostMapping(path = "/placeorder")
	public ResponseEntity<Order> placeOrder(@RequestParam String user_id, @RequestParam String fullname,
			@RequestParam String phoneNumber, @RequestParam String address, @RequestParam String paymentMethod,
			@RequestParam int total) {
		List<Cart> listCart = cartService.GetAllCartByUser_id(user_id);
		Order newOrder = new Order();
		User user = userService.findByIdAndRole(user_id, "user");
		long millis = System.currentTimeMillis();
		Date booking_date = new java.sql.Date(millis);

//		int total = 0;
//		for (Cart y : listCart) {
//			total += y.getProduct().getPrice() * y.getCount();
//		}
		newOrder.setUser(user);
		newOrder.setFullname(fullname);
		newOrder.setBooking_Date(booking_date);
		newOrder.setCountry("Việt Nam");
		newOrder.setEmail(user.getEmail());
		newOrder.setPayment_Method(paymentMethod);
		newOrder.setAddress(address);
		newOrder.setNote(null);
		newOrder.setPhone(phoneNumber);
		newOrder.setStatus("Pending");
		newOrder.setTotal(total);

		newOrder = orderService.saveOrder(newOrder);

		for (Cart y : listCart) {
			for (Product_Size ps : y.getProduct().getProductSize()) {
				if (y.getSize() == ps.getSize()) {
					ps.setQuantity(ps.getQuantity() - y.getCount());
				}
			}
			y.getProduct().setSold(y.getProduct().getSold() + y.getCount());
			productService.saveProduct(y.getProduct());
			Order_Item newOrder_Item = new Order_Item();
			newOrder_Item.setCount(y.getCount());
			newOrder_Item.setSize(y.getSize());
			newOrder_Item.setOrder(newOrder);
			newOrder_Item.setProduct(y.getProduct());
			newOrder_Item = order_ItemService.saveOrder_Item(newOrder_Item);
			cartService.deleteById(y.getId());
		}
		newOrder = orderService.findById(newOrder.getId());
		return new ResponseEntity<>(newOrder, HttpStatus.OK);
	}

	@GetMapping(path = "/orderofuser")
	public ResponseEntity<List<OrderDto>> getAllOrderByUser_Id(String user_id) {
		System.out.println(user_id);
		List<Order> listOrder = orderService.getAllOrderByUser_Id(user_id);
		List<OrderDto> listOrderDto = new ArrayList<>();
		for (Order o : listOrder) {
			OrderDto orderDto = modelMapper.map(o, OrderDto.class);
			System.out.println(orderDto.getId());
			listOrderDto.add(orderDto);
		}
		return new ResponseEntity<>(listOrderDto, HttpStatus.OK);
	}

//	@GetMapping(path = "/orderbystatus")
//	public ResponseEntity<List<OrderDto>> getOrderByStatus(String status) {
//		List<Order> listOrderByStatus = orderService.filterByStatus(status);
//		List<OrderDto> listOrderDto = new ArrayList<>();
//		for (Order o : listOrderByStatus) {
//			OrderDto orderDto = modelMapper.map(o, OrderDto.class);
//			listOrderDto.add(orderDto);
//		}
//		return new ResponseEntity<>(listOrderDto, HttpStatus.OK);
//	}

	@PatchMapping(path = "/updatestatus/{orderId}")
	public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable("orderId") int orderId, String newStatus) {
		Order orderToUpdate = orderService.findById(orderId);
		if (orderToUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		orderToUpdate.setStatus(newStatus);
		Order updatedOrder = orderService.saveOrder(orderToUpdate);
		OrderDto updatedOrderDto = modelMapper.map(updatedOrder, OrderDto.class);

		return new ResponseEntity<>(updatedOrderDto, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Page<Order>> getAllOrder(@RequestParam int page, @RequestParam int pageSize){
		Page<Order> orders = orderService.findAll(page, pageSize);
		return new ResponseEntity<Page<Order>>(orders, HttpStatus.OK);
	}
	
	@GetMapping(path = "/orderbystatus")
	public ResponseEntity<Page<Order>> getOrderByStatus(@RequestParam String status, @RequestParam int page, @RequestParam int pageSize){
		Page<Order> orders = orderService.findByStatus(status,page, pageSize);
		return new ResponseEntity<Page<Order>>(orders, HttpStatus.OK);
	}
	
}