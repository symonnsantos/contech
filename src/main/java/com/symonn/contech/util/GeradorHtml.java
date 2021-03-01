package com.symonn.contech.util;

import org.springframework.stereotype.Component;

@Component
public class GeradorHtml {

    public String header(){
        StringBuilder hdr = new StringBuilder();
        hdr.append("<h3 align=\"center\">Atenção, você tem contas a pagar no dia de hoje!</h3><br/>\n");
        hdr.append("<table width=50% border=\"3px\" align=\"center\">\n");
        hdr.append("<thead>\n");
        hdr.append("<tr bgcolor=green>\n");
        hdr.append("<th scope=\"col\">Descrição</th>\n");
        hdr.append("<th scope=\"col\">Valor</th>\n");
        hdr.append("<th scope=\"col\">Data Vencimento</th>\n");
        hdr.append("</tr>\n");
        hdr.append("</thead>\n");

        hdr.append("<tbody>");

        return hdr.toString();
    }

    public String footer(){
        StringBuilder ftr = new StringBuilder();

        ftr.append("</tbody>");

        ftr.append("</table>");

        return ftr.toString();
    }
}
