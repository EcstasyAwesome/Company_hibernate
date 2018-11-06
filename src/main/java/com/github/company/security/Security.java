package com.github.company.security;

import com.github.company.dao.entity.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Security {

    int SUCCESS = 1;
    int UNAUTHORIZED = 2;
    int FORBIDDEN = 3;
    int NOT_FOUND = 4;
    int GUEST = 1;
    int USER = 2;
    int ADMIN = 3;

    int verify(@Nullable User user, @NotNull String link);
}