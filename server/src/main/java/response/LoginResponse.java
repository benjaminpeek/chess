package response;

import java.util.Objects;

public record LoginResponse(
        String username,
        String authToken
) {}
