package service.response;

import java.util.Objects;

public record JoinGameResponse(
        String message
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinGameResponse that = (JoinGameResponse) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "JoinGameResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
