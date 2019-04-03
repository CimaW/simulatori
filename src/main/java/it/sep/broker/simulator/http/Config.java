package it.sep.broker.simulator.http;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("it.sep.broker.simulator.http")
public class Config {

    org.slf4j.Logger logger= LoggerFactory.getLogger("Config");

    @Value("${sep.broker.simulator.xml}")
    private String xml;

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}
