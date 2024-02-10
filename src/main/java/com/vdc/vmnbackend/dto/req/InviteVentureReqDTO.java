package com.vdc.vmnbackend.dto.req;

import com.vdc.vmnbackend.enumerators.VentureStage;
import jakarta.validation.constraints.NotEmpty;

/**
 * Represents a request DTO for inviting a venture with provided details.
 */
public record InviteVentureReqDTO(
        /**
         * The name of the venture.
         */
        @NotEmpty
        String ventureName,
        
        /**
         * The stage of the venture.
         */
        @NotEmpty
        VentureStage ventureStage
) {
}
