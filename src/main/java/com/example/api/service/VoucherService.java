package com.example.api.service;

import java.util.List;

//import org.springframework.data.domain.Page;

import com.example.api.entity.Voucher;
import com.example.api.model.VoucherDto;

public interface VoucherService {
	Voucher saveVoucher(Voucher Voucher);

	Voucher getVoucherById(int id);
	
	List<Voucher> getAllVoucher();
	
	int validateVoucherUsage(String voucherCode, int orderTotal) throws Exception;
	
	void deleteByVoucherID(int id);
}
