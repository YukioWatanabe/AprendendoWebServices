package com.watanabe.restful.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.watanabe.restful.domain.Cliente;

@Path(value="clientes")
public class ClienteService {

	private static final Map<Long,Cliente> CLIENTE_DB = new HashMap<Long, Cliente>();
	private static final AtomicLong COUNTER = new AtomicLong();

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response criarCliente(InputStream is){
		Cliente cliente = lerArquivoCliente(is);
		cliente.id = COUNTER.incrementAndGet();
		CLIENTE_DB.put(cliente.id, cliente);
		return Response.created(URI.create("/clientes/"+cliente.id)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public StreamingOutput getClientes(){
		return (output) -> {
			PrintStream xml = new PrintStream(output);
			xml.println("<clientes>");
			
			for(Map.Entry<Long, Cliente> entry : CLIENTE_DB.entrySet())
				escreveOutput(xml,entry.getValue());
			
			xml.println("</clientes>");
		};
	}

	@GET
	@Path(value="{id}")
	@Produces(MediaType.APPLICATION_XML)
	public StreamingOutput getCliente(@PathParam(value="id") Long id){
		Cliente cliente = CLIENTE_DB.get(id);

		if(cliente == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		return (output) -> escreveOutput(new PrintStream(output),cliente);
	}

	@PUT
	@Path(value="{id}")
	public void atualizarCliente(@PathParam(value="id") Long id, InputStream is){
		Cliente clienteDoBanco = CLIENTE_DB.get(id);

		if(clienteDoBanco == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		Cliente clienteDoArquivo = lerArquivoCliente(is);
		clienteDoBanco.id = clienteDoArquivo.id;
		clienteDoBanco.nome = clienteDoArquivo.nome;
		clienteDoBanco.sobrenome = clienteDoArquivo.sobrenome;
	}

	@DELETE
	@Path(value="{id}")
	public void deletarCliente(@PathParam(value="id") Long id){
		CLIENTE_DB.remove(id);
	}

	private Cliente lerArquivoCliente(InputStream is) {
		Cliente cliente = new Cliente();

		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();

			if(root.getAttribute("id") != null && !root.getAttribute("id").trim().equals(""))
				cliente.id = Long.parseLong(root.getAttribute("id"));

			NodeList nodes = root.getChildNodes();

			for(int i = 0, length = nodes.getLength(); i < length; i++){
				Node node = nodes.item(i);
				if(node.getNodeName().equals("id"))
					cliente.id = Long.parseLong(node.getTextContent());
				if(node.getNodeName().equals("nome"))
					cliente.nome = node.getTextContent();
				if(node.getNodeName().equals("sobrenome"))
					cliente.sobrenome = node.getTextContent();
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		return cliente;
	}

	private void escreveOutput(PrintStream xml, Cliente cliente) {
		xml.println(String.format("<cliente id=\"%d\">", cliente.id));
		xml.println(String.format("<nome>%s</nome>", cliente.nome));
		xml.println(String.format("<sobrenome>%s</sobrenome>", cliente.sobrenome));
		xml.println("</cliente>");
	}
}
