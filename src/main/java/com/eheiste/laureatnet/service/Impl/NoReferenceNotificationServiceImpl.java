package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.NoReferenceNotification;
import com.eheiste.laureatnet.repository.NoReferenceNotificationRepository;
import com.eheiste.laureatnet.service.NoReferenceNotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoReferenceNotificationServiceImpl implements NoReferenceNotificationService {
    private final NoReferenceNotificationRepository noReferenceNotificationRepository;
    public NoReferenceNotificationServiceImpl (NoReferenceNotificationRepository noReferenceNotificationRepository)
    {
        this.noReferenceNotificationRepository = noReferenceNotificationRepository;
    }

    public List<NoReferenceNotification> getAllNoReferenceNotifications() {
        return noReferenceNotificationRepository.findAll();
    }

    public NoReferenceNotification getNoReferenceNotificationById(Long id) {
        return noReferenceNotificationRepository.findById(id).orElse(null);
    }

    public NoReferenceNotification saveNoReferenceNotification(NoReferenceNotification notification) {
        return noReferenceNotificationRepository.save(notification);
    }

    public void deleteNoReferenceNotification(Long id) {
        noReferenceNotificationRepository.deleteById(id);
    }
}
