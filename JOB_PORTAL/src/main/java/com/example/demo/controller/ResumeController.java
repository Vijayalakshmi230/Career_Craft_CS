package com.example.demo.controller;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entity.Resume;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.ResumeService;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ResumeController {

	private final ResumeService resumeService;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, String>> uploadResume(@RequestParam("file") MultipartFile file,
			@RequestParam("userId") Long userId) {
		resumeService.uploadResume(file, userId);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Resume uploaded successfully");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/download/{userId}")
	public ResponseEntity<byte[]> downloadResume(@PathVariable Long userId) {
	    Resume resume = resumeService.getResumeByUserId(userId);
	    return ResponseEntity.ok()
	        .header("Content-Disposition", "attachment; filename=\"" + resume.getFileName() + "\"")
	        .contentType(MediaType.parseMediaType(resume.getFileType()))
	        .body(resume.getData());
	}




	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Map<String, String>> deleteResume(@PathVariable Long userId) {
		resumeService.deleteResume(userId);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Resume deleted successfully");
		return ResponseEntity.ok(response);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
	    Map<String, String> error = new HashMap<>();
	    error.put("error", ex.getMessage());
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}


}
