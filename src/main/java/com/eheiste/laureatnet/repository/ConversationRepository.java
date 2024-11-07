package com.eheiste.laureatnet.repository;


import com.eheiste.laureatnet.dto.chat.ConversationResponse;
import com.eheiste.laureatnet.model.Conversation;
import com.eheiste.laureatnet.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c WHERE (c.user1 = :user1 AND c.user2 = :user2) OR (c.user1 = :user2 AND c.user2 = :user1)")
    Optional<Conversation> findConversationByUsers(@Param("user1") UserAccount user1, @Param("user2") UserAccount user2);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT
                    	C.id AS conversationId,
                    	U.id AS otherUserId,
                    	UP.picture AS picture,
                    	CONCAT(UP.first_name, ' ', UP.last_name) AS otherUserName,
                    	M.content AS lastMessage,
                    	M.created_at AS lastMessageTimestamp
                    FROM conversation AS C

                    INNER JOIN user_Account AS U
                    ON (C.user1_id = U.id OR C.user2_id = U.id) AND U.id != ?1
                    INNER JOIN user_profile AS UP
                    ON U.id = UP.user_account_id
                    							
                    LEFT JOIN (
                    	SELECT
                    		M.conversation_id as conversationId,
                    		(
                    			SELECT content FROM message M2
                    			WHERE M2.conversation_id = M.conversation_id
                    			ORDER BY M2.created_at DESC LIMIT 1
                    		) AS content,
                    		MAX(created_at) AS created_at
                    	FROM message M
                    	GROUP BY conversationId
                    ) AS M
                    ON C.id = M.conversationId
                    
                    WHERE C.user1_id = ?1 OR C.user2_id = ?1
                    ORDER BY M.created_at DESC;
                    """
    )
    List<ConversationResponse> findConversationsByUserAccountId(Long userId);
}