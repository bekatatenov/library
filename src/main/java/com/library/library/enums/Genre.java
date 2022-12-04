package com.library.library.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Genre implements GrantedAuthority {
    PHILOCOPHICAL_LITERATURE,
    HISTIRICAL_LITERATURE,
    BIOGRAPHY,
    POLITICAL_LITERATURE,
    LEGAL_LITERATURE,
    EDUCATIONAL_LITERATURE,
    FICTION,
    PRECISE_SCIENCE,
    MEDICINE,
    TECHNOLOGY,
    MILITARY_LITERATURE;

    @Override
    public String getAuthority() {
        return name();
    }
}
