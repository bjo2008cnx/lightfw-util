package org.lightfw.utilx.text.mail;

import org.lightfw.constant.GlobalConstant;
import org.lightfw.util.lang.ExceptionUtil;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtil {
    public static String DEFAULT_MAIL_ENCODING = GlobalConstant.Defaults.DEFAULT_ENCODING;

    private String mailSmtpHost;
    private String sendMailUser;
    private String sendMailPassword;
    private String mailFrom;

    Transport transport = null;

    /**
     * @param mailConfig [0]mailSmtpHost--mail1.quickbundle.org, [1]sendMailUser--test,
     *                   [2]sendMailPassword--******,
     *                   [3]mailFrom--noreply@quickbundle.org
     */
    public MailUtil(String[] mailConfig) {
        mailSmtpHost = mailConfig[0];
        sendMailUser = mailConfig[1];
        sendMailPassword = mailConfig[2];
        mailFrom = mailConfig[3];
    }

    /**
     * @param mailto   收件人
     * @param subject  标题
     * @param bodyText 正文文本, 可以为NULL
     * @param bodyHtml 正文html, 可以为NULL
     * @param aAffix   附件数组{{"显示名", "附件"}, {"a", "/a.jpg"}}
     */
    public void send(String mailto, String subject, String bodyText, String bodyHtml, String[][] aAffix) {
        try {
            //1、设置邮件服务器，创建session
            String encoding = MimeUtility.mimeCharset(DEFAULT_MAIL_ENCODING);
            Properties props = new Properties();

            props.put("mail.smtp.localhost", mailSmtpHost);
            props.put("mail.smtp.host", mailSmtpHost);
            props.put("mail.smtp.auth", "true"); // 这样才能通过验证
            Session session = Session.getDefaultInstance(props);  // Get session
            session.setDebug(true);  // watch the mail commands go by to the mail server

            //2、创建邮件MimeMessage
            MimeMessage mes = new MimeMessage(session);
            mes.setFrom(new InternetAddress(mailFrom));
            mes.addRecipients(RecipientType.TO, InternetAddress.parse(mailto, false));
            mes.setSubject(subject, encoding);

            //3,填充邮件的正文（文本、html、附件）
            handleBody(bodyText, bodyHtml, aAffix, encoding, mes);
            if (transport == null || !transport.isConnected()) {
                transport = session.getTransport("smtp");
                transport.connect(props.getProperty("mail.smtp.host"), sendMailUser, sendMailPassword);
            }
            transport.sendMessage(mes, mes.getAllRecipients());
        } catch (MessagingException e) {
            throw ExceptionUtil.transform(e);
        } catch (IOException e) {
            throw ExceptionUtil.transform(e);
        }
    }

    private void handleBody(String bodyText, String bodyHtml, String[][] aAffix, String encoding, MimeMessage mes) throws MessagingException, UnsupportedEncodingException {
        MimeMultipart multipart = new MimeMultipart(); // 设置邮件正文部分
        multipart.setSubType("mixed");  // 文本、超文本、附件形式

        // 设置邮件正文文本
        if (bodyText != null && bodyText.length() > 0) {
            MimeBodyPart txtbodyPart = new MimeBodyPart();
            txtbodyPart.setText(bodyText, encoding);
            multipart.addBodyPart(txtbodyPart);
        }

        // 设置邮件正文HTML部分
        if (bodyHtml != null && bodyHtml.length() > 0) {
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(bodyHtml, "text/html;charset=" + encoding);
            multipart.addBodyPart(htmlBodyPart);
        }

        // 发送附件
        if (aAffix != null && aAffix.length > 0) {
            for (int i = 0; i < aAffix.length; i++) {
                MimeBodyPart affixPart = new MimeBodyPart();
                // 附件的设置
                affixPart.setDataHandler(new DataHandler(new FileDataSource(aAffix[i][1])));
                affixPart.setFileName(MimeUtility.encodeText(aAffix[i][0], encoding, "Q"));
                multipart.addBodyPart(affixPart);
            }
        }
        mes.setContent(multipart); // 加入message
    }

    /**
     * 显式关闭连接
     */
    public void close() {
        if (transport == null) {
            return;
        }
        try {
            transport.close();
        } catch (MessagingException e) {
            throw ExceptionUtil.transform(e);
        }
    }
}