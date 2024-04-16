package serverFacade;

import com.google.gson.Gson;
import exceptions.ResponseException;
import request.*;
import response.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private final String serverUrl;
    private String authToken;

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }


    // method used only for testing
    public void clear() throws ResponseException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, ClearApplicationResponse.class);
    }

    public RegisterResponse register(RegisterRequest req) throws ResponseException {
        var path = "/user";
        RegisterResponse madeReq = this.makeRequest("POST", path, req, RegisterResponse.class);
        authToken = madeReq.authToken();
        return madeReq;
    }

    public LoginResponse login(LoginRequest req) throws ResponseException {
        var path = "/session";
        LoginResponse madeReq = this.makeRequest("POST", path, req, LoginResponse.class);
        authToken = madeReq.authToken();
        return madeReq;
    }

    public void logout() throws ResponseException {
        var path = "/session";
        this.makeRequest("DELETE", path, null, LogoutResponse.class);
    }

    public ListGamesResponse listGames() throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, null, ListGamesResponse.class);
    }

    public void createGame(CreateGameRequest req) throws ResponseException {
        var path = "/game";
        this.makeRequest("POST", path, req, CreateGameResponse.class);
    }

    public void joinGame(JoinGameRequest req) throws ResponseException {
        var path = "/game";
        this.makeRequest("PUT", path, req, JoinGameResponse.class);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            http.setRequestProperty("authorization", authToken);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
