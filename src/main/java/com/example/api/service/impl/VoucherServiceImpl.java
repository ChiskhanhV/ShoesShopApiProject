package com.example.api.service.impl;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entity.Voucher;
import com.example.api.model.VoucherDto;
import com.example.api.repository.VoucherRepository;
import com.example.api.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService{
	@Autowired
	private VoucherRepository voucherRepository;
	
	@Override
	public Voucher saveVoucher(Voucher Voucher) {
		// TODO Auto-generated method stub
		return voucherRepository.save(Voucher);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Voucher getVoucherById(int id) {
		// TODO Auto-generated method stub
		return voucherRepository.getById(id);
	}

	@Override
	public List<Voucher> getAllVoucher() {
		// TODO Auto-generated method stub
		return voucherRepository.findAll();
	}

	@Override
	    public int validateVoucherUsage(String voucherCode, int orderTotal) throws Exception {
	        // Tìm voucher theo mã code
	        Voucher voucher = voucherRepository.findByCode(voucherCode)
	                .orElseThrow(() -> new NoSuchFieldException("Voucher không tồn tại với mã: " + voucherCode));

	        // Kiểm tra trạng thái voucher
	        if (voucher.isDeleted() || !voucher.isStatus()) {
	            throw new IllegalStateException("Voucher không hợp lệ hoặc đã bị xóa.");
	        }

	        // Kiểm tra số lần sử dụng
	        if (voucher.getLimitNumber() <= voucher.getNumberUsed()) {
	            throw new IllegalStateException("Voucher đã đạt giới hạn sử dụng. Không thể áp dụng.");
	        }

	        // Kiểm tra ngày hết hạn
	        Date now = new Date();
	        if (voucher.getExpirationDate().before(now)) {
	            throw new IllegalStateException("Voucher đã hết hạn.");
	        }

	        // Kiểm tra giá trị thanh toán tối thiểu
	        if (orderTotal < voucher.getPaymentLimit()) {
	            throw new IllegalStateException("Tổng giá trị đơn hàng không đạt mức tối thiểu để sử dụng voucher.");
	        }

	        // Tính toán tổng giá trị sau khi giảm giá
	        int discountAmount = voucher.getDiscount();
	        int newOrderTotal = orderTotal - discountAmount;

	        // Đảm bảo tổng giá trị không âm
	        if (newOrderTotal < 0) {
	            throw new IllegalStateException("Giá trị giảm giá vượt quá tổng giá trị đơn hàng.");
	        }

	        // Tăng số lần sử dụng voucher
	        voucher.setNumberUsed(voucher.getNumberUsed() + 1);
	        voucherRepository.save(voucher);

	        // Trả về tổng giá trị sau khi giảm giá
	        return newOrderTotal;
	    }


	@Override
	public void deleteByVoucherID(int id) {
		// TODO Auto-generated method stub
		voucherRepository.deleteById(id);
	}

}
