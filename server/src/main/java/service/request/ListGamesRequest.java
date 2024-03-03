package service.request;

import java.util.Objects;

public record ListGamesRequest(
        String authToken
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListGamesRequest that = (ListGamesRequest) o;
        return Objects.equals(authToken, that.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken);
    }

    @Override
    public String toString() {
        return "ListGamesRequest{" +
                "authToken='" + authToken + '\'' +
                '}';
    }
}
