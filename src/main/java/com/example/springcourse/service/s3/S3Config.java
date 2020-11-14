package com.example.springcourse.service.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

	@Value("${app.aws.s3.bucket-name}")
	private String bucketName;

	@Value("${app.aws.s3.access-key}")
	private String accessKey;

	@Value("${app.aws.s3.secret-key}")
	private String secretKey;

	@Bean("amazonS3")
	public AmazonS3 getAmazonS3() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

		return AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.US_EAST_2)
				.build();

	}

	@Bean("amazonS3Region")
	public String getRegion() {
		return Region.getRegion(Regions.US_EAST_2).getName();
	}

	@Bean("amazonS3Bucket")
	public String getBucket() {
		return bucketName;
	}

}
