package com.example.api.service;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.api.entity.Order;

public interface OrderService {
	List<Order> getAllOrder();
	
	Order saveOrder(Order order);

	List<Order> getAllOrderByUser_Id(String id);
	
	List<Order> getAllOrderByShipper_Id(String id);

	Order findById(int id);

	void deleteById(int id);

	List<Order> findAllByPayment_Method(String payment_Method);

	List<Order> filterByStatus(String status);
	
	List<Order> getOrderToday();
	
	Page<Order> findAll(int page, int pageSize);

	Page<Order> findByStatus(String status, int page, int pageSize);
	
	List<Object> findRevenueLast7DaysWithAllDates();
	
	List<Object[]> findMonthlyRevenueByYear(int year);
	
	List<Order> getOrderByMonthAndYear(int month, int year);
	
	List<Object[]> findDailyRevenueByMonth(int month, int year);
	
	void assignShipper (String shipperId, List<Integer> orderIds) throws Exception;
	
	public Page<Order> searchOrderById(String id, Pageable pageable);
	
	List<Order> filterByStatusOfShipper(String shipperId, String status);

}
