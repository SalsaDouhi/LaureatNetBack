package com.eheiste.laureatnet.dto.chat;

import java.sql.Timestamp;

public interface ConversationResponse {
    Integer getConversationId();

    Integer getOtherUserId();

    String getOtherUserName();

    String getLastMessage();

    String getPicture();

    Timestamp getLastMessageTimestamp();
}