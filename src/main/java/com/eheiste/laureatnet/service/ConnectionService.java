package com.eheiste.laureatnet.service;
import com.eheiste.laureatnet.model.Connection;

import java.util.Collection;

public interface ConnectionService {

    Collection<Connection> getAllConnections();

    Connection findConnectionById(Long id);
    Collection<Connection> findConnectionsById(Long id);
    Collection<Connection> getAcceptedConnectionsByUserId(Long id);
    Collection<Connection> getPendingConnectionsByReceiverId(Long id);
    Collection<Connection> getPendingConnectionsBySenderId(Long id);
    void deleteConnectionById(Long id);

    Connection saveConnection(Connection connection);
}

