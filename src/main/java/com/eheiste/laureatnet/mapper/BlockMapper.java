package com.eheiste.laureatnet.mapper;

import com.eheiste.laureatnet.dto.BlockDTO;
import com.eheiste.laureatnet.dto.ConnectionDTO;
import com.eheiste.laureatnet.dto.viewmodel.BlockCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.ConnectionCreationDTO;
import com.eheiste.laureatnet.model.Block;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.UserAccount;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
@Component
public class BlockMapper {
    public static BlockDTO mapBlockToDTO(Block block, Long userId) {
        BlockDTO blockDTO = new BlockDTO();
        if (Objects.equals(block.getBlocker().getId(), userId)) {
                blockDTO.setId(block.getId());
                blockDTO.setFullname(block.getBlocked().getUserProfile().getFirstName() + " " + block.getBlocked().getUserProfile().getLastName());
                blockDTO.setCreatedAt(block.getCreatedAt());
        }
        return blockDTO;
    }

    public Block mapToEntity(BlockCreationDTO dto) {
        Block block = new Block();
        UserAccount blocker = new UserAccount();
        UserAccount blocked = new UserAccount();
        blocker.setId(dto.getBlockerId());
        blocked.setId(dto.getBlockedId());
        block.setId(dto.getBlockId());
        block.setBlocked(blocked);
        block.setBlocker(blocker);
        block.setCreatedAt(LocalDateTime.now());

        return block;
    }
}
