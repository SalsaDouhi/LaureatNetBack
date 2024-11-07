package com.eheiste.laureatnet.service.Impl;

import com.eheiste.laureatnet.model.Block;
import com.eheiste.laureatnet.model.UserAccount;
import com.eheiste.laureatnet.repository.BlockRepository;
import com.eheiste.laureatnet.repository.UserAccountRepository;
import com.eheiste.laureatnet.service.BlockService;
import com.eheiste.laureatnet.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private UserAccountService userAccountService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Collection<Block> getAllBlocks() {
        return blockRepository.findAll();
    }

    @Override
    public Collection<Block> getBlocksByUserId(Long id){
        return blockRepository.findBlocksByBlockerId(id);
    }
    @Override
    public Block findBlockById(Long id) {
        Optional<Block> blockOptional = blockRepository.findById(id);
        return blockOptional.orElse(null);
    }

    @Override
    public boolean deleteBlockById(Long id) {
        blockRepository.deleteById(id);
        return true;
    }

    @Override
    public Block saveBlock(Block block) {
        return blockRepository.save(block);
    }

    @Override
    public boolean checkPassword(Long userId, String password) {
        Optional<UserAccount> userOptional = userAccountService.loadById(userId);
        if (userOptional.isPresent()) {
            UserAccount user = userOptional.get();
            return passwordEncoder.matches(password, user.getPassword());
        } else {
            return false;
        }
    }
}
