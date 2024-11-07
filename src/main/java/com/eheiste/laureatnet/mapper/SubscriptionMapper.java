package com.eheiste.laureatnet.mapper;

import com.eheiste.laureatnet.dto.ConnectionDTO;
import com.eheiste.laureatnet.dto.SubscriptionDTO;
import com.eheiste.laureatnet.dto.viewmodel.ConnectionCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.SubscriptionCreationDTO;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.Subscription;
import com.eheiste.laureatnet.model.UserAccount;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class SubscriptionMapper {
    public static SubscriptionDTO mapSubscribedToDTO(Subscription subscription, Long userId) {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setPicture(subscription.getSubscriber().getUserProfile().getPicture());
        subscriptionDTO.setFullname(subscription.getSubscriber().getUserProfile().getFirstName() + " " + subscription.getSubscriber().getUserProfile().getLastName());
        subscriptionDTO.setBio(subscription.getSubscriber().getUserProfile().getBio());
        subscriptionDTO.setId(subscription.getId());
        subscriptionDTO.setSubscriberId(subscription.getSubscriber().getId());
        subscriptionDTO.setSubscribedToId(subscription.getSubscribedTo().getId());
        return subscriptionDTO;
    }

    public static SubscriptionDTO mapSubscriberToDTO(Subscription subscription, Long userId) {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setPicture(subscription.getSubscribedTo().getUserProfile().getPicture());
        subscriptionDTO.setFullname(subscription.getSubscribedTo().getUserProfile().getFirstName() + " " + subscription.getSubscribedTo().getUserProfile().getLastName());
        subscriptionDTO.setBio(subscription.getSubscribedTo().getUserProfile().getBio());
        subscriptionDTO.setId(subscription.getId());
        subscriptionDTO.setSubscriberId(subscription.getSubscriber().getId());
        subscriptionDTO.setSubscribedToId(subscription.getSubscribedTo().getId());

        return subscriptionDTO;
    }


    public Subscription mapToEntity(SubscriptionCreationDTO dto) {
        Subscription subscription = new Subscription();
        UserAccount subscriber = new UserAccount();
        UserAccount subscribedTo = new UserAccount();

        subscriber.setId(dto.getSubscriberId());
        subscribedTo.setId(dto.getSubscribedToId());
        subscription.setId(dto.getSubscriptionId());
        subscription.setSubscriber(subscriber);
        subscription.setSubscribedTo(subscribedTo);
        subscription.setCreatedAt(LocalDateTime.now());

        return subscription;
    }
}

