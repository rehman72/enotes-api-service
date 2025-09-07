package com.project.enotes_api_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.enotes_api_service.Exception.ExtensionNotAllowedException;
import com.project.enotes_api_service.Exception.ResourceNotFoundException;
import com.project.enotes_api_service.dto.NotesDto;
import com.project.enotes_api_service.dto.NotesResponseDto;
import com.project.enotes_api_service.entity.FileDetails;
import com.project.enotes_api_service.entity.Notes;
import com.project.enotes_api_service.repository.FileRepository;
import com.project.enotes_api_service.repository.NotesRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

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
//        Object Mapper for to Convert Json to Java Object
        ObjectMapper objectMapper = new ObjectMapper();
//        Reading Values of json and mapping the fields to Java Class Object
        NotesDto notesDto = objectMapper.readValue(notes, NotesDto.class);

        notesDto.setIsDeleted(false);
        notesDto.setDeletedOn(null);
//       update notes if id is given in the request body
        if(!ObjectUtils.isEmpty(notesDto.getId())){
            updateNotes(notesDto,file);
        }
//         if the file is not Empty then the file details are saved in DB || it will get NULL
        FileDetails fileDetail= saveFileDetails(file);
//        map the values of notesdto to Real Enitites of Notes
        Notes notesMap = modelMapper.map(notesDto, Notes.class);
//        check Category Id is present or Not in Category Table
        categoryService.getCategoryById(notesMap.getCategory().getId());
//        if the File Details Object is not Null then set FileDetails values in Notes Object
        if(!ObjectUtils.isEmpty(fileDetail)){
            notesMap.setFileDetails(fileDetail);
        }else {
//          The File is not Passed.
            if(ObjectUtils.isEmpty(notesDto.getId())){
                notesMap.setFileDetails(null);
            }
        }

        Notes saveNotes = notesRepository.save(notesMap);
        if(!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }
        return false;
    }

    private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
//        First Check if the notes id which is pass it exist in notes table or not
        Notes notes = notesRepository.findById(notesDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Notes Not found with this id"));
//      if the user has not provided File then fetch fileDetails from DB for the corresponding notes id
//      set the notesDto as fileDetails
        if(ObjectUtils.isEmpty(file)){
            notesDto.setFileDetails(modelMapper.map(notes.getFileDetails(), NotesDto.FileDetailsDto.class));
        }
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
            List<String> allowedExtension=Arrays.asList("png","jpg","pdf","txt");

            if(!allowedExtension.contains(extension)){
                throw new ExtensionNotAllowedException("Only png,jpg,pdf file are allowed!");
            }
            File saveFile=new File(uploadPath);
            if(!saveFile.exists()){
                saveFile.mkdir();
            }
            String uploadFileName = UUID.randomUUID().toString();
            String storePath = uploadPath.concat(uploadFileName+"."+extension);
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
        String extension = FilenameUtils.getExtension(originalFilename);
        String fileWithoutExtension = FilenameUtils.removeExtension(originalFilename);
        if(fileWithoutExtension.length()>8){
            fileWithoutExtension=fileWithoutExtension.substring(0, Math.min(fileWithoutExtension.length(),10));
            fileWithoutExtension=fileWithoutExtension+"."+extension;
        }else{
            fileWithoutExtension+="."+extension;
        }
        return fileWithoutExtension;
    }

    @Override
    public byte[] downloadFile(FileDetails fileDetail) throws Exception {
        InputStream io=new FileInputStream(fileDetail.getPath());
       return StreamUtils.copyToByteArray(io);
    }

    @Override
    public FileDetails getFileDetails(Integer id) throws Exception {
       return fileRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("File Details Not Found with id"+id));
    }

    @Override
        public NotesResponseDto getAllNotesByUser(Integer userId,Integer pageNo,Integer pageSize)  throws Exception{
        Pageable pageRequest= PageRequest.of(pageNo,pageSize);
        Page<Notes> notes = notesRepository.findByCreatedByAndIsDeletedFalse(userId,pageRequest);
        if(notes.isEmpty()){
            throw new Exception("The User has no Notes");
        }
        List<NotesDto> notesDtoList = notes.get()
                .map(n -> modelMapper.map(n, NotesDto.class)).toList();

        return NotesResponseDto.builder()
                .notes(notesDtoList)
                .pageNo(pageRequest.getPageNumber())
                .pageSize(pageRequest.getPageSize())
                .totalElements(notes.getTotalElements())
                .totalPages(notes.getTotalPages())
                .isFirst(notes.isFirst())
                .isLast(notes.isLast())
                .build();
    }

    @Override
    public void softDeleteNotes(Integer id)  throws Exception{
        Notes notes = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notes id Invalid!Not Found"));
        notes.setIsDeleted(true);
        notes.setDeletedOn(LocalDateTime.now());
        notesRepository.save(notes);
    }

    @Override
    public void restoreNotes(Integer id) throws  Exception {
        Notes notes = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notes id Invalid! Not Found"));

        notes.setIsDeleted(false);
        notes.setDeletedOn(null);
        notesRepository.save(notes);
    }

    @Override
    public List<NotesDto> getUserRecycleBin(Integer userId) {
        List<Notes> notesList = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
        return notesList.stream()
                .map(notes -> modelMapper.map(notes, NotesDto.class))
                .toList();
    }

    @Override
    public void hardDeleteNotes(Integer id) throws  Exception {
        Notes notes = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notes with this id not Found!"));
        if(notes.getIsDeleted()){
            notesRepository.delete(notes);
        }else{
            throw new IllegalArgumentException("You cannot Hard delete Directly");
        }
    }

    @Override
    public void emptyRecycleBin(Integer userId)  throws Exception{
        List<Notes> deletedNotes = notesRepository.findAllByCreatedByAndIsDeletedTrue((userId));

        if(!deletedNotes.isEmpty()){
            List<Integer> list = deletedNotes.stream()
                    .map(Notes::getId)
                    .toList();
            notesRepository.deleteAllById(list);
        }
        else{
            throw new Exception("The User has no Notes in Recycle Bin");
        }

    }
}
