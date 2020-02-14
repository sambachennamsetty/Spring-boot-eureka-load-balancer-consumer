package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/student-consumer")
public class StudentConsumer {

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@RequestMapping("/get")
	public String getStudentData() {
		String path = "student-provider/get";
		ServiceInstance si = loadBalancerClient.choose("STUDENT-PROVIDER");
		String uri = si.getUri().toString();
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> re = rt.getForEntity(uri + path, String.class);
		return "Consumer -> " + re.getBody();
	}
}
