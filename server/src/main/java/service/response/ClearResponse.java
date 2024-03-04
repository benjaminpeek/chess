package service.response;

import java.util.Objects;

public record ClearResponse(
        String message
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClearResponse that = (ClearResponse) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "ClearResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
