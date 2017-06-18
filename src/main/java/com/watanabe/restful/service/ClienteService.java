package com.watanabe.restful.service;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.watanabe.restful.contraint.PathContraints;
import com.watanabe.restful.domain.Cliente;
import com.watanabe.restful.resource.ClienteResourceMock;
import com.watanabe.restful.resource.IClienteResource;

@Path(value="clientes")
public class ClienteService {

	private final IClienteResource clienteResource;
	public static final URI CLIENTE_URI = UriBuilder.fromUri(PathContraints.SERVICE_URI).path(ClienteService.class).build();
	
	public ClienteService() {
		clienteResource = new ClienteResourceMock();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response criarCliente(Cliente cliente){
		clienteResource.insertCliente(cliente);
		return Response.created(UriBuilder.fromUri(CLIENTE_URI).path(cliente.getId().toString()).build()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getClientes(){
		GenericEntity<List<Cliente>> clientes = new GenericEntity<List<Cliente>>(clienteResource.getAllClientes()){};
		return Response.ok(clientes).build();
	}

	@GET
	@Path(value="{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response buscarCliente(@PathParam(value="id") Long id){
		Cliente cliente = clienteResource.getCliente(id);

		if(cliente == null)
			throw new NotFoundException();

		return Response.ok(cliente).build();
	}

	@PUT
	@Path(value="{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response atualizarCliente(@PathParam(value="id") Long id, Cliente clienteDoArquivo){
		Cliente clienteDoBanco = clienteResource.getCliente(id);

		if(clienteDoBanco == null)
			throw new NotFoundException();

		clienteDoBanco.setNome(clienteDoArquivo.getNome());
		clienteDoBanco.setSobrenome(clienteDoArquivo.getSobrenome());
		
		clienteResource.updateCliente(clienteDoBanco);
		
		return Response.ok().build();
	}

	@DELETE
	@Path(value="{id}")
	public Response deletarCliente(@PathParam(value="id") Long id){
		Cliente cliente = clienteResource.getCliente(id);

		if(cliente == null)
			throw new NotFoundException();
		
		clienteResource.deleteCliente(id);
		
		return Response.ok().build();
	}
	
	public static enum ClienteServicePath{
		ID("{id}");
		
		private final String path;

		private ClienteServicePath(String path){
			this.path = path;
		}
		
		public String getPath() {
			return path;
		}
		
	}

}
