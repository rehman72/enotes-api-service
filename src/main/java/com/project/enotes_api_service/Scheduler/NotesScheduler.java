package com.project.enotes_api_service.Scheduler;

import com.project.enotes_api_service.entity.Notes;
import com.project.enotes_api_service.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesScheduler {

    @Autowired
    private NotesRepository notesRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public  void deleteNotesScheduler(){
        LocalDateTime cutOffDay = LocalDateTime.now().minusDays(7);
        List<Notes> allExpiredNotes = notesRepository.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDay);
        notesRepository.deleteAll(allExpiredNotes);
    }

}
