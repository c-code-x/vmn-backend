package com.vdc.vmnbackend.dto.req;

import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.enumerators.VentureStage;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

public record InviteVentureReqDTO(
        @NotEmpty
        String ventureName,
        @NotEmpty
        VentureStage ventureStage
) {

}
