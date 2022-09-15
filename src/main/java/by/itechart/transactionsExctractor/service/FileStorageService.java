package by.itechart.transactionsExctractor.service;

import by.itechart.transactionsExctractor.entity.FileEntity;
import by.itechart.transactionsExctractor.properties.FileStorageProperties;
import by.itechart.transactionsExctractor.repository.FileRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class FileStorageService {

    private final FileRepository fileRepository;
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties,FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try{
            log.info("Get directory for uploaded file");
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            log.error("Could not create the directory where the uploaded files will be stored.",ex);
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex); //TODO custom exception
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            saveFileToBD(file);
            // Check if the file's name contains invalid characters
            isValidPathName(fileName);
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("file saved in folder");
            return fileName;
        } catch (IOException ex) {
            log.error("Could not store file " + fileName + ". Please try again!",ex);
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex); //TODO custom exception
        }

    }

    private boolean isValidPathName(String fileName) {
        if (fileName.contains("..")) {
            log.error("Sorry! Filename contains invalid path sequence " + fileName);
            throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName); //TODO custom exception
        }
        return fileName.contains("..") ;
    }

    private void saveFileToBD(MultipartFile file) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileEntity.setContentType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setSize(file.getSize());
        fileRepository.save(fileEntity);
        log.info("file saved in DB");
    }

    public FileEntity getFile(String id) {
        return fileRepository.findById(id).get();
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }
}
