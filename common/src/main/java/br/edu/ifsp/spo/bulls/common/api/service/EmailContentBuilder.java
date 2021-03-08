package br.edu.ifsp.spo.bulls.common.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.ExchangeTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailContentBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public EmailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build( String text, String title, String action, String link, ExchangeTO exchangeTO) {
        Context context  = new Context();
        context.setVariable("text", text);
        context.setVariable("title", title);
        context.setVariable("action", action);
        context.setVariable("link", link);
        context.setVariable("exchangeTO", exchangeTO);

        return templateEngine.process("template-email", context);
    }

}
