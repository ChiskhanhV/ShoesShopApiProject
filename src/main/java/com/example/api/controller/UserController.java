package com.example.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.entity.User;
import com.example.api.model.Mail;
import com.example.api.service.CloudinaryService;
import com.example.api.service.MailService;
import com.example.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	MailService mailService;

	@Autowired
	CloudinaryService cloudinaryService;
	
	@Autowired
	HttpSession session;

	@GetMapping
	public ResponseEntity<Page<User>> GetAllUser(@RequestParam int page, @RequestParam int pageSize) {
		Page<User> listcaUser = userService.getAllUser(page, pageSize);
		return new ResponseEntity<>(listcaUser, HttpStatus.OK);
	}

	@GetMapping(path = "/login")
	public ResponseEntity<Optional<User>> Login(@RequestParam String id, @RequestParam String password) {
		System.out.println(id);
		Optional<User> user = userService.getUserByID(id);

		if (user.isPresent() && user.get().getPassword() != null) {
			String decodedValue = new String(Base64.getDecoder().decode(user.get().getPassword()));
			System.out.println(user.get());
			if (password.equals(decodedValue)) {
				user.get().setPassword(decodedValue);
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return null;
			}
		} else
			return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping(path = "/signup")
	public ResponseEntity<User> SignUp(@RequestParam String username, @RequestParam String fullname,
			@RequestParam String email, @RequestParam String password) {
		User user = userService.findByIdAndRole(username, "user");
		if (user != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} else {
			String encodedValue = Base64.getEncoder().encodeToString(password.getBytes());
			String avatar = "https://res.cloudinary.com/dedqzbz5c/image/upload/v1721817032/user_bzk8hn.png";
			User newUser = userService.saveUser(new User(username, "default", "user", encodedValue, fullname, avatar,
					email, null, null, null, null, null, null));
			System.out.println(newUser);
			return new ResponseEntity<>(newUser, HttpStatus.OK);
		}
	}

	@PostMapping(path = "changepassword")
	public ResponseEntity<String> ChangePassword(String id, String password) {
		User user = userService.findByIdAndRole(id, "user");
		if (user != null) {
			String encodedValue = Base64.getEncoder().encodeToString(password.getBytes());
			user.setPassword(encodedValue);
			userService.saveUser(user);
			return new ResponseEntity<String>("Change password successfully.", HttpStatus.OK);
		} else
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
	}

	@PostMapping(path = "update", consumes = "multipart/form-data")
	public ResponseEntity<User> UpdateAvatar(String id, MultipartFile avatar, String fullname, String email,
			String phoneNumber, String address) {
		User user = userService.findByIdAndRole(id, "user");
		if (user != null) {
			if (avatar != null) {
				String url = cloudinaryService.uploadFile(avatar);
				user.setAvatar(url);
			}
			user.setUser_Name(fullname);
			user.setEmail(email);
			user.setPhone_Number(phoneNumber);
			user.setAddress(address);
			userService.saveUser(user);
			if (user.getPassword() != null) {
				user.setPassword(new String(Base64.getDecoder().decode(user.getPassword())));
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping(path = "/userbyid/{id}")
	public ResponseEntity<Optional<User>> getUserByID(@PathVariable String id) {
		Optional<User> user = userService.getUserByID(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping(path = "/add", consumes = "multipart/form-data")
	public ResponseEntity<User> AddUser(String username, String fullname, String email, String password, String role,
			String phone, String address, MultipartFile avatarInput) {
		Optional<User> user = userService.getUserByID(username);
		if (user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} else {
			String encodedValue = Base64.getEncoder().encodeToString(password.getBytes());
			String avatar = "";
			if (avatarInput != null) {
				avatar = cloudinaryService.uploadFile(avatarInput);
			} else {
				avatar = "https://res.cloudinary.com/dedqzbz5c/image/upload/v1721817032/user_bzk8hn.png";
			}

			User newUser = userService.saveUser(new User(username, "default", role, encodedValue, fullname, avatar,
					email, phone, address, null, null, null, null));
			System.out.println(newUser);
			return new ResponseEntity<>(newUser, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/edit", consumes = "multipart/form-data")
	public ResponseEntity<User> UpdateUser(String id, String password, MultipartFile avatar, String fullname, String email, String role,
			String phone, String address) {
		Optional<User> user = userService.getUserByID(id);
		if (user.isPresent()) {
			if (avatar != null) {
				String url = cloudinaryService.uploadFile(avatar);
				user.get().setAvatar(url);
			}
			user.get().setUser_Name(fullname);
			user.get().setEmail(email);
			user.get().setRole(role);
			user.get().setPhone_Number(phone);
			user.get().setAddress(address);
			userService.saveUser(user.get());
			String encodedValue = Base64.getEncoder().encodeToString(password.getBytes());
			if (user.get().getPassword().equals(encodedValue) == false) {
				user.get().setPassword(encodedValue);
			}
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id) {
		try {
			Optional<User> user = userService.getUserByID(id);
			if (user.isPresent()) {
				userService.deleteUserById(id);
				System.out.println("User with ID " + id + " has been deleted");
				return ResponseEntity.ok("User with ID " + id + " has been deleted");
			} else {
				System.out.println("User with ID " + id + " not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found");
			}
		} catch (Exception e) {
			System.out.println("Error deleting user with ID " + id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user with ID " + id);
		}
	}
	
	@PostMapping(path = "/forgot")
	public ResponseEntity<String> ForgotPassword(@RequestParam String id) {
		User user = userService.findByIdAndRole(id, "user");
		if (user != null) {
			int code = (int) Math.floor(((Math.random() * 899999) + 100000));
			Mail mail = new Mail();
			mail.setMailFrom("nguyentienngoc02@gmail.com");
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("For got Password");
			mail.setMailContent("Your code is: " + code);
			mailService.sendEmail(mail);
			session.setAttribute("code", code);
			System.out.println(code);
			ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = "";
            try {
                jsonResponse = mapper.writeValueAsString(String.valueOf(code));
                return new ResponseEntity<String>(jsonResponse, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<String>("Error generating JSON response", HttpStatus.INTERNAL_SERVER_ERROR);
            }

		} else
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path = "changepasswordadmin")
	public ResponseEntity<String> ChangePasswordAadmi(String id, String password) {
		User user = userService.findByIdAndRole(id, "admin");
		if (user != null) {
			String encodedValue = Base64.getEncoder().encodeToString(password.getBytes());
			user.setPassword(encodedValue);
			userService.saveUser(user);
			return new ResponseEntity<String>("Change password successfully.", HttpStatus.OK);
		} else
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	@PostMapping(path = "/forgotadmin")
	public ResponseEntity<String> ForgotPasswordAdmin(@RequestParam String id) {
		User user = userService.findByIdAndRole(id, "admin");
		if (user != null) {
			int code = (int) Math.floor(((Math.random() * 899999) + 100000));
			Mail mail = new Mail();
			mail.setMailFrom("nguyentienngoc02@gmail.com");
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("For got Password");
			mail.setMailContent("Your code is: " + code);
			mailService.sendEmail(mail);
			session.setAttribute("code", code);
			System.out.println(code);
			ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = "";
            try {
                jsonResponse = mapper.writeValueAsString(String.valueOf(code));
                return new ResponseEntity<String>(jsonResponse, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<String>("Error generating JSON response", HttpStatus.INTERNAL_SERVER_ERROR);
            }

		} else
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path = "updateadmin", consumes = "multipart/form-data")
	public ResponseEntity<User> UpdateAdmin(String id, MultipartFile avatar, String fullname, String email,
			String phoneNumber, String address) {
		User user = userService.findByIdAndRole(id, "admin");
		if (user != null) {
			if (avatar != null) {
				String url = cloudinaryService.uploadFile(avatar);
				user.setAvatar(url);
			}
			user.setUser_Name(fullname);
			user.setEmail(email);
			user.setPhone_Number(phoneNumber);
			user.setAddress(address);
			userService.saveUser(user);
			if (user.getPassword() != null) {
				user.setPassword(new String(Base64.getDecoder().decode(user.getPassword())));
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
