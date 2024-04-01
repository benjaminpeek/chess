package clientTests;

import exceptions.ResponseException;
import org.junit.jupiter.api.*;
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
        String authToken = serverFacade.login(new LoginRequest("ben", "pass")).authToken();
        serverFacade.logout();
    }

}
