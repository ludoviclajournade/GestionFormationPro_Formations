/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.toulouse.miage;

import com.google.gson.Gson;
import fr.toulouse.miage.entities.Planning;
import fr.toulouse.miage.services.ServiceIFormationLocal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.soap.SOAPException;

/**
 * REST Web Service
 *
 * @author Claire
 */
@Path("iformation")
@RequestScoped
public class IformationResource {

    @Context
    private UriInfo context;
    
    ServiceIFormationLocal iform;
    private Gson gson;

    /**
     * Creates a new instance of GenericResource
     */
    public IformationResource() {
        this.gson = new Gson();
        this.iform = lookupServicesIFormationLocal();
    }

    /**
     * Retrieves representation of an instance of fr.toulouse.miage.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    public Response getJson() {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @Path("creerIForm")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String creerIFormation(@QueryParam("idForm") int idFormation, @QueryParam("effectif") int numEffectif, 
            @QueryParam("codeClient") String codeClient) throws SOAPException {
        return this.gson.toJson(this.iform.creerIFormation(idFormation, numEffectif, codeClient));
    }
    
    
    @Path("annulerIform")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String annulerIformation(@QueryParam("idIForm") int idIform) {
        return this.gson.toJson(this.iform.annulerIFormation(idIform));
    }
    
    @Path("choixSalleIform")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String choixSalleIformation(@QueryParam("idIForm") int idIformation) {
        return this.gson.toJson(this.iform.choixSalleIformation(idIformation));
    }
    
    private ServiceIFormationLocal lookupServicesIFormationLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ServiceIFormationLocal) c.lookup("java:global/Formation-ear/Formation-ejb-1.0/ServiceIFormation!fr.toulouse.miage.services.ServiceIFormationLocal");       
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    
}
