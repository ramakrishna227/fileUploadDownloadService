package com.example.fileUploadDownloadService.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileUploadDownloadService.exception.FileStorageException;
import com.example.fileUploadDownloadService.exception.MyFileNotFoundException;
import com.example.fileUploadDownloadService.properties.FileStorageProperties;

@Service
public class FileStorageService {
  private final Path fileStorageLocation;

  @Autowired
  public FileStorageService(FileStorageProperties fileStorageProperties) {
    this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    try {
      Files.createDirectories(this.fileStorageLocation);
    } catch (IOException e) {
      throw new FileStorageException("Could not create a directory to store uploaded files", e);
    }
  }

  public String storeFile(MultipartFile file) {
    //Normalize filename
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      if (fileName.contains("..")) {
        throw new FileStorageException("File name contains invalid path sequence " + fileName);
      }

      Path targetLocation = this.fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return fileName;
    } catch (IOException e) {
      throw new FileStorageException("Could not store file " + fileName + " Please try again", e);
    }
  }

  public Resource loadFileAsResource(String fileName) {
    try {
      Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new MyFileNotFoundException("File not found " + fileName);
      }

    } catch (MalformedURLException ex) {
      throw new MyFileNotFoundException("File not found ", ex);
    }
  }

}
