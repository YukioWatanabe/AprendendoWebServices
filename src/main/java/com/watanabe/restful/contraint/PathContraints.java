package com.watanabe.restful.contraint;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

public class PathContraints {
	public static final String HOST = "localhost";
	public static final int PORT = 8080;
	public static final URI SERVICE_URI = UriBuilder.fromPath("/restful/services").host(HOST).port(PORT).scheme("http").build();
}
