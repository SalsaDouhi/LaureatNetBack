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
public class BlockNotification extends Notification {

    @OneToOne
    @JoinColumn(name = "referenced_block_id")
    private Block referencedBlock;

}