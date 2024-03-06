package request;

import java.util.Objects;

public record LoginRequest(
        String username,
        String password
) {}
