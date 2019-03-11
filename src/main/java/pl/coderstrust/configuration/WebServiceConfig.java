package pl.coderstrust.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

  @Bean
  public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(servlet, "/soap/invoices/*");
  }

  @Bean(name = "invoices")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema invoicesSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("InvoicesPort");
    wsdl11Definition.setLocationUri("/soap/invoices");
    wsdl11Definition.setTargetNamespace("https://github.com/CodersTrustPL/project-8-basia-daniel-maksym");
    wsdl11Definition.setSchema(invoicesSchema);
    return wsdl11Definition;
  }

  @Bean
  public XsdSchema invoicesSchema() {
    return new SimpleXsdSchema(new ClassPathResource("invoicesSchema.xsd"));
  }
}
