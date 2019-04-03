package it.sep.broker.simulator.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class HelloController {

    @Autowired
    Config config;

    @RequestMapping("/ok")
    public String ok() {
        return "OK";
    }


    @RequestMapping("/xml/${channel}")
    public String xml(@PathVariable("channel") String channel) {
        Integer random=(int)(Math.random() * (2000000000 - 1000000) + 1000000);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyymmddHHMMss");
        return String.format(config.getXml(),random,channel,sdf.format(new Date()));
    }
}