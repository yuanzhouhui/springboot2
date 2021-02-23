package com.bright.springboot2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author bright
 * @version 1.0
 * @description
 * @date 2021-02-23 21:37
 */
@RestController
public class AopController {

	@GetMapping(value = "testAround")
	public String testAround() throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		//throw new NullPointerException("空");
		return "SUCCESS";
	}
}
