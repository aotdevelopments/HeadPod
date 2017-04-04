package com.siestasystemheadpod.headpodv10.mail;

/**
 * Created by Michael on 08/09/2016.
 * Java mail o javax mail
 */

//Posible solución
//https://www.creados.com/es/blog/leer_mi_correo_corporativo_a_traves_de_gmail/ //-> no ha hecho falta usarlo.
//http://www.journaldev.com/2532/javamail-example-send-mail-in-java-smtp

//API
//https://javamail.java.net/nonav/docs/api/com/sun/mail/smtp/package-summary.html

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.RememberPasswordActivity;

import java.util.Date;
import java.util.Properties;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;




public class Mail extends javax.mail.Authenticator {


    private String _user;
    private String _pass;

    private String[] _to;
    private String _from;

    private String _port;
    private String _sport;

    private String _host;

    private String _subject;
    private String _body;

    private boolean _auth;

    private boolean _debuggable;

    private Multipart _multipart;


    public Mail() {
       // _host = "smtp.gmail.com"; // default smtp server
        // _port = "465"; // default smtp port
        // _sport = "465"; // default socketfactory port

        //_host = "smtp.strato.com"; // default smtp server
        //_port = "465"; // default smtp port
        //_sport = "465"; // default socketfactory port

        //Puerto = 587 TLS //props.put("mail.smtp.starttls.enable", "true"); //enable TLS
        _host = "smtp.strato.com"; // default smtp server
        _port = "587"; // default smtp port

        //_host="pop3.strato.de";
        //_port="995";
        //_sport="995";

        //_host="imap.strato.de";
        //_port="993";
        //_sport="993";

        _user = "webmaster@kinesiopod.com"; // username //webmaster@kinesiopod.com
        _pass = "aot14141414"; // password //aot14141414
        _from = "webmaster@kinesiopod.com"; // email sent from
        _subject = ""; // email subject
        _body = ""; // email body

        _debuggable = false; // debug mode on or off - default off
        _auth = true; // smtp authentication - default on

        _multipart = new MimeMultipart();

        // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    public Mail(String user, String pass) {
        this();

        _user = user;
        _pass = pass;
        //setFrom("webmaster@kinesiopod.com");
    }

    public boolean send() throws MessagingException {
        Properties props = _setProperties();

        if(!_user.equals("") && !_pass.equals("") && _to.length > 0 && !_from.equals("") && !_subject.equals("") && !_body.equals("")) {
            Session session = Session.getInstance(props, this);

            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(_from));

            InternetAddress[] addressTo = new InternetAddress[_to.length];
            for (int i = 0; i < _to.length; i++) {
                addressTo[i] = new InternetAddress(_to[i]);
            }
            msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

            msg.setSubject(_subject);
            msg.setSentDate(new Date());

            // setup message body
           // BodyPart messageBodyPart = new MimeBodyPart();
           // messageBodyPart.setText(_body);

            try {
               // InternetHeaders headers = new InternetHeaders();
               // headers.addHeader("Content-type", "text/html; charset=UTF-8");
                String html =  _body; //+  "\n\n<a href='http://www.example.com/activar'>activar cuentaaaa</a>";
               // MimeBodyPart body = new MimeBodyPart(headers, html.getBytes("UTF-8"));
                MimeBodyPart body = new MimeBodyPart();
                body.setText(html,"UTF-8", "html");


                _multipart.addBodyPart(body);

//            _multipart.addBodyPart(messageBodyPart);

                // Put parts in message
                msg.setContent(_multipart,"text/html" );



                // send email
                Transport.send(msg);
            }
            catch (Exception e)
            {
                Log.e("errorMail", "send: ",e );
                return false;
            }

            return true;

        } else {

            Log.e("error_credendial", "send: " );
            return false;
        }
    }

    public void addAttachment(String filename) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);

        _multipart.addBodyPart(messageBodyPart);
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(_user, _pass);
    }


    private Properties _setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.host", _host);

        if(_debuggable) {
            props.put("mail.debug", "true");
        }

        if(_auth) {
            props.put("mail.smtp.auth", "true");
        }

        //Codigo para cifrado SSL
        //******************************************************************************
/*
        props.put("mail.smtp.port", _port);
        props.put("mail.smtp.socketFactory.port", _sport);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
*/
       // props.put("mail.smtp.ssl.enable", "true");
        //**********************************************************************************

        //Envio a traves de cifrado TLS
        //************************************************************
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.port", _port);
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        //****************************************************************

        return props;
    }

    // the getters and setters
    public String getBody() {
        return _body;
    }

    public void setBody(String _body) {
        this._body = _body;
    }

    // more of the getters and setters …..


    public void setTo(String[] toArr) {
        this._to = toArr;
    }

    public void setFrom(String string) {
        this._from = string;
    }

    public void setSubject(String string) {
        this._subject = string;
    }

    public String addColor(String msg, Context context, int resourceColor) {

        String hexColor = Integer.toHexString(ContextCompat.getColor(context, resourceColor) & 0x00ffffff);
        return  "<FONT COLOR=\"#" + hexColor + "\">" + msg + "</FONT>";

    }

}
