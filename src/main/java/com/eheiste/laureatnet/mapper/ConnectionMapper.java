package com.eheiste.laureatnet.mapper;

import com.eheiste.laureatnet.dto.ConnectionDTO;
import com.eheiste.laureatnet.dto.viewmodel.ConnectionCreationDTO;
import com.eheiste.laureatnet.model.Connection;
import com.eheiste.laureatnet.model.UserAccount;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class ConnectionMapper {
    public static ConnectionDTO mapConnectionToDTO(Connection connection, Long userId) {
        ConnectionDTO accceptedConnectionDTO = new ConnectionDTO();
        if (connection.getSender().getId() != userId) {
            if (connection.getSender().getUserProfile() != null) {
                accceptedConnectionDTO.setId(connection.getId());
                accceptedConnectionDTO.setUserConnectedId(connection.getReceiver().getId());
                accceptedConnectionDTO.setUserConnectWithId(connection.getSender().getId());
                accceptedConnectionDTO.setAccepted(connection.isAccepted());
                accceptedConnectionDTO.setBio(connection.getSender().getUserProfile().getBio());
                accceptedConnectionDTO.setFullname(connection.getSender().getUserProfile().getFirstName() + " " + connection.getSender().getUserProfile().getLastName());
                accceptedConnectionDTO.setPicture(connection.getSender().getUserProfile().getPicture());
            }
        } else if (connection.getReceiver().getUserProfile() != null) {
            accceptedConnectionDTO.setId(connection.getId());
            accceptedConnectionDTO.setUserConnectedId(connection.getSender().getId());
            accceptedConnectionDTO.setUserConnectWithId(connection.getReceiver().getId());
            accceptedConnectionDTO.setAccepted(connection.isAccepted());
            accceptedConnectionDTO.setBio(connection.getReceiver().getUserProfile().getBio());
            accceptedConnectionDTO.setFullname(connection.getReceiver().getUserProfile().getFirstName() + " " + connection.getReceiver().getUserProfile().getLastName());
            accceptedConnectionDTO.setPicture(connection.getReceiver().getUserProfile().getPicture());
        }
        return accceptedConnectionDTO;
    }

    public Connection mapToEntity(ConnectionCreationDTO dto) {
        System.out.println(dto);
        Connection connection = new Connection();
        UserAccount sender = new UserAccount();
        UserAccount receiver = new UserAccount();

        sender.setId(dto.getSenderId());
        receiver.setId(dto.getReceiverId());
        connection.setId(dto.getConnectionId());
        connection.setSender(sender);
        connection.setReceiver(receiver);
        connection.setCreatedAt(LocalDateTime.now());

        return connection;
    }
}

