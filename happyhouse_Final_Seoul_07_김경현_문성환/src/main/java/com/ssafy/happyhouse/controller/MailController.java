package com.ssafy.happyhouse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.model.service.MailService;
import com.ssafy.happyhouse.model.service.MailServiceImpl;

@Controller
@RequestMapping("/service")
public class MailController {
	@Autowired
	MailService service;

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@GetMapping("/mail/{userId}")
	@ResponseBody
	public void emailConfirm(@PathVariable("userId") String userId) throws Exception {
		System.out.println(userId);
		logger.info("post emailConfirm");
		System.out.println("전달 받은 이메일 : " + userId);
//			service.sendSimpleMessage(memberDto.getUserId());
		service.sendSimpleMessage(userId);
	}

	@GetMapping("/verifyCode/{code}")
	public ResponseEntity<Integer> verifyCode(@PathVariable("code") String code) {
		logger.info("Post verifyCode");

		int result = 0;
		System.out.println("code : " + code);
		System.out.println("code match : " + MailServiceImpl.ePw.equals(code));
		if (MailServiceImpl.ePw.equals(code)) {
			result = 1;
		}

		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}
