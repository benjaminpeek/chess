package response;

import java.util.Objects;

public record RegisterResponse(
        String username,
        String authToken
) {}
