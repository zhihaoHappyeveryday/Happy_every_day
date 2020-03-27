package zhihao.sendmail.impl;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import zhihao.sendmail.SendMail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.InputStream;

/**
 * @Author: zhihao
 * @Date: 2020/3/27 14:01
 * @Description: 发送邮件具体实现
 * @Versions 1.0
 **/
public class SendMailImpl implements SendMail {

    private  JavaMailSender javaMailSender;

    public SendMailImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 发送普通文本邮件
     *
     * @param from 发送者邮箱
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return boolean
     * @author: zhihao
     * @date: 2020/3/27
     */
    @Override
    public boolean sendTextMail(String from, String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        //发送
        try {
            javaMailSender.send(mailMessage);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送带Html文本邮件
     *
     * @param from 发送者邮箱
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content 带Html文本邮件内容
     * @return boolean
     * @author: zhihao
     * @date: 2020/3/27
     */
    @Override
    public boolean sendHtmlMail(String from, String to, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            //传递对象进行构建发送html文本格式
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送带附件文本邮件
     *
     * @param from 发送者邮箱
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content 邮件文本内容,例如xx.jpg ||  xx.xlsx || xx.后缀名
     * @param AnnexName 附件名
     * @param inputStream 文件流
     * @return boolean
     * @author: zhihao
     * @date: 2020/3/27
     */
    @Override
    public boolean sendAnnexMail(String from, String to, String subject, String content, String AnnexName, InputStream inputStream) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            //传递对象进行构建发送附件文本格式
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            ByteArrayDataSource dataSource = new ByteArrayDataSource(inputStream,"application/octet-stream");
            helper.addAttachment(AnnexName, dataSource);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送模板,本质上还是发送HTML邮件，只不过多了绑定变量的过程。
     *
     * @param from 发送者邮箱
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content 模板Html文本内容
     * @return boolean
     * @author: zhihao
     * @date: 2020/3/27
     */
    @Override
    public boolean sendHtmlTemplateMail(String from, String to, String subject, String content) {
        return this.sendHtmlMail(from, to, subject, content);
    }


}
