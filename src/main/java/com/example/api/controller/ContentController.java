package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.entity.Content;
import com.example.api.service.ContentService;
import com.example.api.service.CloudinaryService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/contents")
public class ContentController {
	@Autowired
	ContentService ContentService;

	@Autowired
	CloudinaryService cloudinaryService;

	@GetMapping
	public ResponseEntity<List<Content>> GetContent() {
		List<Content> listContents = ContentService.getAllContent();
		if (listContents != null) {
			return new ResponseEntity<>(listContents, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(listContents, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/pagination/{page}/{pageSize}")
	public ResponseEntity<Page<Content>> findProductsWithPagination(@PathVariable int page,@PathVariable int pageSize){
		Page<Content> categories = ContentService.getContentByPagination(page, pageSize);
		return new ResponseEntity<>(categories, HttpStatus.OK); 
	}

	@PostMapping(path = "/add", consumes = "multipart/form-data")
	public ResponseEntity<Content> newContent(@RequestParam String title, @RequestParam String fulltext,
			@RequestParam MultipartFile ContentImage, @RequestParam Boolean status) {
		Content newContent = new Content();
		newContent.setTitle(title);
		newContent.setFullText(fulltext);

		if (ContentImage != null) {
			String url = cloudinaryService.uploadFile(ContentImage);
			newContent.setImg(url);
		}
		newContent.setStatus(status);
		newContent = ContentService.saveContent(newContent);
		if (newContent != null) {
			System.out.println("New Content add success.");
			return new ResponseEntity<>(newContent, HttpStatus.OK);
		} else {
			System.out.println("New Content add failed.");
			return new ResponseEntity<>(newContent, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/edit/{id}", consumes = "multipart/form-data")
	public ResponseEntity<Content> editContent(@PathVariable Integer id, 
			@RequestParam String title, @RequestParam String fulltext,
			@RequestParam MultipartFile ContentImage, @RequestParam Boolean status) {

		Content newContent = ContentService.getContentById(id);
		newContent.setTitle(title);
		newContent.setFullText(fulltext);

		if (ContentImage != null) {
			String url = cloudinaryService.uploadFile(ContentImage);
			newContent.setImg(url);
		}
		newContent.setStatus(status);
		newContent = ContentService.saveContent(newContent);
			System.out.println("Edit Content success.");
			return new ResponseEntity<Content>(newContent, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContent(@PathVariable Integer id) {
        try {
            Content Content = ContentService.getContentById(id);
            if (Content != null) {
                ContentService.deleteContentById(id);
                System.out.println("Content with ID " + id + " has been deleted");
                return ResponseEntity.ok("Content with ID " + id + " has been deleted");
            } else {
                System.out.println("Content with ID " + id + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Content with ID " + id + " not found");
            }
        } catch (Exception e) {
            System.out.println("Error deleting Content with ID " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Content with ID " + id);
        }
    }
}
