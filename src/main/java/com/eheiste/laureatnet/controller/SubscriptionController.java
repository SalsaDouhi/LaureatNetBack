package com.eheiste.laureatnet.controller;
import com.eheiste.laureatnet.dto.ConnectionDTO;
import com.eheiste.laureatnet.dto.SubscriptionDTO;
import com.eheiste.laureatnet.dto.viewmodel.ConnectionCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.SubscriptionCreationDTO;
import com.eheiste.laureatnet.mapper.ConnectionMapper;
import com.eheiste.laureatnet.mapper.SubscriptionMapper;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.Subscription;
import com.eheiste.laureatnet.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @GetMapping
    public Collection<Subscription> getAllSubscriptions(){
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Collection<SubscriptionDTO>> getSubscriptionsByUserId(@PathVariable Long id) {
        Collection<Subscription> subscriptions = subscriptionService.findSubscriptionsById(id);
        if (subscriptions != null && !subscriptions.isEmpty()) {
            Collection<SubscriptionDTO> subscriptionDTOS = new ArrayList<>();
            for (Subscription subscription : subscriptions) {
                SubscriptionDTO subscriptionDTO = SubscriptionMapper.mapSubscriberToDTO(subscription, id);
                subscriptionDTOS.add(subscriptionDTO);
            }
            return ResponseEntity.ok(subscriptionDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/subscribedTo/{id}")
    public ResponseEntity<Collection<SubscriptionDTO>> getSubscribedToListByUserId(@PathVariable Long id) {
        Collection<Subscription> subscriptions = subscriptionService.findSubscribedToById(id);
        if (subscriptions != null && !subscriptions.isEmpty()) {
            Collection<SubscriptionDTO> subscriptionDTOS = new ArrayList<>();
            for (Subscription subscription : subscriptions) {
                SubscriptionDTO subscriptionDTO = SubscriptionMapper.mapSubscribedToDTO(subscription, id);
                subscriptionDTOS.add(subscriptionDTO);
            }
            System.out.println(subscriptionDTOS);
            return ResponseEntity.ok(subscriptionDTOS);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable long id){
        Subscription subscription = subscriptionService.findSubscriptionById(id);
        if (subscription != null) {
            return ResponseEntity.ok(subscription);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSubscription(@PathVariable long id){
        subscriptionService.deleteSubscriptionById(id);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionCreationDTO subscriptionDto) {
        Subscription subscription = subscriptionMapper.mapToEntity(subscriptionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.saveSubscription(subscription));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable long id, @RequestBody Subscription subscription){
        Subscription existingSubscription = subscriptionService.findSubscriptionById(id);
        if (existingSubscription != null) {
            existingSubscription.setSubscriber(subscription.getSubscriber());
            existingSubscription.setSubscribedTo(subscription.getSubscribedTo());
            existingSubscription.setAccepted(subscription.isAccepted());
            subscriptionService.saveSubscription(existingSubscription);
            return ResponseEntity.ok(existingSubscription);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
