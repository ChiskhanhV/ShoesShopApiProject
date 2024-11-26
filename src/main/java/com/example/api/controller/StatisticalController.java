package com.example.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.Order;
import com.example.api.model.OrderDto;
import com.example.api.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/statistical")
public class StatisticalController {
	@Autowired
	OrderService orderService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping(path = "/revenue-last-7day")
	public ResponseEntity<List<Object>> getRevenueLast7DaysWithAllDates(){
		List<Object> revenue = orderService.findRevenueLast7DaysWithAllDates();
		return new ResponseEntity<List<Object>>(revenue, HttpStatus.OK);
	}
	
	@GetMapping(path = "/monthly-revenue-by-year")
	public ResponseEntity<List<Object[]>> getMonthlyRevenueByYear(@RequestParam Integer year){
		List<Object[]> revenue = orderService.findMonthlyRevenueByYear(year);
		return new ResponseEntity<List<Object[]>>(revenue, HttpStatus.OK);
	}
	
	@GetMapping(path = "/daily-revenue-by-month")
	public ResponseEntity<List<Object[]>> findDailyRevenueByMonth(@RequestParam Integer month, @RequestParam Integer year){
		List<Object[]> revenue = orderService.findDailyRevenueByMonth(month, year);
		return new ResponseEntity<List<Object[]>>(revenue, HttpStatus.OK);
	}
	
	@GetMapping(path = "/order-by-month-year")
	public ResponseEntity<List<OrderDto>> getOrderByMonthAndYear(@RequestParam Integer month, @RequestParam Integer year){
		List<Order> orders = orderService.getOrderByMonthAndYear(month, year);
		List<OrderDto> listOrderDto = new ArrayList<>();
		for (Order o : orders) {
			OrderDto orderDto = modelMapper.map(o, OrderDto.class);
			listOrderDto.add(orderDto);
		}
		return new ResponseEntity<>(listOrderDto, HttpStatus.OK);
	}
	
	
}
