package com.example.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findAllByUser_id(String user_id);

	Order findById(int id);

	void deleteById(int id);

	@Query(value = "select * from `order` o where o.payment_method = ?1", nativeQuery = true)
	List<Order> findAllByPayment_Method(String payment_Method);

	@Query(value = "select * from `order` o where o.status = ?1", nativeQuery = true)
	List<Order> filterByStatus(String status);

	@Query(value = "SELECT * FROM `order` o WHERE DATE(o.booking_date) = CURDATE()", nativeQuery = true)
	List<Order> getOrderToday();

	Page<Order> findAll(Pageable pageable);

	Page<Order> findByStatus(String status, Pageable pageable);

	@Query(value = "WITH RECURSIVE date_series AS (" + "    SELECT CURDATE() - INTERVAL 6 DAY AS date "
			+ "    UNION ALL " + "    SELECT date + INTERVAL 1 DAY " + "    FROM date_series "
			+ "    WHERE date + INTERVAL 1 DAY <= CURDATE() " + ") "
			+ "SELECT ds.date AS day, COALESCE(SUM(o.total), 0) AS total_revenue " + "FROM date_series ds "
			+ "LEFT JOIN shoesshop.order o ON DATE(o.booking_date) = ds.date AND o.status = 'Completed' "
			+ "GROUP BY ds.date " + "ORDER BY ds.date;", nativeQuery = true)
	List<Object> findRevenueLast7DaysWithAllDates();

	@Query(value = "WITH RECURSIVE month_series AS (" + "    SELECT 1 AS month_num " + "    UNION ALL "
			+ "    SELECT month_num + 1 " + "    FROM month_series " + "    WHERE month_num < 12 " + ") "
			+ "SELECT DATE_FORMAT(CONCAT(:year, '-', LPAD(month_series.month_num, 2, '0'), '-01'), '%m/%Y') AS month, "
			+ "    COALESCE(SUM(o.total), 0) AS total_revenue " + "FROM month_series "
			+ "LEFT JOIN shoesshop.order o ON DATE_FORMAT(o.booking_date, '%Y-%m') = DATE_FORMAT(CONCAT(:year, '-', LPAD(month_series.month_num, 2, '0'), '-01'), '%Y-%m') "
			+ "    AND o.status = 'Completed' " + "GROUP BY month_series.month_num "
			+ "ORDER BY month_series.month_num", nativeQuery = true)
	List<Object[]> findMonthlyRevenueByYear(@Param("year") int year);

	@Query(value = "WITH RECURSIVE day_series AS (" + "    SELECT 1 AS day_num " + "    UNION ALL "
			+ "    SELECT day_num + 1 " + "    FROM day_series "
			+ "    WHERE day_num < DAY(LAST_DAY(CONCAT(:year, '-', LPAD(:month, 2, '0'), '-01'))) " + ") "
			+ "SELECT LPAD(day_series.day_num, 2, '0') AS day, " + "    COALESCE(SUM(o.total), 0) AS total_revenue "
			+ "FROM day_series "
			+ "LEFT JOIN shoesshop.order o ON DATE_FORMAT(o.booking_date, '%Y-%m-%d') = CONCAT(:year, '-', LPAD(:month, 2, '0'), '-', LPAD(day_series.day_num, 2, '0')) "
			+ "    AND o.status = 'Completed' " + "GROUP BY day_series.day_num "
			+ "ORDER BY day_series.day_num", nativeQuery = true)
	List<Object[]> findDailyRevenueByMonth(@Param("year") int year, @Param("month") int month);

	@Query(value = "SELECT * FROM shoesshop.order o WHERE MONTH(o.booking_date) = :month AND YEAR(o.booking_date) = :year AND o.status = 'Completed';", nativeQuery = true)
	List<Order> getByMonthAndYear(@Param("month") int month, @Param("year") int year);
}