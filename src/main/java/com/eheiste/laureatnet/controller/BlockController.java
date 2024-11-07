package com.eheiste.laureatnet.controller;
import com.eheiste.laureatnet.dto.BlockDTO;
import com.eheiste.laureatnet.dto.ConnectionDTO;
import com.eheiste.laureatnet.dto.viewmodel.BlockCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.ConnectionCreationDTO;
import com.eheiste.laureatnet.mapper.BlockMapper;
import com.eheiste.laureatnet.mapper.ConnectionMapper;
import com.eheiste.laureatnet.model.Block;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/blocks")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @Autowired
    private BlockMapper blockMapper;

    @GetMapping
    public Collection<Block> getAllBlocks(){
        return blockService.getAllBlocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection<BlockDTO>> getBlocksByUserId(@PathVariable Long id){
        Collection<Block> blocks = blockService.getBlocksByUserId(id);
        if (blocks != null && !blocks.isEmpty()) {
            Collection<BlockDTO> blockDTOS = new ArrayList<>();
            for (Block block : blocks) {
                BlockDTO blockDto = BlockMapper.mapBlockToDTO(block, id);
                blockDTOS.add(blockDto);
            }
            return ResponseEntity.ok(blockDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable long id) {
        try {
            boolean deleted = blockService.deleteBlockById(id);
            if (deleted) {
                return ResponseEntity.noContent().build(); // Return 204 No Content on successful deletion
            } else {
                return ResponseEntity.notFound().build(); // Return 404 Not Found if deletion fails
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // Return 500 Internal Server Error
        }
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Block> createBlock(@RequestBody BlockCreationDTO blockDto){
        Block block = blockMapper.mapToEntity(blockDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(blockService.saveBlock(block));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Block> updateBlock(@PathVariable long id, @RequestBody Block block){
        Block existingBlock = blockService.findBlockById(id);
        if (existingBlock != null) {
            existingBlock.setBlocked(block.getBlocked());
            existingBlock.setBlocker(block.getBlocker());
            blockService.saveBlock(existingBlock);
            return ResponseEntity.ok(existingBlock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/check-password")
    public ResponseEntity<Boolean> checkPassword(@PathVariable Long id, @RequestBody Map<String, String> passwordMap) {
        String password = passwordMap.get("password");
        boolean isValid = blockService.checkPassword(id, password);
        return ResponseEntity.ok(isValid);
    }
}
