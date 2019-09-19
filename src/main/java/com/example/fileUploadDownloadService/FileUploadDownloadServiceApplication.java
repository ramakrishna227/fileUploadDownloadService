package com.example.fileUploadDownloadService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.fileUploadDownloadService.properties.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class FileUploadDownloadServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploadDownloadServiceApplication.class, args);
	}

}
