package ec.com.students.library.supa.resource;

import ec.com.students.library.supa.dto.StudentDTO;
import ec.com.students.library.supa.service.StudentService;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.*;
import lombok.AllArgsConstructor;
import jakarta.ws.rs.core.MediaType;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor

public class StudentResource {
    private final StudentService service;

    @GET
    public Response obtenerTodos(){
        return Response.ok(service.getAll()).build();
    }

    @Path("{id}")
    @GET
    public Response obtenerPorId(@PathParam("id") String id){
        return Response.ok(service.getById(id)).build();
    }

    @Path("{regis_num}")
    @GET
    public Response obtenerPorMatricula(@PathParam("regis_num") String regis_num){
        return Response.ok(service.getByRegisNum(regis_num)).build();
    }

    @POST
    public Response guardar(StudentDTO studentDTO){
        return Response.ok(service.save(studentDTO)).build();
    }

    @PUT
    public Response actualizar(StudentDTO studentDTO){
        return Response.status(Status.ACCEPTED).entity(service.update(studentDTO)).build();
    }


    @Path("{id}")
    @DELETE
    public Response eliminar(@PathParam("id") String id){
        return Response.ok(service.delete(id)).build();
    }

}
