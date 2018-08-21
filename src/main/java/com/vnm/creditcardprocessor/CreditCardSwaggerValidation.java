package com.vnm.creditcardprocessor;

        import com.atlassian.oai.validator.springmvc.SwaggerValidationFilter;
        import com.atlassian.oai.validator.springmvc.SwaggerValidationInterceptor;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.core.io.Resource;
        import org.springframework.core.io.support.EncodedResource;
        import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
        import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

        import javax.servlet.Filter;
        import java.io.IOException;

@Configuration
public class CreditCardSwaggerValidation extends WebMvcConfigurerAdapter {

    private  SwaggerValidationInterceptor swaggerValidationInterceptor;

    /**
     * @param swaggerApi the {@link Resource} to your Swagger schema
     */
    @Autowired
    public void SwaggerRequestValidationConfig(@Value("classpath:swagger_ccp.json") final Resource swaggerApi) throws IOException {
        final EncodedResource swaggerResource = new EncodedResource(swaggerApi, "UTF-8");
        this.swaggerValidationInterceptor = new SwaggerValidationInterceptor(swaggerResource);

    }

    @Bean
    public Filter swaggerValidationFilter() {
        return new SwaggerValidationFilter();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(swaggerValidationInterceptor);
    }
}