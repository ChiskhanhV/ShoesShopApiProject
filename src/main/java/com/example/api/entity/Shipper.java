package com.example.api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shipper")
public class Shipper {
	@Id()
    @Column(name = "id", nullable = false, columnDefinition = "varchar(20)")
    private String id; // Số điện thoại, sử dụng làm khóa chính

    @Column(name = "fullname", nullable = false, columnDefinition = "nvarchar(255)")
    private String fullname; // Tên đầy đủ của shipper

    @Column(name = "password", columnDefinition = "varchar(255)")
    private String password; // Mật khẩu của shipper

    @Column(name = "avatar", columnDefinition = "nvarchar(1111)")
    private String avatar; // URL hoặc đường dẫn ảnh đại diện

    @Column(name = "address", columnDefinition = "MEDIUMTEXT")
    private String address; // Địa chỉ của shipper

    
    @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL)
	private List<Order> order;
}
