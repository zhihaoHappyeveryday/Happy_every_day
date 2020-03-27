package com.zhihao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import zhihao.config.MailConfig;
import zhihao.sendmail.SendMail;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.io.File;
import java.io.FileInputStream;

/**
 * @Author: zhihao
 * @Date: 2020/3/26 17:35
 * @Description:
 * @Versions 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MailConfig.class)
public class SendMailTest {

    @Autowired
    private SendMail sendMail;

    @Test
    public void sendText(){
        String from = MailConfig.properties.getProperty("mail.username");
         sendMail.sendTextMail(from, from, "这是个普通邮件", "测试发送");
    }

    @Test
    public void sendHtml(){
        String from = MailConfig.properties.getProperty("mail.username");
        String html = "<html><body><img src='https://s1.ax1x.com/2020/03/16/8JHaM4.jpg'/>这个是个图片</body></html>";
         sendMail.sendHtmlMail(from, from, "Html格式", html);
    }

    @Test
    public void sendAnnex() throws Exception {
        String from = MailConfig.properties.getProperty("mail.username");
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\hhhh.xlsx"));
//         sendMail.sendAnnexMail(from, from, "这是个普通邮件", "图片邮件","我的.jpg",inputStream);
         sendMail.sendAnnexMail(from, from, "这是个附件邮件", "图片邮件","我的.xlsx",inputStream);
    }

    @Test
    public void sendHtmlTemplateMail() throws Exception {
        String from = MailConfig.properties.getProperty("mail.username");
        Context context = new Context();
        context.setVariable("name", "张韶涵");
        //创建模板
        TemplateEngine templateEngine = new TemplateEngine();
        //设置模板解析器
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setTemplateMode("HTML");
        //文件夹前缀
        templateResolver.setPrefix("src/main/resources/");
        //后缀
        templateResolver.setSuffix(".html");
        templateEngine.setTemplateResolver(templateResolver);
        String process = templateEngine.process("emailTemplate", context);
        sendMail.sendHtmlTemplateMail(from, from, "html模板邮件",process );
    }

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Test
    public void incomingMail() throws Exception {
        //1.获取会话
        Session session = javaMailSender.getSession();
        //2.通过会话获取Store
        Store store = session.getStore();
        //3.连上邮件服务器  账号----密码(QQ邮箱使用授权码)
        store.connect(javaMailSender.getUsername(),MailConfig.properties.getProperty("mail.password"));
        //4.获取收件箱的邮件夹
        Folder folder = store.getFolder("inbox");
        //5.打开只读邮件夹,还有很多操作
        folder.open(Folder.READ_ONLY);
        //6.获取邮件
        Message[] messages = folder.getMessages();
        for (Message message : messages) {
            String subject = message.getSubject();
            Object content = message.getContent();
            System.out.println("主题"+subject);
            System.out.println("内容"+content);
        }
        folder.close();
        store.close();
    }
}
