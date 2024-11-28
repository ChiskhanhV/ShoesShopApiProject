package com.example.api.controller;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.entity.Shipper;
import com.example.api.repository.ShipperRepository;
import com.example.api.service.ShipperService;
import com.example.api.service.CloudinaryService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/Shippers")
public class ShipperController {
	@Autowired
	ShipperService ShipperService;
	
	@Autowired
	ShipperRepository ShipperRepository;
	
	@Autowired
	CloudinaryService cloudinaryService;

	@GetMapping
	public ResponseEntity<List<Shipper>> GetShipper() {
		List<Shipper> listShippers = ShipperService.getAllShipper();
		if (listShippers != null) {
			return new ResponseEntity<>(listShippers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(listShippers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/login")
	public ResponseEntity<Shipper> newShipper(@RequestParam String phone_number,
			@RequestParam String password) throws Exception {
		Shipper shipper = ShipperService.getShipperByID(phone_number);
		if (shipper != null && shipper.getPassword() != null) {
			String decodedValue = new String(Base64.getDecoder().decode(shipper.getPassword()));
			System.out.println(shipper);
			if (password.equals(decodedValue)) {
				shipper.setPassword(decodedValue);
				return new ResponseEntity<>(shipper, HttpStatus.OK);
			} else {
				return null;
			}
		} else
			return new ResponseEntity<>(shipper, HttpStatus.OK);
	}
	
//	@PostMapping(path = "/signup")
//	public ResponseEntity<Shipper> SignUp(@RequestParam String phone_number, @RequestParam String fullname,
//			@RequestParam String password) throws Exception {
//		Shipper shipper = ShipperService.getShipperByID(phone_number);
//		if (shipper != null) {
//			return new ResponseEntity<>(HttpStatus.CONFLICT);
//		} else {
//			String encodedValue = Base64.getEncoder().encodeToString(password.getBytes());
//			String avatar = "https://res.cloudinary.com/dedqzbz5c/image/upload/v1721817032/shipper_bzk8hn.png";
//			Shipper newshipper = ShipperService.saveShipper(new Shipper(phone_number, fullname, encodedValue, avatar, null, null));
//			System.out.println(newshipper);
//			return new ResponseEntity<>(newshipper, HttpStatus.OK);
//		}
//	}
	
	@PostMapping(path = "/signup")
	public ResponseEntity<Shipper> SignUp(@RequestParam String phone_number, @RequestParam String fullname,
	                                      @RequestParam String password) {
	    // Kiểm tra xem shipper đã tồn tại
	    Optional<Shipper> existingShipper = java.util.Optional.empty();
		try {
			existingShipper = ShipperRepository.findById(phone_number);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (existingShipper.isPresent()) {
	        return new ResponseEntity<>(HttpStatus.CONFLICT);
	    }

	    // Mã hóa mật khẩu
	    String encodedValue = Base64.getEncoder().encodeToString(password.getBytes());
	    String avatar = "https://res.cloudinary.com/dedqzbz5c/image/upload/v1721817032/shipper_bzk8hn.png";

	    // Tạo shipper mới
	    Shipper newShipper = new Shipper(phone_number, fullname, encodedValue, avatar, null, null);
	    Shipper savedShipper = ShipperService.saveShipper(newShipper);

	    return new ResponseEntity<>(savedShipper, HttpStatus.OK);
	}


	@PostMapping(path = "changepassword")
	public ResponseEntity<String> ChangePassword(String phone_number, String password) throws Exception {
		Shipper Shipper = ShipperService.getShipperByID(phone_number);
		if (Shipper != null) {
			String encodedValue = Base64.getEncoder().encodeToString(password.getBytes());
			Shipper.setPassword(encodedValue);
			ShipperService.saveShipper(Shipper);
			return new ResponseEntity<String>("Change password successfully.", HttpStatus.OK);
		} else
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
	}

	@PostMapping(path = "update", consumes = "multipart/form-data")
	public ResponseEntity<Shipper> UpdateAvatar(String phone_number, MultipartFile avatar, String fullname,
			String address) throws Exception {
		Shipper Shipper = ShipperService.getShipperByID(phone_number);
		if (Shipper != null) {
			if (avatar != null) {
				String url = cloudinaryService.uploadFile(avatar);
				Shipper.setAvatar(url);
			}
			Shipper.setFullname(fullname);
			Shipper.setAddress(address);
			ShipperService.saveShipper(Shipper);
			if (Shipper.getPassword() != null) {
				Shipper.setPassword(new String(Base64.getDecoder().decode(Shipper.getPassword())));
			}
			return new ResponseEntity<Shipper>(Shipper, HttpStatus.OK);
		} else {
			return new ResponseEntity<Shipper>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShipper(@PathVariable String id) {
        try {
            Shipper Shipper = ShipperService.getShipperByID(id);
            if (Shipper != null) {
                ShipperService.deleteShipperById(id);
                System.out.println("Shipper with ID " + id + " has been deleted");
                return ResponseEntity.ok("Shipper with ID " + id + " has been deleted");
            } else {
                System.out.println("Shipper with ID " + id + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipper with ID " + id + " not found");
            }
        } catch (Exception e) {
            System.out.println("Error deleting Shipper with ID " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Shipper with ID " + id);
        }
    }
}
