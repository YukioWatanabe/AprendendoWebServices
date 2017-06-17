package com.watanabe.restful.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="cliente")
public class Cliente {
	@XmlAttribute
	public Long id;
	@XmlElement
	public String nome;
	@XmlElement
	public String sobrenome;
}
