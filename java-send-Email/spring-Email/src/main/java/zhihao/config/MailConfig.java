package zhihao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import zhihao.sendmail.SendMail;
import zhihao.sendmail.impl.SendMailImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: zhihao
 * @Date: 2020/3/26 17:43
 * @Description: 使用配置类方式代替xml
 * @Versions 1.0
 **/
@Configuration
public class MailConfig {
    /** 属性集 */
    public static Properties properties;

    /** 加载配置文件到属性集 */
    static {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + "mail.properties");
        properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注册发件人
     *
     * @return org.springframework.mail.MailSender
     * @author: zhihao
     * @date: 2020/3/27
     */
    @Bean
    public MailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(properties.getProperty("mail.host"));
        javaMailSender.setPort(Integer.valueOf(properties.getProperty("mail.port")));
        javaMailSender.setUsername(properties.getProperty("mail.username"));
        javaMailSender.setPassword(properties.getProperty("mail.password"));
        javaMailSender.setProtocol(properties.getProperty("mail.protocol"));
        javaMailSender.setDefaultEncoding(properties.getProperty("mail.default-encoding"));
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    @Bean
    public SendMail sendMail(MailSender mailSender) {
        SendMail javaMailSender = new SendMailImpl((JavaMailSender) mailSender);
        return javaMailSender;
    }
}
