package com.eheiste.laureatnet.repository;
import com.eheiste.laureatnet.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    Collection<Block> findBlocksByBlockerId(Long userId);
}
