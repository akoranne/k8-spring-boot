package com.sakx.developer.k8springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class ServiceController {
	
	private static final Logger log = LoggerFactory.getLogger(ServiceController.class);
	
	@Autowired
	private Environment env;
	
	@Value("${message:Hello default}")
	private String message;

	@Value("${user:unknown}")
	private String user;

	@Value("${password:unknown}")
	private String password;

	@Value("${endpoint:host:port}")
	private String endpoint;

	@Value("${sid:sid}")
	private String sid;
	
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody
    Greeting helloView() {
        return new Greeting("hello world");
    }


	@RequestMapping("/message")
	public String getMessage() {
		
		// dataSource.setUrl(env.getProperty("jdbc.url"));

		
		
		log.info(" in getMessage() " + this.user + ", " + this.password);
		StringBuilder buf = new StringBuilder();
		buf.append("jdbc:oracle:thin:")
				// .append(user)
				.append(env.getProperty("spring.security.user.name"))
				.append("/")
				.append(env.getProperty("spring.security.user.password"))
				.append("@")
				.append(endpoint)
				.append(":")
				.append(sid);

		StringBuilder msg = new StringBuilder();
		msg.append(" Message: ").append(this.message);
		msg.append(" [dburl: ").append(buf).append("]");
		return msg.toString();
	}
	
}
