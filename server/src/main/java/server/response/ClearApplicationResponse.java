package server.response;

import java.util.Objects;

public record ClearApplicationResponse(
        String message
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClearApplicationResponse that = (ClearApplicationResponse) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "ClearApplicationResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
