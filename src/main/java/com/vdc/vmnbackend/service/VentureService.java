package com.vdc.vmnbackend.service;

import com.vdc.vmnbackend.dao.UserDAO;
import com.vdc.vmnbackend.dao.VentureDAO;
import com.vdc.vmnbackend.dto.req.InviteVentureReqDTO;
import com.vdc.vmnbackend.dto.res.BasicResDTO;
import com.vdc.vmnbackend.dto.res.ResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for managing ventures.
 */
public interface VentureService {

    /**
     * Creates a new venture based on the provided data.
     *
     * @param inviteVentureReqDTO The request containing details of the new venture.
     * @param userDAO             The UserDAO associated with the creator of the venture.
     * @return A BasicResDTO indicating the result of the operation.
     */
    BasicResDTO createVenture(InviteVentureReqDTO inviteVentureReqDTO, UserDAO userDAO);

    /**
     * Retrieves all ventures associated with a user.
     *
     * @param userDAO The UserDAO of the user.
     * @return A ResponseDTO containing a list of VentureDAO objects.
     */
    ResponseDTO<List<VentureDAO>> getAllVentures(UserDAO userDAO);

    /**
     * Retrieves a venture by its ID.
     *
     * @param ventureId The UUID of the venture to retrieve.
     * @return An Optional containing the VentureDAO if found, otherwise empty.
     */
    Optional<VentureDAO> getVentureById(UUID ventureId);
}
