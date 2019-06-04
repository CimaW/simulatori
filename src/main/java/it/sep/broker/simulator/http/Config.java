package it.sep.broker.simulator.http;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.List;

@Configuration
@ComponentScan("it.sep.broker.simulator.http")
public class Config {

    org.slf4j.Logger logger= LoggerFactory.getLogger("Config");

    @Value("${sep.broker.simulator.xml}")
    private String xml;
    @Value("${sep.broker.simulator.air.noCrediMSISDNList}")
    public List<String> noCrediMSISDNList;

    @Value("${sep.broker.simulator.air.timeout}")
    public Long timeout;

    @Value("${sep.broker.simulator.air.accountValue}")
    public Integer accountValue;

    @Value("${sep.broker.simulator.xml40400}")
    private String xml40400;

    public String getXml40400() {
        return xml40400;
    }

    public void setXml40400(String xml40400) {
        this.xml40400 = xml40400;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }


}
