package com.eheiste.laureatnet.controller;

import com.eheiste.laureatnet.dto.ConnectionDTO;
import com.eheiste.laureatnet.dto.viewmodel.ConnectionCreationDTO;
import com.eheiste.laureatnet.dto.viewmodel.PostCreationDTO;
import com.eheiste.laureatnet.mapper.ConnectionMapper;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.service.ConnectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/connections")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ConnectionMapper connectionMapper;

    @GetMapping
    public Collection<Connection> getAllConnections(){
        return connectionService.getAllConnections();
    }

    @GetMapping("/accepted/{id}")
    public ResponseEntity<Collection<ConnectionDTO>> getAcceptedConnectionsByUserId(@PathVariable Long id) {
        Collection<Connection> acceptedConnections = connectionService.getAcceptedConnectionsByUserId(id);
        if (acceptedConnections != null && !acceptedConnections.isEmpty()) {
            Collection<ConnectionDTO> accceptedConnectionDTOS = new ArrayList<>();
            for (Connection connection : acceptedConnections) {
                ConnectionDTO accceptedConnectionDTO = ConnectionMapper.mapConnectionToDTO(connection, id);
                accceptedConnectionDTOS.add(accceptedConnectionDTO);
            }
            return ResponseEntity.ok(accceptedConnectionDTOS);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Collection<ConnectionDTO>> getConnectionsByUserId(@PathVariable Long id) {
        Collection<Connection> connections = connectionService.findConnectionsById(id);
        if (connections != null && !connections.isEmpty()) {
            Collection<ConnectionDTO> connectionDTOS = new ArrayList<>();
            for (Connection connection : connections) {
                ConnectionDTO accceptedConnectionDTO = ConnectionMapper.mapConnectionToDTO(connection, id);
                connectionDTOS.add(accceptedConnectionDTO);
            }
            System.out.println(connectionDTOS);
            return ResponseEntity.ok(connectionDTOS);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("pending/received/{id}")
    public ResponseEntity<Collection<ConnectionDTO>> getPendingConnectionsByReceiverId(@PathVariable long id) {
        Collection<Connection> acceptedConnections = connectionService.getPendingConnectionsByReceiverId(id);
        if (acceptedConnections != null && !acceptedConnections.isEmpty()) {
            Collection<ConnectionDTO> accceptedConnectionDTOS = new ArrayList<>();
            for (Connection connection : acceptedConnections) {
                ConnectionDTO accceptedConnectionDTO = ConnectionMapper.mapConnectionToDTO(connection, id);
                accceptedConnectionDTOS.add(accceptedConnectionDTO);
            }
            return ResponseEntity.ok(accceptedConnectionDTOS);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("pending/sent/{id}")
    public ResponseEntity<Collection<ConnectionDTO>> getPendingConnectionsBySenderId(@PathVariable long id) {
        Collection<Connection> acceptedConnections = connectionService.getPendingConnectionsBySenderId(id);
        if (acceptedConnections != null && !acceptedConnections.isEmpty()) {
            Collection<ConnectionDTO> accceptedConnectionDTOS = new ArrayList<>();
            for (Connection connection : acceptedConnections) {
                ConnectionDTO accceptedConnectionDTO = ConnectionMapper.mapConnectionToDTO(connection, id);
                accceptedConnectionDTOS.add(accceptedConnectionDTO);
            }
            return ResponseEntity.ok(accceptedConnectionDTOS);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectionDTO> getConnection(@PathVariable long id){
        Connection connection = connectionService.findConnectionById(id);
        if (connection != null) {
            ConnectionDTO connectionDTO = ConnectionMapper.mapConnectionToDTO(connection, id);
            return ResponseEntity.ok(connectionDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteConnection(@PathVariable Long id){
        connectionService.deleteConnectionById(id);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Connection> createConnection(@RequestBody ConnectionCreationDTO connectionDto){
        System.out.println(connectionDto);
        Connection connection = connectionMapper.mapToEntity(connectionDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(connectionService.saveConnection(connection));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Connection> updateConnection(@PathVariable Long id, @RequestBody Boolean isAccepted){
        Connection existingConnection = connectionService.findConnectionById(id);
        if (existingConnection != null) {
            existingConnection.setAccepted(isAccepted);
            connectionService.saveConnection(existingConnection);
            return ResponseEntity.ok(existingConnection);
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
