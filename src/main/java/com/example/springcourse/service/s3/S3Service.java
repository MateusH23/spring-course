package com.example.springcourse.service.s3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.springcourse.model.UploadedFileModel;

@Service
public class S3Service {
	
	private AmazonS3 s3;
	private String region;
	private String bucketName;
	
	@Autowired
	public S3Service(AmazonS3 amazonS3, String amazonS3Region, String amazonS3Bucket) {
		s3 = amazonS3;
		region = amazonS3Region;
		bucketName = amazonS3Bucket;
	}
	
	public List<UploadedFileModel> upload(MultipartFile[] files) {
		
		List<UploadedFileModel> uploadedFiles = new ArrayList<>();
		
		for (MultipartFile file : files) {
			String originalName = file.getOriginalFilename();
			String s3FileName = getUniqueFileName(originalName);
			
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			
			try {
				PutObjectRequest request = new PutObjectRequest(bucketName, originalName, file.getInputStream(), metadata)
													.withCannedAcl(CannedAccessControlList.PublicRead);
				
				s3.putObject(request);
				
				String location = getFileLocation(s3FileName);
				
				UploadedFileModel fileModel = new UploadedFileModel(originalName, location);
				
				uploadedFiles.add(fileModel);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return uploadedFiles;
	}
	
	private String getFileLocation(String fileName) {
		return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
	}
	
	private String getUniqueFileName(String fileName) {
		return UUID.randomUUID().toString() + fileName;
	}

}
