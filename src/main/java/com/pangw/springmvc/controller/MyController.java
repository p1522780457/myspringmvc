package com.pangw.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mvc")
public class MyController {

	@RequestMapping("hello")
	public String helloworld(){
		return "home";
	}
}
