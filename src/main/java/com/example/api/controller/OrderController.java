package com.example.api.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.api.service.MailService;

import com.example.api.entity.Cart;
import com.example.api.model.Mail;
import com.example.api.entity.Order;
import com.example.api.entity.Order_Item;
import com.example.api.entity.Product_Size;
import com.example.api.entity.Shipper;
import com.example.api.entity.Shipping_Type;
import com.example.api.entity.User;
import com.example.api.entity.Voucher;
import com.example.api.model.OrderDto;
import com.example.api.service.CartService;
import com.example.api.service.OrderService;
import com.example.api.service.ShipperService;
import com.example.api.service.VoucherService;
import com.example.api.service.Shipping_TypeService;
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
	VoucherService voucherService;
	
	@Autowired
	ShipperService shipperService;
	
	@Autowired
	Shipping_TypeService shipping_typeService;

	@Autowired
	Order_ItemService order_ItemService;

	@Autowired
	ProductService productService;
	
	@Autowired
	MailService mailService;

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

//	@PostMapping(path = "/placeorder")
//	public ResponseEntity<Order> placeOrder(@RequestParam String user_id, @RequestParam String fullname,
//			@RequestParam String phoneNumber, @RequestParam String address, @RequestParam String paymentMethod,
//			@RequestParam int total, @RequestParam(required = false) Integer voucherId, @RequestParam int shipping_type_id, @RequestParam(required = false) String shipper_id,
//			@RequestParam Boolean is_pay) throws Exception {
//		int randomPart = ThreadLocalRandom.current().nextInt(1, 1000); // Tạo số ngẫu nhiên từ 1 đến 999
//		int orderId = (int) ((System.currentTimeMillis() + randomPart) % Integer.MAX_VALUE);
//
//		List<Cart> listCart = cartService.GetAllCartByUser_id(user_id);
//		Order newOrder = new Order();
//		User user = userService.findByIdAndRole(user_id, "user");
//		long millis = System.currentTimeMillis();
//		Date booking_date = new java.sql.Date(millis);
//
//		Voucher voucher = voucherId != null ? voucherService.getVoucherById(voucherId) : null;
//	    Shipper shipper = shipper_id != null ? shipperService.getShipperByID(shipper_id) : null;
//		Shipping_Type shipping_type = shipping_typeService.getShipping_TypeById(shipping_type_id);
//		
//		if (shipping_type == null) {
//	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Kiểu vận chuyển không hợp lệ
//	    }
//
////	    // Tính tổng tiền đơn hàng
//	    int voucherDiscount = voucher != null ? voucher.getDiscount() : 0;
//	    int shippingFee = shipping_type.getShipCost();
////	    int finalTotal = total + shippingFee - voucherDiscount;
//	    
//		newOrder.setId(orderId);
//		newOrder.setUser(user);
//		newOrder.setFullname(fullname);
//		newOrder.setBooking_Date(booking_date);
//		newOrder.setCountry("Việt Nam");
//		newOrder.setEmail(user.getEmail());
//		newOrder.setPayment_Method(paymentMethod);
//		newOrder.setAddress(address);
//		newOrder.setNote(null);
//		newOrder.setPhone(phoneNumber);
//		newOrder.setStatus("Pending");
//		newOrder.setTotal(total);
//		newOrder.setVoucher(voucher);
//		newOrder.setShipper(shipper);
//		newOrder.setShipping_type(shipping_type);
//		newOrder.setIsPay(is_pay);
//
//		newOrder = orderService.saveOrder(newOrder);
//
//		for (Cart y : listCart) {
//			for (Product_Size ps : y.getProduct().getProductSize()) {
//				if (y.getSize() == ps.getSize()) {
//					ps.setQuantity(ps.getQuantity() - y.getCount());
//				}
//			}
//			y.getProduct().setSold(y.getProduct().getSold() + y.getCount());
//			productService.saveProduct(y.getProduct());
//			Order_Item newOrder_Item = new Order_Item();
//			newOrder_Item.setCount(y.getCount());
//			newOrder_Item.setSize(y.getSize());
//			newOrder_Item.setOrder(newOrder);
//			newOrder_Item.setProduct(y.getProduct());
//			newOrder_Item = order_ItemService.saveOrder_Item(newOrder_Item);
//			cartService.deleteById(y.getId());
//		}
//		// Tạo email HTML
//        String emailContent = generateOrderEmailContent(newOrder, shippingFee, voucherDiscount, total);
//	    // Gửi email
//        Mail mail = new Mail();
//        mail.setMailFrom("nguyentienngoc02@gmail.com");
//        mail.setMailTo(newOrder.getEmail());
//        mail.setMailSubject("Thông tin đơn hàng #" + newOrder.getId());
//        mail.setMailContent(emailContent);
//        mail.setContentType("text/html");
//
//        mailService.sendEmail(mail);
//		newOrder = orderService.findById(newOrder.getId());
//		return new ResponseEntity<>(newOrder, HttpStatus.OK);
//	}
	
	@PostMapping(path = "/placeorder")
	public ResponseEntity<Order> placeOrder(@RequestParam String user_id, @RequestParam String fullname,
			@RequestParam String phoneNumber, @RequestParam String address, @RequestParam String paymentMethod,
			@RequestParam int total, @RequestParam(required = false) String voucherId,
			@RequestParam int shipping_type_id, @RequestParam Boolean is_pay) throws Exception {
//		UUID uuid = UUID.randomUUID();
//        int orderId = Math.abs(uuid.hashCode());
		int randomPart = ThreadLocalRandom.current().nextInt(1, 1000); // Tạo số ngẫu nhiên từ 1 đến 999
		int orderId = (int) ((System.currentTimeMillis() + randomPart) % Integer.MAX_VALUE);

		List<Cart> listCart = cartService.GetAllCartByUser_id(user_id);
		Order newOrder = new Order();
		User user = userService.findByIdAndRole(user_id, "user");
		long millis = System.currentTimeMillis();
		Date booking_date = new java.sql.Date(millis);

//		Voucher voucher = voucherService.getVoucherById(voucherId);
//		Shipper shipper = shipperService.getShipperByID(shipper_id);
		
		Voucher voucher = null;
		if (voucherId != null && !voucherId.equals("null")) {
		    try {
		        voucher = voucherService.getVoucherById(Integer.parseInt(voucherId));
		        voucher.setNumberUsed(voucher.getNumberUsed() + 1);
		    } catch (NumberFormatException e) {
		        // Xử lý trường hợp không thể chuyển đổi voucherId thành Integer
		        System.out.println("Voucher ID không hợp lệ: " + voucherId);
		    }
		}
		
		int voucherDiscount = voucher != null ? voucher.getDiscount() : 0;
	    
		//Shipper shipper = shipper_id != null ? shipperService.getShipperByID(shipper_id) : null;
		Shipping_Type shipping_type = shipping_typeService.getShipping_TypeById(shipping_type_id);
		int shippingFee = shipping_type.getShipCost();
		newOrder.setId(orderId);
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
		newOrder.setVoucher(voucher);
		//newOrder.setShipper(shipper);
		newOrder.setShipping_type(shipping_type);
		newOrder.setIsPay(is_pay);

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
//		 Tạo email HTML
      String emailContent = generateOrderEmailContent(newOrder, shippingFee, voucherDiscount, total);
	    // Gửi email
      Mail mail = new Mail();
      mail.setMailFrom("nguyentienngoc02@gmail.com");
      mail.setMailTo(newOrder.getEmail());
      mail.setMailSubject("Thông tin đơn hàng #" + newOrder.getId());
      mail.setMailContent(emailContent);
      mail.setContentType("text/html");

      mailService.sendEmail(mail);
		return new ResponseEntity<>(newOrder, HttpStatus.OK);
	}
	private String generateOrderEmailContent(Order order, int shippingFee, int voucherDiscount, int finalTotal) {
        StringBuilder orderItemsHtml = new StringBuilder();
        int orderTotal = 0;
        List<Order_Item> orderItems = order_ItemService.getAllByOrder_Id(order.getId());
        for (Order_Item item : orderItems) {
            int discountedPrice = productService.calculateDiscountedPrice(item.getProduct().getId());
            int itemTotal = discountedPrice * item.getCount();
            orderTotal += itemTotal; // Cộng dồn vào tổng tiền đơn hàng

            orderItemsHtml.append("<tr>")
                          .append("<td>").append(item.getProduct().getProduct_Name()).append("</td>")
                          .append("<td>").append(item.getCount()).append("</td>")
                          .append("<td>").append(item.getSize()).append("</td>")
                          .append("<td>").append(String.format("%,d", discountedPrice)).append(" VND</td>")
                          .append("<td>").append(String.format("%,d", itemTotal)).append(" VND</td>")
                          .append("</tr>");
        }

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid black; padding: 8px; text-align: left; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h3>Xin chào {{customerName}},</h3>
                <p>Cảm ơn Quý khách đã đặt hàng tại <strong>ShoesShop</strong></p>
                <p>Đơn hàng của Quý khách đã được tiếp nhận. Nhân viên của hệ thống sẽ nhanh chóng liên hệ với Quý khách trong thời gian sớm nhất.</p>

                <h4>Thông tin khách hàng</h4>
                <div style="border: 1px solid #000; padding: 10px; margin-bottom: 20px;">
        			<p><strong>Họ tên:</strong> {{customerName}}</p>
        			<p><strong>Email:</strong> {{customerEmail}}</p>
        			<p><strong>Điện thoại:</strong> {{customerPhone}}</p>
        			<p><strong>Địa chỉ:</strong> {{customerAddress}}</p>
        		</div>

                <h4>Thông tin đơn hàng</h4>
                <p>Mã đơn hàng: <strong>#{{orderCode}}</strong></p>
                <p>Ngày tạo: {{orderDate}}</p>

                <table>
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Số lượng</th>
                            <th>Size</th>
                            <th>Giá tiền</th>
                            <th>Tổng</th>
                        </tr>
                    </thead>
                    <tbody>
                        {{orderItems}}
                    </tbody>
                </table>

                <div style="text-align: right;">
        			<p><strong>Tổng cộng:</strong> {{orderTotal}} VND</p>
        			<p><strong>Phí vận chuyển:</strong> {{shippingFee}} VND</p>
        			<p><strong>Voucher:</strong> {{voucherDiscount}} VND</p>
        			<p><strong>Phương thức thanh toán:</strong> {{paymentMethod}}</p>
        			<h3><strong>Tổng thành tiền:</strong> {{finalTotal}} VND</h3>
        		</div>

            </body>
            </html>
            """
            .replace("{{customerName}}", order.getFullname())
            .replace("{{customerEmail}}", order.getEmail())
            .replace("{{customerPhone}}", order.getPhone())
            .replace("{{customerAddress}}", order.getAddress())
            .replace("{{orderCode}}", order.getId().toString())
            .replace("{{orderDate}}", order.getBooking_Date().toString())
            .replace("{{orderItems}}", orderItemsHtml.toString())
            .replace("{{orderTotal}}", String.format("%,d", orderTotal))
            .replace("{{shippingFee}}", String.format("%,d", shippingFee))
            .replace("{{voucherDiscount}}", String.format("%,d", voucherDiscount))
            .replace("{{paymentMethod}}", order.getPayment_Method())
            .replace("{{finalTotal}}", String.format("%,d", finalTotal));
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
	
	@GetMapping(path = "/orderofshipper")
	public ResponseEntity<List<OrderDto>> getAllOrderByShipper_Id(String shipper_id) {
		System.out.println(shipper_id);
		List<Order> listOrder = orderService.getAllOrderByShipper_Id(shipper_id);
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
	
	@PostMapping("/assign-shipper")
	public ResponseEntity<String> assignShipperToOrders(
	        @RequestParam String shipperId,
	        @RequestBody List<Integer> orderIds) {
	    try {
	        orderService.assignShipper(shipperId, orderIds);
	        return ResponseEntity.ok("Shipper assigned successfully!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	    }
	}
	@GetMapping(path = "/search/orders")
	public ResponseEntity<?> searchOrders(@RequestParam String id, 
	                                      @RequestParam int page, 
	                                      @RequestParam int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    Page<Order> orders = orderService.searchOrderById(id, pageable);
	    return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping(path = "/orderStatus")
	public ResponseEntity<List<OrderDto>> getAllOrderByStatus(String status){
		List<Order> listOrderByStatus = orderService.filterByStatus(status);
		List<OrderDto> listOrderDto = new ArrayList<>();
		for(Order o: listOrderByStatus) {
			OrderDto orderDto = modelMapper.map(o, OrderDto.class);
			listOrderDto.add(orderDto);
		}
		return new ResponseEntity<>(listOrderDto, HttpStatus.OK);
	}
	
	@GetMapping(path = "/orderStatusOfShipper")
	public ResponseEntity<List<OrderDto>> getAllOrderByStatusOfShipper(String shipperId, String status){
		List<Order> listOrderByStatusOfShipper = orderService.filterByStatusOfShipper(shipperId,status);
		List<OrderDto> listOrderDto = new ArrayList<>();
		for(Order o: listOrderByStatusOfShipper) {
			OrderDto orderDto = modelMapper.map(o, OrderDto.class);
			listOrderDto.add(orderDto);
		}
		return new ResponseEntity<>(listOrderDto, HttpStatus.OK);
	}
	
	@PatchMapping(path = "/getmoney/{orderId}")
	public ResponseEntity<OrderDto> getMoney(@PathVariable("orderId") int orderId, Boolean is_pay) {
		Order orderToUpdate = orderService.findById(orderId);
		if (orderToUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		orderToUpdate.setIsPay(is_pay);
		Order updatedOrder = orderService.saveOrder(orderToUpdate);
		OrderDto updatedOrderDto = modelMapper.map(updatedOrder, OrderDto.class);

		return new ResponseEntity<>(updatedOrderDto, HttpStatus.OK);
	}
}
