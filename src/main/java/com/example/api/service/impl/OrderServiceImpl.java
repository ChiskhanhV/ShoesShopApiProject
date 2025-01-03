package com.example.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.entity.Order;
import com.example.api.entity.Shipper;
import com.example.api.repository.OrderRepository;
import com.example.api.repository.ShipperRepository;
import com.example.api.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ShipperRepository shipperRepository;

	@Override
	public Order saveOrder(Order order) {
		// TODO Auto-generated method stub
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrderByUser_Id(String id) {
		// TODO Auto-generated method stub
		return orderRepository.findAllByUser_id(id);
	}

	@Override
	public Order findById(int id) {
		// TODO Auto-generated method stub
		return orderRepository.findById(id);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		orderRepository.deleteById(id);
	}

	@Override
	public List<Order> findAllByPayment_Method(String payment_Method) {
		// TODO Auto-generated method stub
		return orderRepository.findAllByPayment_Method(payment_Method);
	}

	@Override
	public List<Order> filterByStatus(String status) {
		// TODO Auto-generated method stub
		return orderRepository.filterByStatus(status);
	}

	@Override
	public List<Order> getAllOrder() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	@Override
	public List<Order> getOrderToday() {
		// TODO Auto-generated method stub
		return orderRepository.getOrderToday();
	}

	@Override
	public Page<Order> findAll(int page, int pageSize) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(page, pageSize);
		return orderRepository.findAll(pageable);
	}

	@Override
	public Page<Order> findByStatus(String status, int page, int pageSize) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(page, pageSize);
		return orderRepository.findByStatus(status, pageable);
	}

	@Override
	public List<Object> findRevenueLast7DaysWithAllDates() {
		// TODO Auto-generated method stub
		return orderRepository.findRevenueLast7DaysWithAllDates();
	}

	@Override
	public List<Object[]> findMonthlyRevenueByYear(int year) {
		// TODO Auto-generated method stub
		return orderRepository.findMonthlyRevenueByYear(year);
	}

	@Override
	public List<Order> getOrderByMonthAndYear(int month, int year) {
		// TODO Auto-generated method stub
		return orderRepository.getByMonthAndYear(month, year);
	}

	@Override
	public List<Object[]> findDailyRevenueByMonth(int month, int year) {
		// TODO Auto-generated method stub
		return orderRepository.findDailyRevenueByMonth(year, month);
	}

	@Override
	public void assignShipper(String shipperId, List<Integer> orderIds) throws Exception {
	    // Tìm shipper theo ID, ném ngoại lệ nếu không tồn tại
	    Shipper shipper = shipperRepository.findById(shipperId)
	            .orElseThrow(() -> new Exception("Shipper not found with ID: " + shipperId));

	    // Tìm các đơn hàng theo danh sách ID
	    List<Order> orders = orderRepository.findAllById(orderIds);

	    // Cập nhật shipper cho các đơn hàng
	    for (Order order : orders) {
	        order.setShipper(shipper);
	    }

	    // Lưu danh sách đơn hàng đã cập nhật
	    orderRepository.saveAll(orders);
	}

	@Override
	public Page<Order> searchOrderById(String id, Pageable pageable) {
		// TODO Auto-generated method stub
		return orderRepository.findByIdContaining(id, pageable);
	}

	@Override
	public List<Order> getAllOrderByShipper_Id(String id) {
		// TODO Auto-generated method stub
		return orderRepository.findAllByShipperId(id);
	}

	@Override
	public List<Order> filterByStatusOfShipper(String shipperId, String status) {
		// TODO Auto-generated method stub
		return orderRepository.findAllByShipperIdAndStatus(shipperId, status);
	}

	

	
}