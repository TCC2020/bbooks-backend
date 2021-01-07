package br.edu.ifsp.spo.bulls.common.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>() {{
        add(new ResponseMessageBuilder()
                .code(500)
                .message("500 message")
                .responseModel(new ModelRef("Error"))
                .build());
        add(new ResponseMessageBuilder()
                .code(404)
                .message("Not Found")
                .build());
        add(new ResponseMessageBuilder()
                .code(200)
                .message("OK")
                .build());
    }};
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.edu.ifsp.spo.bulls"))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, responseMessageForGET())
                .globalResponseMessage(RequestMethod.POST, responseMessageForPOST())
                .globalResponseMessage(RequestMethod.PUT, responseMessageForPUT())
                .globalResponseMessage(RequestMethod.DELETE, responseMessageForDELETE())
                ;
    }

    private List<ResponseMessage> responseMessageForGET()
    {
        return responseMessages;
    }

    private List<ResponseMessage> responseMessageForPUT()
    {
        responseMessages.add(new ResponseMessageBuilder()
                .code(409)
                .message("Conflict")
                .build());

        return responseMessages;
    }

    private List<ResponseMessage> responseMessageForDELETE()
    {
        return responseMessages;
    }

    private List<ResponseMessage> responseMessageForPOST()
    {
        responseMessages.add(new ResponseMessageBuilder()
                .code(409)
                .message("Conflict")
                .build());

        return responseMessages;
    }
}
