package com.WindowsStartUp.rest;

import com.WindowsStartUp.NetworkManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/command")
public class HttpRest {

    @GET
    @Path("/start/{password}")
    @Produces("application/json")
    public Response startComputer(@PathParam("password") String password) throws JsonProcessingException {
        NetworkManager.getInstance().sendWakeOnLanPackage(password);
        return Response.status(200).entity(generateSuccessResponse()).build();
    }

    @GET
    @Path("/start/{password}/{macAddress}")
    @Produces("application/json")
    public Response startComputer(@PathParam("password") String password, @PathParam("macAddress") String macAddress) throws JsonProcessingException {
        NetworkManager.getInstance().sendWakeOnLanPackage(password, macAddress);
        return Response.status(200).entity(generateSuccessResponse()).build();
    }

    @GET
    @Path("/status")
    @Produces("application/json")
    public Response checkServer() throws JsonProcessingException {
        return Response.status(200).entity(generateSuccessResponse()).build();
    }

    private String generateSuccessResponse() throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        response.put("Status", "OK");
        return new ObjectMapper().writeValueAsString(response);
    }
}
