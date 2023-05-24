package ec.com.books.library.supa.resource;

import ec.com.books.library.supa.dto.BookDTO;
import ec.com.books.library.supa.service.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class BookResource {
    private final BookService service;

    @GET
    public Response obtenerTodos(){
        return Response.ok(service.getALl()).build();
    }


    @Path("{id}")
    @GET
    public Response obtenerPorId(@PathParam("id") String id){
        return Response.ok(service.getById(id)).build();
    }

    @POST
    public Response guardar(BookDTO bookDTO){
        return Response.ok(service.save(bookDTO)).build();
    }

    @PUT
    public Response actualizar(BookDTO bookDTO){
        return Response.status(Status.ACCEPTED).entity(service.update(bookDTO)).build();
    }


    @Path("{id}")
    @DELETE
    public Response eliminar(@PathParam("id") String id){
        return Response.ok(service.delete(id)).build();
    }

}
