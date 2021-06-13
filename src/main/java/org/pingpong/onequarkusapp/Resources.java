package org.pingpong.onequarkusapp;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.pingpong.onequarkusapp.dominio.Orden;
import org.pingpong.onequarkusapp.dominio.Usuaria;

@Path("/")
public class Resources {

    @Inject
    ServiceItem service;

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/wellcome")
    public String wellcome() {
        return "Wellcome Ollivanders!";
    }

    @GET
    @Path("/usuaria/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    // curl -w "\n" http://localhost:8080/usuaria/Doobey -v
    // curl -w "\n" http://localhost:8080/usuaria/Severus -v
    public Response get(@PathParam("nombre") String nombre) {
        Usuaria usuaria = service.cargaUsuaria(nombre);
        return usuaria.getNombre().isEmpty()? 
            Response.status(Response.Status.NOT_FOUND).build():
            Response.status(Response.Status.OK).entity(usuaria).build();
    }

    @POST
    @Path("/ordena")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    // curl -d '{"user": {"nombre": "Hermione"}, "item": {"nombre": "AgedBrie"}}' 
    // -H "Content-Type: application/json" -X POST http://localhost:8080/ordena -v
    public Response post(@Valid Orden orden) {
        Orden pedido = service.comanda(orden.getUser().getNombre(), orden.getItem().getNombre());
        return pedido != null?
            Response.status(Response.Status.CREATED).entity(pedido).build():
            Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/pedidos/{usuaria}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // curl -w "\n" http://localhost:8080/pedidos/Hermione -v
    public List<Orden> list(@PathParam("usuaria") String usuaria) {
        return service.cargaOrden(usuaria);
    }
}