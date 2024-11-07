package com.eheiste.laureatnet.service;

import com.eheiste.laureatnet.model.Block;

import java.util.Collection;

public interface BlockService {

    Collection<Block> getAllBlocks();
    Collection<Block> getBlocksByUserId(Long id);
    Block findBlockById(Long id);
    boolean deleteBlockById(Long id);
    Block saveBlock(Block block);
    boolean checkPassword(Long userId, String password);}
