package com.qingwenwei;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUpRunner implements CommandLineRunner {

	// @Autowired
	// private UserMapper userMapper;

	// @Autowired
	// private EmailService emailService;

	@Override
	public void run(String... strings) throws Exception {
		// System.out.println("StartUpRunner!");
		// User user = this.userMapper.findById(1L);
		// System.out.println(user.getRolesSet());
		// for(String str : user.getRolesSet()) {
		// System.out.println(str);
		// }

		// String subject = "你好呀！";
		// String message = "这里是内容。";
		//
		// SimpleMailMessage email = new SimpleMailMessage();
		// email.setFrom("springbootforum@163.com");
		// email.setTo("weicee@foxmail.com");
		// email.setSubject(subject);
		// email.setText(message );
		// javaMailSender.send(email);
		// System.out.println("finished sending email");

		// VerificationToken token = new VerificationToken(null, null);
		// token.calculateExpiryDate(System.currentTimeMillis());
	}
}