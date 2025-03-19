package com.saec.formtic.model.mail;

import org.springframework.stereotype.Component;

@Component
public class MailDesigns {

    public String sendSimpleMail() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <style>\n" +
                "        body {\n" +
                "            background-color: #e9ecef;\n" +
                "            margin: 0;\n" +
                "            font-family: 'Helvetica Neue', Arial, sans-serif;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            max-width: 700px;\n" +
                "            margin: 25px auto;\n" +
                "            background: #ffffff;\n" +
                "            border-radius: 10px;\n" +
                "            border: 1px solid #ccc;\n" +
                "            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .header {\n" +
                "            background-color: #0190ba;\n" +
                "            color: white;\n" +
                "            text-align: center;\n" +
                "            padding: 20px;\n" +
                "            font-size: 26px;\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            line-height: 1.6;\n" +
                "            color: black;\n" +
                "            font-size: 18px;\n" +
                "        }\n" +
                "\n" +
                "        .field {\n" +
                "            margin-bottom: 15px;\n" +
                "            padding: 10px;\n" +
                "            border-left: 5px solid #0190ba;\n" +
                "            background: #f7f7f7;\n" +
                "            border-radius: 5px;\n" +
                "            transition: background 0.3s;\n" +
                "        }\n" +
                "\n" +
                "        .label-container {\n" +
                "            text-align: center;\n" +
                "            width: 100%;\n" +
                "            font-weight: bold;\n" +
                "            color: #0190ba;\n" +
                "            font-size: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            padding: 15px;\n" +
                "            font-size: 14px;\n" +
                "            color: #666;\n" +
                "            background: #f7f7f7;\n" +
                "            border-top: 1px solid #ddd;\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">Solicitud de recuperación de contraseña</div>\n" +
                "        <div class=\"content\">\n" +
                "            <div class=\"field\">\n" +
                "                <p>Te enviamos esta notificación para garantizar la privacidad y seguridad de tu cuenta de\n" +
                "                    <strong>IPM</strong>. Si\n" +
                "                    has solicitado la recuperación de contraseña del empleado:\n" +
                "                </p>\n" +
                "                <div class=\"label-container\">\n" +
                "                    <label>Santander verdayes Omar de jesus</label>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"footer\">\n" +
                "            Si no has solicitado este cambio, ignora este correo.\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";
    }



}
