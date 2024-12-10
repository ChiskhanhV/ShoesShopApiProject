package com.example.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.example.api.entity.Product;
import com.example.api.entity.Product_Image;
import com.example.api.entity.Product_Size;
import com.example.api.service.CategoryService;
import com.example.api.service.CloudinaryService;
import com.example.api.service.ProductService;
import com.example.api.service.Product_ImageService;
import com.example.api.service.Product_SizeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {
	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	Product_ImageService product_ImageService;

	@Autowired
	Product_SizeService product_SizeService;

	@Autowired
	CloudinaryService cloudinaryService;

	@GetMapping
	public ResponseEntity<List<Product>> getAllProduct() {
		List<Product> listProducts = productService.getAllProduct();
		return new ResponseEntity<>(listProducts, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
		Product product = productService.getProductById(id);
		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		}
		return new ResponseEntity<Product>(product, HttpStatus.NOT_FOUND);
	}

	@GetMapping(path = "/newarrivals")
	public ResponseEntity<List<Product>> newArivals() {
		List<Product> newProducts = productService.findTop12ProductNewArrivals();
		return new ResponseEntity<>(newProducts, HttpStatus.OK);
	}

	@GetMapping(path = "/bestsellers")
	public ResponseEntity<List<Product>> bestSellers() {
		List<Product> bestSellers = productService.findTop12ProductBestSellers();
		return new ResponseEntity<>(bestSellers, HttpStatus.OK);
	}

	@PostMapping(path = "/add", consumes = "multipart/form-data")
	public ResponseEntity<Product> newProduct(@RequestParam String product_name,
			@RequestParam String product_decription, @RequestParam String product_price,
			@RequestParam String product_category, @RequestParam String product_is_active,
			@RequestParam("product_images") List<MultipartFile> product_images,
			@RequestParam("product_sizes") String product_sizes) {

		try {
			Product newProduct = new Product();
			List<Product_Image> productImages = new ArrayList<Product_Image>();
			if (!product_images.isEmpty()) {
				for (MultipartFile image : product_images) {
					Product_Image productImage = new Product_Image();
					String url = cloudinaryService.uploadFile(image);
					productImage.setUrl_Image(url);
					productImages.add(productImage);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			java.sql.Date createdAt = new java.sql.Date(System.currentTimeMillis());
			newProduct.setProduct_Name(product_name);
			newProduct.setPrice(Integer.parseInt(product_price));
			newProduct.setDescription(product_decription);
			newProduct.setIs_Active(Integer.parseInt(product_is_active));
			newProduct.setSold(0);
			newProduct.setCreated_At(createdAt);
			newProduct.setCategory(categoryService.getCategoryById(Integer.parseInt(product_category)));
			System.out.println(newProduct);
			Product savedProduct = productService.saveProduct(newProduct);

			for (Product_Image productImage : productImages) {
				Product_Image productImage1 = new Product_Image();
				productImage1 = productImage;
				productImage1.setProduct(savedProduct);
				product_ImageService.saveProduct_Image(productImage1);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				List<Product_Size> sizeList = objectMapper.readValue(product_sizes,
						new TypeReference<List<Product_Size>>() {
						});
				if (!sizeList.isEmpty()) {
					for (Product_Size size : sizeList) {
						Product_Size newSize = new Product_Size();
						newSize.setSize(size.getSize());
						newSize.setQuantity(size.getQuantity());
						newSize.setProduct(savedProduct);
						product_SizeService.saveProduct_Size(newSize);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity<Product>(savedProduct, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "pagination/{page}/{pageSize}")
	public ResponseEntity<Page<Product>> findProductsWithPagination(@PathVariable int page,
			@PathVariable int pageSize) {
		Page<Product> products = productService.findProductsWithPagination(page, pageSize);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping(path = "/isOnSale")
	public ResponseEntity<List<Product>> getProductIsOnSale() {
		List<Product> products = productService.findProductIsOnSale();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping(path = "/search")
	public ResponseEntity<Page<Product>> searchProduct(@RequestParam String searchContent, @RequestParam int page,
			@RequestParam int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Product> products = productService.findByProduct_NameContaining(searchContent, pageable);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping(path = "/filter")
	public Page<Product> filterProducts(@RequestParam(value = "categoryName", required = false) String categoryName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "shoesSize", required = false) Integer shoesSize,
			@RequestParam(value = "minPrice", required = false) Integer minPrice,
			@RequestParam(value = "maxPrice", required = false) Integer maxPrice,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "12") int size) {

		return productService.filterProducts(categoryName, brandName, shoesSize, minPrice, maxPrice, sortOrder, page, size);
	}

	@GetMapping("/notinpromotion")
	public ResponseEntity<List<Product>> getProductNotInPromotion() {
		List<Product> products = productService.getProductNotInPromotion();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/getbypromoid/{id}")
	public ResponseEntity<List<Product>> getProductByPromotionID(@PathVariable int id) {
		List<Product> products = productService.getProductByPromotionID(id);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
		try {
			Product product = productService.getProductById(id);
			if (product != null) {
				product_ImageService.deleteProductImagesByProductId(id);
				product_SizeService.deleteProductSizesByProductId(id);
				productService.deleteProductById(id);
				System.out.println("Product with ID " + id + " has been deleted");
				return ResponseEntity.ok("Product with ID " + id + " has been deleted");
			} else {
				System.out.println("Product with ID " + id + " not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found");
			}
		} catch (Exception e) {
			System.out.println("Error deleting product with ID " + id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product with ID " + id);
		}
	}

	@PostMapping(path = "/update", consumes = "multipart/form-data")
	public ResponseEntity<Product> editProduct(@RequestParam int id, @RequestParam String product_name,
			@RequestParam String product_decription, @RequestParam(defaultValue = "0") String product_price,
			@RequestParam int product_category, @RequestParam(defaultValue = "0") int product_is_active,
			@RequestParam("product_images") List<MultipartFile> product_images,
			@RequestParam("product_sizes") String product_sizes) {

		Product newProduct = productService.getProductById(id);
		if (newProduct != null) {
			try {
				product_ImageService.deleteProductImagesByProductId(id);
				product_SizeService.deleteProductSizesByProductId(id);
				List<Product_Image> productImages = new ArrayList<Product_Image>();
				if (!product_images.isEmpty()) {
					for (MultipartFile image : product_images) {
						Product_Image productImage = new Product_Image();
						String url = cloudinaryService.uploadFile(image);
						productImage.setUrl_Image(url);
						productImages.add(productImage);
					}
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				newProduct.setProduct_Name(product_name);
				newProduct.setPrice(Integer.parseInt(product_price));
				newProduct.setDescription(product_decription);
				newProduct.setIs_Active(product_is_active);
				newProduct.setCategory(categoryService.getCategoryById(product_category));
				System.out.println(newProduct);
				Product savedProduct = productService.saveProduct(newProduct);

				for (Product_Image productImage : productImages) {
					Product_Image productImage1 = new Product_Image();
					productImage1 = productImage;
					productImage1.setProduct(savedProduct);
					product_ImageService.saveProduct_Image(productImage1);
				}

				ObjectMapper objectMapper = new ObjectMapper();
				try {
					List<Product_Size> sizeList = objectMapper.readValue(product_sizes,
							new TypeReference<List<Product_Size>>() {
							});
					if (!sizeList.isEmpty()) {
						for (Product_Size size : sizeList) {
							Product_Size newSize = new Product_Size();
							newSize.setSize(size.getSize());
							newSize.setQuantity(size.getQuantity());
							newSize.setProduct(savedProduct);
							product_SizeService.saveProduct_Size(newSize);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				return new ResponseEntity<Product>(savedProduct, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
