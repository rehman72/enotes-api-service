package com.project.enotes_api_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.enotes_api_service.Exception.ExtensionNotAllowedException;
import com.project.enotes_api_service.dto.NotesDto;
import com.project.enotes_api_service.entity.FileDetails;
import com.project.enotes_api_service.entity.Notes;
import com.project.enotes_api_service.repository.FileRepository;
import com.project.enotes_api_service.repository.NotesRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public  class NotesServiceImpl implements NotesService{

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileRepository fileRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean savedNotes(String  notes,MultipartFile file) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        NotesDto notesDto = objectMapper.readValue(notes, NotesDto.class);
        FileDetails fileDetail= saveFileDetails(file);
        Notes notesMap = modelMapper.map(notesDto, Notes.class);
        categoryService.getCategoryById(notesMap.getCategory().getId());
        if(!ObjectUtils.isEmpty(fileDetail)){
            notesMap.setFileDetails(fileDetail);
        }

        Notes save = notesRepository.save(notesMap);
        if(!ObjectUtils.isEmpty(save)){
            return true;
        }else {
            notesMap.setFileDetails(null);
        }
        return false;
    }

    @Override
    public List<NotesDto> geAllNotes() {
       return notesRepository.findAll()
                .stream()
                .map(notes->modelMapper.map(notes,NotesDto.class))
                .toList();
    }

    public FileDetails saveFileDetails(MultipartFile file) throws Exception {
        if(!ObjectUtils.isEmpty(file) && !file.isEmpty()){
            Boolean exists = fileRepository.existsByOriginalFileName(file.getOriginalFilename());
            if(exists) {
                throw new Exception("File Already Exists");
            }

            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
            List<String> allowedExtension=Arrays.asList("png","jpg","png");

            if(!allowedExtension.contains(extension)){
                throw new ExtensionNotAllowedException("Only png,jpg,pdf file are allowed!");
            }
            File saveFile=new File(uploadPath);
            boolean directoryCreated=false;
            if(!saveFile.exists()){
                directoryCreated=saveFile.mkdir();
            }
            String uploadFileName = UUID.randomUUID().toString();
            String storePath = uploadPath.concat(uploadFileName);
            long upload=Files.copy(file.getInputStream(),Paths.get(storePath));
            log.info("File Uploaded Successfully {}",upload);
            if(upload!=0){
                FileDetails fileDetails = new FileDetails();
                fileDetails.setOriginalFileName(originalFilename);
                fileDetails.setDisplayFileName(getDisplayName(originalFilename));
                uploadFileName=uploadFileName+"."+extension;
                fileDetails.setUploadFileName(uploadFileName);
                fileDetails.setFileSize(file.getSize());
                fileDetails.setPath(storePath);
               return fileRepository.save(fileDetails);
            }

        }
        return null;
    }

    private String getDisplayName(String originalFilename) {
//        Java_programming_tutorial.pdf
        String fileextension = FilenameUtils.getExtension(originalFilename);
        String fileWithoutExtension = FilenameUtils.removeExtension(originalFilename);
        if(fileWithoutExtension.length()>8){ //true
//            Java_progra.pdf
            fileWithoutExtension=fileWithoutExtension.substring(0, 10);
            fileWithoutExtension=fileWithoutExtension+"."+fileextension;
        }else{
            fileWithoutExtension+="."+fileextension;
        }
        return fileWithoutExtension;
    }


}
