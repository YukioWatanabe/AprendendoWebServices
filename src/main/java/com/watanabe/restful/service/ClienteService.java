package com.watanabe.restful.service;

import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.watanabe.restful.domain.Cliente;
import com.watanabe.restful.domain.Clientes;
import com.watanabe.restful.resource.ClienteResourceMock;
import com.watanabe.restful.resource.IClienteResource;
import com.watanabe.restful.util.XmlParser;

@Path(value="clientes")
public class ClienteService {

	private final IClienteResource clienteResource;
	
	public ClienteService() {
		clienteResource = new ClienteResourceMock();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response criarCliente(InputStream is){
		Cliente cliente = XmlParser.lerArquivo(is, Cliente.class);
		clienteResource.insertCliente(cliente);
		return Response.created(URI.create("/clientes/"+cliente.id)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public StreamingOutput getClientes(){
		return (output) -> XmlParser.escreveOutput(output,new Clientes(clienteResource.getAllClientes()));
	}

	@GET
	@Path(value="{id}")
	@Produces(MediaType.APPLICATION_XML)
	public StreamingOutput buscarCliente(@PathParam(value="id") Long id){
		Cliente cliente = clienteResource.getCliente(id);

		if(cliente == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		return (output) -> XmlParser.escreveOutput(output,cliente);
	}

	@PUT
	@Path(value="{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response atualizarCliente(@PathParam(value="id") Long id, InputStream is){
		Cliente clienteDoBanco = clienteResource.getCliente(id);

		if(clienteDoBanco == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		Cliente clienteDoArquivo = XmlParser.lerArquivo(is, Cliente.class);
		clienteDoBanco.nome = clienteDoArquivo.nome;
		clienteDoBanco.sobrenome = clienteDoArquivo.sobrenome;
		
		return Response.ok().build();
	}

	@DELETE
	@Path(value="{id}")
	public Response deletarCliente(@PathParam(value="id") Long id){
		Cliente cliente = clienteResource.getCliente(id);

		if(cliente == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		
		clienteResource.deleteCliente(id);
		
		return Response.ok().build();
	}

}
