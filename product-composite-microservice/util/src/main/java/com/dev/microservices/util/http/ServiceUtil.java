package com.dev.microservices.util.http;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {

	private final String port;
	
	private String serviceAddress;
	
	@Autowired
	public ServiceUtil(@Value("${server.port}") String port) {
		this.port = port;
	}
	
	public String getServiceAddress() {
		return Optional.ofNullable(serviceAddress)
					.orElseGet(() -> {
						serviceAddress = findHostname.get() + "/" + findIpAddress.get() + ":" + port;
						return serviceAddress;
					});
	}
	
	private Supplier<String> findHostname = () -> {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "unknown host name";
		}
	};
	
	private Supplier<String> findIpAddress = () -> {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "unknown IP address";
		}
	};
}