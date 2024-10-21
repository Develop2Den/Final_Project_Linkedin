package com.finalProject.linkedin.service.serviceIR;

import com.finalProject.linkedin.dto.request.notification.NotificationReq;
import com.finalProject.linkedin.dto.responce.notification.NotificationRes;
import com.finalProject.linkedin.entity.Notification;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
   NotificationRes create(NotificationReq notificationReq);

    boolean deleteById(Long id);

    Notification getOne(long id);

    List<NotificationRes> findAll(Pageable pageable);
}
