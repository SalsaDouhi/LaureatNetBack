package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.Conversation;
import com.eheiste.laureatnet.repository.ConnectionRepository;
import com.eheiste.laureatnet.repository.ConversationRepository;
import com.eheiste.laureatnet.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private ConversationRepository conversationRepository;

    @Override
    public Collection<Connection> getAllConnections() {
        return connectionRepository.findAll();
    }

    @Override
    public Connection findConnectionById(Long id) {
        Optional<Connection> connectionOptional = connectionRepository.findById(id);
        return connectionOptional.orElse(null);
    }

    @Override
    public Collection<Connection> findConnectionsById(Long id) {
        Collection<Connection> connections = connectionRepository.findAllBySenderIdOrReceiverId(id);
        return connections;
    }

    @Override
    public Collection<Connection> getAcceptedConnectionsByUserId(Long id) {
        Collection<Connection> acceptedConnections = connectionRepository.findConnectionBySenderIdOrReceiverId(id);
        return acceptedConnections;
    }

    @Override
    public Collection<Connection> getPendingConnectionsByReceiverId(Long id) {
        Collection<Connection> pendingConnections = connectionRepository.findPendingConnectionByReceiverId(id);
        return pendingConnections;
    }

    @Override
    public Collection<Connection> getPendingConnectionsBySenderId(Long id) {
        Collection<Connection> pendingConnections = connectionRepository.findPendingConnectionBySenderId(id);
        return pendingConnections;
    }

    @Override
    public void deleteConnectionById(Long id) {
        connectionRepository.deleteById(id);
    }

    @Override
    public Connection saveConnection(Connection connection) {
        // create a conversation if it doesn't already exist
        Optional<Conversation> optionalConversation = conversationRepository.findConversationByUsers(connection.getSender(), connection.getReceiver());
        if (optionalConversation.isEmpty()) {
            conversationRepository.save(Conversation.builder().user1(connection.getSender()).user2(connection.getReceiver()).build());
        }

        return connectionRepository.save(connection);
    }
}

