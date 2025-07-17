package com.example.demo.serviceImpl;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Resume;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.ResumeRepository;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.ResumeService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

	private final ResumeRepository resumeRepository;
	private final UserRepo userRepository;

	@Override
	public void uploadResume(MultipartFile file, Long userId) {
		try {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found"));

			Resume resume = resumeRepository.findByUser(user).orElse(new Resume());
			resume.setFileName(file.getOriginalFilename());
			resume.setFileType(file.getContentType());
			resume.setData(file.getBytes());
			resume.setUser(user);

			resumeRepository.save(resume);
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload resume", e);
		}
	}

	@Override
	public Resume getResumeByUserId(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		return resumeRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
	}

	@Override
	public void deleteResume(Long userId) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
	    Resume resume = resumeRepository.findByUser(user)
	            .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
	    resumeRepository.delete(resume);
	}



}
