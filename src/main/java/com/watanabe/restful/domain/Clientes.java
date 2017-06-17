package com.watanabe.restful.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Clientes {
	
	@XmlElement(name="cliente")
	public List<Cliente> clientes;
	
	public Clientes(){}
	
	public Clientes(List<Cliente> clientes){
		this.clientes = clientes;
	}
}
