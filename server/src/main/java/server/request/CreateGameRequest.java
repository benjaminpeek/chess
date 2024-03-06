package server.request;

import java.util.Objects;

public record CreateGameRequest(
        String authToken,
        String gameName
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateGameRequest that = (CreateGameRequest) o;
        return Objects.equals(authToken, that.authToken) && Objects.equals(gameName, that.gameName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, gameName);
    }

    @Override
    public String toString() {
        return "CreateGameRequest{" +
                "authToken='" + authToken + '\'' +
                ", gameName='" + gameName + '\'' +
                '}';
    }
}
