package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.Conversation;
import com.eheiste.laureatnet.model.Message;
import com.eheiste.laureatnet.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderId(Long senderId);

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) OR (m.sender.id = :userId2 AND m.receiver.id = :userId1) ORDER BY m.createdAt ASC")
    Page<Message> findMessagesBetweenUsers(Long userId1, Long userId2, Pageable pageable);

    List<Message> findBySenderAndReceiver(UserAccount sender, UserAccount receiver);

    List<Message> findByReceiver(UserAccount receiver);


    //    new
    List<Message> findAllByConversationOrderByCreatedAtDesc(Conversation conversation);

    void deleteAllByConversation(Conversation conversation);
}
