package com.example.fileUploadDownloadService.payload;

public class UploadFileResponse {
  private String fileName;
  private String fileDownloadURI;
  private String fileType;
  private long size;

  public UploadFileResponse(String fileName, String fileDownloadURI, String fileType, long size) {
    this.fileName = fileName;
    this.fileDownloadURI = fileDownloadURI;
    this.fileType = fileType;
    this.size = size;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileDownloadURI() {
    return fileDownloadURI;
  }

  public void setFileDownloadURI(String fileDownloadURI) {
    this.fileDownloadURI = fileDownloadURI;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

}