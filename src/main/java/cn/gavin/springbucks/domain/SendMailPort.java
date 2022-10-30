package cn.gavin.springbucks.domain;

public interface SendMailPort {
    void sendMail(String subject, String text);
}
