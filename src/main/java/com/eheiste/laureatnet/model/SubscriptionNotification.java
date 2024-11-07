package com.eheiste.laureatnet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionNotification extends Notification {

	@OneToOne
    @JoinColumn(name = "referenced_subscription_id")
    private Subscription referencedSubscription;

}