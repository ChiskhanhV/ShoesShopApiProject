package com.example.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.api.entity.Voucher;
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
	Optional<Voucher> findByCode(String code);
	
	List<Voucher> findAll();
	
	void deleteById(int id);
}
