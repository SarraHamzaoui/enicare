package com.sarliftou.enicare.service;

import com.sarliftou.enicare.entities.Notification;
import com.sarliftou.enicare.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByEtudiant(Long etudiantId) {
        return notificationRepository.findByEtudiantId(etudiantId);
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification markAsRead(Long id) {
        Optional<Notification> notif = notificationRepository.findById(id);
        if (notif.isPresent()) {
            Notification notification = notif.get();
            notification.setLue(true);
            return notificationRepository.save(notification);
        }
        return null;
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
