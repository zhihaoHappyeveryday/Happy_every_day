package zhihao.sendmail;

import java.io.InputStream;

/**
 * 发送邮件
 *
 * @author: zhihao
 * @date: 2020/3/27
 */
public interface SendMail {

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
    boolean sendTextMail(String from, String to,String subject, String content);

    /**
     * 发送带Html文本邮件
     *
     * @param from 发送者邮箱
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content 带Html格式文本邮件内容
     * @return boolean
     * @author: zhihao
     * @date: 2020/3/27
     */
    boolean sendHtmlMail(String from, String to,String subject, String content);

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
    boolean sendAnnexMail(String from, String to, String subject, String content,String AnnexName,InputStream inputStream);

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
    boolean sendHtmlTemplateMail(String from, String to, String subject, String content);



}
