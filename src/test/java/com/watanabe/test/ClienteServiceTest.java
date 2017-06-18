package com.watanabe.test;

import static com.watanabe.restful.service.ClienteService.CLIENTE_URI;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.junit.AfterClass;
import org.junit.Test;

import com.watanabe.restful.domain.Cliente;

public class ClienteServiceTest {
	
	private static final Client CLIENT = ClientBuilder.newClient();
	
	@AfterClass
	public static void closeClient(){
		CLIENT.close();
	}
	
	@Test
	public void deve_ser_possivel_cadastrar_cliente(){
		Cliente cliente = new Cliente();
		cliente.setNome("Xpto inserido");
		cliente.setSobrenome("de Xpto");
		
		Response response = CLIENT.target(CLIENTE_URI)
								  .request()
								  .post(Entity.xml(cliente));
		
		assertEquals(Response.Status.CREATED.getStatusCode(),response.getStatus());
	}
	
	@Test
	public void deve_ser_possivel_consultar_cliente(){
		Response response = CLIENT.target(UriBuilder.fromUri(CLIENTE_URI).path("1"))
				.request()
				.get();
		
		assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
	}
	
	@Test
	public void deve_ser_possivel_atualizar_cliente(){
		Cliente cliente = new Cliente();
		cliente.setNome("Xpto atualizado");
		cliente.setSobrenome("de Xpto");
		
		Response response = CLIENT.target(UriBuilder.fromUri(CLIENTE_URI).path("1"))
				.request()
				.put(Entity.xml(cliente));
		
		assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
	}
	
	@Test
	public void deve_ser_possivel_deletar_cliente(){
		Cliente cliente = new Cliente();
		cliente.setNome("Xpto inserido");
		cliente.setSobrenome("de Xpto");
		
		CLIENT.target(CLIENTE_URI).request().post(Entity.xml(cliente)).close();
		
		Response response = CLIENT.target(UriBuilder.fromUri(CLIENTE_URI).path("2"))
				.request()
				.delete();
		
		assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
	}
}
