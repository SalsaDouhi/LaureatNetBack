package com.eheiste.laureatnet.repository;
import com.eheiste.laureatnet.model.Connection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    @Query("Select c from Connection c where (c.sender.id = ?1 or c.receiver.id = ?1) and c.accepted = true")
    public Collection<Connection> findConnectionBySenderIdOrReceiverId(Long id);

    @Query("Select c from Connection c where (c.sender.id = ?1 or c.receiver.id = ?1)")
    public Collection<Connection> findAllBySenderIdOrReceiverId(Long id);

    @Query("SELECT c from Connection c where c.accepted = false and c.receiver.id = ?1")
    public Collection<Connection> findPendingConnectionByReceiverId(Long id);

    @Query("SELECT c from Connection c where c.accepted = false and c.sender.id = ?1")
    public Collection<Connection> findPendingConnectionBySenderId(Long id);

    @Query("SELECT c FROM Connection c WHERE c.sender.id = :userId OR c.receiver.id = :userId")
    List<Connection> findBySenderIdOrReceiverId(Long userId);
}
