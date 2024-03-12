package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import response.ClearApplicationResponse;
import spark.*;
import service.ClearService;

public class ClearHandler {
    private final ClearService clearService;

    public ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }

    public String clearApplicationHandler(Request req, Response res) throws DataAccessException {
        ClearApplicationResponse clearApplicationResponse = this.clearService.clearApplicationService(req.body());
        res.status(200);
        return new Gson().toJson(clearApplicationResponse);
    }
}
