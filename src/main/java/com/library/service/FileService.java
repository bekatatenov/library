package com.library.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Credit to FrontBackend - https://frontbackend.com/spring-boot/spring-boot-upload-file-to-filesystem

@Slf4j
@Service
public class FileService {

  @Value("${upload.path}")
  private String uploadPath;
  private final Map<String, String> uploadPaths = new HashMap<>();

  private static final String ROOT = "root";
  private static final String ACCOUNT = "account";
  private static final String COVER = "cover";
  private static final String READER = "reader";

  @PostConstruct
  public void init() {
    try {
      uploadPaths.put(ROOT, uploadPath);
      uploadPaths.put(ACCOUNT, uploadPath + "/accounts");
      uploadPaths.put(COVER, uploadPath + "/covers");
      uploadPaths.put(READER, uploadPath + "/readers");

      if (!uploadPath.contains("://")) {
        Files.createDirectories(Paths.get(uploadPaths.get(ROOT)));
        Files.createDirectories(Paths.get(uploadPaths.get(ACCOUNT)));
        Files.createDirectories(Paths.get(uploadPaths.get(COVER)));
        Files.createDirectories(Paths.get(uploadPaths.get(READER)));
      }
    }
    catch (IOException e) {
      throw new RuntimeException("Could not create upload folders!");
    }
  }

  private String getDirectoryString(String filename) {
    String path;

    if (filename.contains("-")) {
      String key = filename.replaceAll("-.*", "");
      path = uploadPaths.get(key);
    }
    else {
      path = uploadPaths.get(filename);
    }

    if (path == null) {
      path = uploadPaths.get(ROOT);
    }

    return path;
  }

  private Path getDirectoryPath(String filename) {
    Path path;
    String directory = getDirectoryString(filename);

    if (uploadPath.contains("://")) {
      path = Paths.get(URI.create(directory));
    }
    else {
      path = Paths.get(directory);
    }

    return path;
  }

  public void save(MultipartFile file) {
    saveAs(file, file.getOriginalFilename());
  }

  public void saveAs(MultipartFile file, String filename) {
    try {
      Path path = getDirectoryPath(filename);
      log.info("Saving to " + path);

      if (!Files.exists(path)) {
        init();
      }

      Files.copy(file.getInputStream(),
                 path.resolve(filename),
                 StandardCopyOption.REPLACE_EXISTING);
    }
    catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }

  public Resource load(String filename) {
    try {
      Path path = getDirectoryPath(filename).resolve(filename);
      log.info("Loading " + path.toUri());
      Resource resource = new PathResource(path.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      }
      else {
        throw new RuntimeException("Could not read the file!");
      }
    }
    catch (Exception e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  public boolean exists(String filename) {
    try {
      load(filename);
      return true;
    }
    catch (Exception e) {
      log.trace(e.toString());
      return false;
    }
  }

  public void deleteAll() {
    deleteAll(ROOT);
  }

  public void deleteAll(String directory) {
    Path path = getDirectoryPath(directory);
    log.info("Deleting all from " + path);
    FileSystemUtils.deleteRecursively(path.toFile());
  }

  public List<Path> loadAll() {
    return loadAll(ROOT);
  }

  public List<Path> loadAll(String directory) {
    try {
      Path root = getDirectoryPath(directory);
      log.info("Loading all from " + root);

      if (Files.exists(root)) {
        return Files.walk(root, 1)
                    .filter(path -> !path.equals(root))
                    .collect(Collectors.toList());
      }

      return Collections.emptyList();
    }
    catch (IOException e) {
      throw new RuntimeException("Could not list the files!");
    }
  }

}
