package com.eheiste.laureatnet.service.Impl;

import java.util.Collection;

import com.eheiste.laureatnet.service.ConnectionNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eheiste.laureatnet.model.ConnectionNotification;
import com.eheiste.laureatnet.repository.ConnectionNotificationRepository;

@Service
public class ConnectionNotificationServiceImpl implements ConnectionNotificationService {

    @Autowired
    private ConnectionNotificationRepository connectionNotificationRepository;

    @Override
    public Collection<ConnectionNotification> getAllConnectionNotifications() {
        return connectionNotificationRepository.findAll();
    }

    @Override
    public ConnectionNotification findConnectionNotificationById(Long id) {
        return connectionNotificationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteConnectionNotificationById(Long id) {
        connectionNotificationRepository.deleteById(id);
    }

    @Override
    public ConnectionNotification saveConnectionNotification(ConnectionNotification connectionNotification) {
        return connectionNotificationRepository.save(connectionNotification);
    }

    @Override
    public ConnectionNotification updateConnectionNotification(Long id, ConnectionNotification updatedConnectionNotification) {
        ConnectionNotification existingConnectionNotification = findConnectionNotificationById(id);
        if (existingConnectionNotification != null) {
            // Update the existing connection notification
            existingConnectionNotification.setContent(updatedConnectionNotification.getContent());
            existingConnectionNotification.setRead(updatedConnectionNotification.isRead());
            existingConnectionNotification.setReadAt(updatedConnectionNotification.getReadAt());
            existingConnectionNotification.setUser(updatedConnectionNotification.getUser());
            existingConnectionNotification.setReferencedConnection(updatedConnectionNotification.getReferencedConnection());
            // Save the updated connection notification
            return connectionNotificationRepository.save(existingConnectionNotification);
        }
        return null; // Or throw an exception if not found
    }
}
