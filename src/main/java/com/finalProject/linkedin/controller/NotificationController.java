package com.finalProject.linkedin.controller;


import com.finalProject.linkedin.dto.request.notification.NotificationReq;
import com.finalProject.linkedin.dto.responce.notification.NotificationRes;
import com.finalProject.linkedin.service.serviceIR.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor

public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/list")
    public List<NotificationRes> getAllNotification(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        log.info("Pageable request chats: page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return notificationService.findAll(pageable);
    }


    @PostMapping("/create")
    public ResponseEntity<NotificationRes> createNotification(@Valid @RequestBody NotificationReq notificationReq) {
        return ResponseEntity.ok(notificationService.create(notificationReq));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable long id) {
        if (notificationService.deleteById(id)) return ResponseEntity.ok().build();
        else return ResponseEntity.notFound().build();

    }

}
