package com.watanabe.restful.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class XmlParser{

	public static <T> void escreveOutput(OutputStream os, T obj) {
		try {
			StringWriter sw = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			context.createMarshaller().marshal(obj, sw);
			
			PrintStream ps = new PrintStream(os);
			ps.println(sw.toString());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	public static <T> T lerArquivo(InputStream is, Class<T> outputClass) {
		T obj = null;
		
		try {
			JAXBContext context = JAXBContext.newInstance(outputClass);
			obj = outputClass.cast( context.createUnmarshaller().unmarshal(is) );
		} catch (JAXBException e) {
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
		
		return obj;
	}

}
