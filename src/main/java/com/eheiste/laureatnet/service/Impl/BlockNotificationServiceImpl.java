package com.eheiste.laureatnet.service.Impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eheiste.laureatnet.model.BlockNotification;
import com.eheiste.laureatnet.repository.BlockNotificationRepository;
import com.eheiste.laureatnet.service.BlockNotificationService;

@Service
public class BlockNotificationServiceImpl implements BlockNotificationService {

    @Autowired
    private BlockNotificationRepository blockNotificationRepository;

    @Override
    public Collection<BlockNotification> getAllBlockNotifications() {
        return blockNotificationRepository.findAll();
    }

    @Override
    public BlockNotification findBlockNotificationById(Long id) {
        Optional<BlockNotification> blockNotificationOptional = blockNotificationRepository.findById(id);
        return blockNotificationOptional.orElse(null);
    }

    @Override
    public void deleteBlockNotificationById(Long id) {
        blockNotificationRepository.deleteById(id);
    }

    @Override
    public BlockNotification saveBlockNotification(BlockNotification blockNotification) {
        return blockNotificationRepository.save(blockNotification);
    }

    @Override
    public BlockNotification updateBlockNotification(Long id, BlockNotification updatedBlockNotification) {
        BlockNotification existingBlockNotification = findBlockNotificationById(id);
        if (existingBlockNotification != null) {
            existingBlockNotification.setContent(updatedBlockNotification.getContent());
            existingBlockNotification.setRead(updatedBlockNotification.isRead());
            existingBlockNotification.setReadAt(updatedBlockNotification.getReadAt());
            existingBlockNotification.setUser(updatedBlockNotification.getUser());
            existingBlockNotification.setReferencedBlock(updatedBlockNotification.getReferencedBlock());
            return blockNotificationRepository.save(existingBlockNotification);
        }
        return null;
    }
}
