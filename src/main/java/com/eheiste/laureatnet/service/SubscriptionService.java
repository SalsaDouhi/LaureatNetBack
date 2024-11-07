package com.eheiste.laureatnet.service;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.Subscription;

import java.util.Collection;

public interface SubscriptionService {

    Collection<Subscription> getAllSubscriptions();
    Collection<Subscription> findSubscriptionsById(Long id);
    Collection<Subscription> findSubscribedToById(Long id);
    Subscription findSubscriptionById(Long id);
    void deleteSubscriptionById(Long id);

    Subscription saveSubscription(Subscription subscription);
}
