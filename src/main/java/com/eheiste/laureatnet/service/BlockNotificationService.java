package com.eheiste.laureatnet.service;

import java.util.Collection;

import com.eheiste.laureatnet.model.BlockNotification;

public interface BlockNotificationService {

    Collection<BlockNotification> getAllBlockNotifications();

    BlockNotification findBlockNotificationById(Long id);

    void deleteBlockNotificationById(Long id);

    BlockNotification saveBlockNotification(BlockNotification blockNotification);

    BlockNotification updateBlockNotification(Long id, BlockNotification updatedBlockNotification);
}