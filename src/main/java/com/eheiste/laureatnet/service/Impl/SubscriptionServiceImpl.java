package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.Subscription;
import com.eheiste.laureatnet.repository.SubscriptionRepository;
import com.eheiste.laureatnet.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Collection<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Collection<Subscription> findSubscriptionsById(Long id) {
        Collection<Subscription> subscriptions = subscriptionRepository.findAllBySubscriberId(id);
        return subscriptions;
    }

    @Override
    public Collection<Subscription> findSubscribedToById(Long id) {
        Collection<Subscription> subscriptions = subscriptionRepository.findAllBySubscribedToId(id);
        return subscriptions;
    }

    @Override
    public Subscription findSubscriptionById(Long id) {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(id);
        return subscriptionOptional.orElse(null);
    }

    @Override
    public void deleteSubscriptionById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public Subscription saveSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
}
