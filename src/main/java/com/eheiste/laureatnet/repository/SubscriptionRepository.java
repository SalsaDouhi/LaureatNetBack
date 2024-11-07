package com.eheiste.laureatnet.repository;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("Select s from Subscription s where s.subscriber.id = ?1")
    public Collection<Subscription> findAllBySubscriberId(Long id);

    @Query("Select s from Subscription s where s.subscribedTo.id = ?1")
    public Collection<Subscription> findAllBySubscribedToId(Long id);
}
