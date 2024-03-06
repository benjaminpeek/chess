package response;

import model.GameData;

import java.util.Collection;
import java.util.Objects;

public record ListGamesResponse(
        Collection<GameData> games
) {}
