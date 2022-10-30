package cn.gavin.springbucks.mail;

import cn.gavin.springbucks.domain.SendMailPort;
import org.springframework.stereotype.Component;

@Component
public class MailingAdapter implements SendMailPort {

  @Override
  public void sendMail(String subject, String text) {
      // sending a mail...
  }

}
