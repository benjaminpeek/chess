package clientTests;

import exceptions.ResponseException;
import org.junit.jupiter.api.*;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;
import response.RegisterResponse;
import server.Server;
import serverFacade.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    public void prep() throws ResponseException {
        serverFacade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerSuccess() throws ResponseException {
        RegisterResponse res = serverFacade.register(new RegisterRequest("ben", "pass", "ben@gmail"));
        assertEquals("ben", res.username());
    }

    @Test
    public void registerThrows() throws ResponseException {
        serverFacade.register(new RegisterRequest("ben", "pass", "ben@gmail"));
        assertThrows(ResponseException.class, () -> serverFacade.register(new RegisterRequest("ben", "pass", "ben@gmail")));
    }

    @Test
    public void loginSuccess() throws ResponseException {
        serverFacade.register(new RegisterRequest("ben", "pass", "ben@gmail"));
        LoginResponse res = serverFacade.login(new LoginRequest("ben", "pass"));
        assertEquals("ben", res.username());
    }

    @Test
    public void loginThrows() {
        assertThrows(ResponseException.class, () -> serverFacade.login(new LoginRequest("ben", "pass")));
    }

    @Test
    public void logoutSuccess() throws ResponseException {
        serverFacade.register(new RegisterRequest("ben", "pass", "ben@gmail"));
        serverFacade.login(new LoginRequest("ben", "pass"));
        serverFacade.logout();
    }

    @Test
    public void logoutThrows() {
        assertThrows(ResponseException.class, () -> serverFacade.logout());
    }

    @Test
    public void createGameSuccess() throws ResponseException {
        serverFacade.register(new RegisterRequest("ben", "pass", "ben@gmail"));
        serverFacade.createGame(new CreateGameRequest("cool game"));
    }

    @Test
    public void createGameThrows() {
        assertThrows(ResponseException.class, () -> serverFacade.createGame(new CreateGameRequest("fails")));
    }

    @Test
    public void joinGameSuccess() throws ResponseException {
        serverFacade.register(new RegisterRequest("ben", "pass", "ben@gmail"));
        serverFacade.login(new LoginRequest("ben", "pass"));
        serverFacade.createGame(new CreateGameRequest("cool game"));

        serverFacade.joinGame(new JoinGameRequest("WHITE", 1));
    }

    @Test
    public void joinGameThrows() {
        assertThrows(ResponseException.class, () -> serverFacade.joinGame(new JoinGameRequest("WHITE", 100)));
    }

    @Test
    public void listGamesSuccess() throws ResponseException {
        serverFacade.register(new RegisterRequest("ben", "pass", "ben@gmail"));
        serverFacade.login(new LoginRequest("ben", "pass"));
        for (int i = 1; i < 11; i++) {
            serverFacade.createGame(new CreateGameRequest("game"+i));
        }

        System.out.println(serverFacade.listGames());
    }

    @Test
    public void listGamesThrows() {
        assertThrows(ResponseException.class, () -> serverFacade.listGames());
    }

}
