package com.watanabe.restful.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="cliente")
public class Cliente {
	public Long id;
	public String nome;
	public String sobrenome;
}
