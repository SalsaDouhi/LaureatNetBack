package com.eheiste.laureatnet.service;

import java.util.Collection;

import com.eheiste.laureatnet.model.ConnectionNotification;

public interface ConnectionNotificationService {

    Collection<ConnectionNotification> getAllConnectionNotifications();

    ConnectionNotification findConnectionNotificationById(Long id);

    void deleteConnectionNotificationById(Long id);

    ConnectionNotification saveConnectionNotification(ConnectionNotification connectionNotification);

    ConnectionNotification updateConnectionNotification(Long id, ConnectionNotification updatedConnectionNotification);
}

