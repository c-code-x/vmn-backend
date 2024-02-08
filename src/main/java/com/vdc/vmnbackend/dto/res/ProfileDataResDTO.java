package com.vdc.vmnbackend.dto.res;

import com.vdc.vmnbackend.dao.UserDAO;

public record ProfileDataResDTO<D>(
        UserDAO userDAO,
        D roleSpecificData
) {
}
