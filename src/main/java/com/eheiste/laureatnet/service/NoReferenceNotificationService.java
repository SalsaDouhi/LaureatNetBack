package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.NoReferenceNotification;
import java.util.List;

public interface NoReferenceNotificationService {
    public List<NoReferenceNotification> getAllNoReferenceNotifications();

    public NoReferenceNotification getNoReferenceNotificationById(Long id);

    public NoReferenceNotification saveNoReferenceNotification(NoReferenceNotification notification);

    public void deleteNoReferenceNotification(Long id);
}
