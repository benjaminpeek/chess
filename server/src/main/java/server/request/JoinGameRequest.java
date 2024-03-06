package server.request;

import java.util.Objects;

public record JoinGameRequest(
    String authToken,
    String playerColor,
    int gameID
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGameRequest that = (JoinGameRequest) o;
        return gameID == that.gameID && Objects.equals(authToken, that.authToken) && Objects.equals(playerColor, that.playerColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, playerColor, gameID);
    }

    @Override
    public String toString() {
        return "JoinGameRequest{" +
                "authToken='" + authToken + '\'' +
                ", playerColor='" + playerColor + '\'' +
                ", gameID=" + gameID +
                '}';
    }
}
