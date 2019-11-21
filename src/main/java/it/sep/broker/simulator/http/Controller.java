package it.sep.broker.simulator.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class Controller {

    private static final String msisdnRegEx = "subscriberNumber<\\/name>\\s*<value>\\s*<string>(\\d+)";
    private Integer accountValue1 = 0;
    private Pattern p= Pattern.compile(msisdnRegEx);;

    Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    SocketTelnet socketTelnet;
    @Autowired
    Config config;

    private static Map<String,String> lastParams =new HashMap<>();

    @RequestMapping("/help")
    public String help() {
        String html="<html><head></head>\n"+
                "<body>\n" +
                "\t<a href=\"/ok?time=50\">/ok?time=[XXms]</a>\n"+
                "\t<br/><a href=\"/json/test?time=50\">/json/[any_val]?time=[XXms]</a>\n"+
                "\t<br/><a href=\"/oneapi/sms/1/outbound/328999999/requests/?time=50\">/oneapi/sms/1/outbound/[DEST_NUMBER]/requests/?time=[XXms]</a>\n"+
                "\t<br/><a href=\"/cache?msisdn=32899999&itCrm=004&brand=wind&time=50\">/cache?msisdn=[NUMBER]&itCrm=[004|005|006]&brand=[BRAND]&time=[XXms]</a>\n"+
                "\t<br/><a href=\"/xml/CTMC?time=50\">/xml/[CHANNEL]?time=[XXms]</a>\n"+
                "\t<br/><a href=\"/air/request?time=50\">/air/request?time=[XXms]</a>\n"+
                "\t<br/><a href=\"/40400/50\">/40400/[XXms]</a>\n"+
                "</body>\n";
        return html;
    }

    @RequestMapping("/ok")
    public String ok(@RequestParam(name="time",required=false) Long time,HttpServletRequest request) {
        lastParams =new HashMap<>();
        try {
            request.getParameterMap().forEach((key,value)->{
                if(value.length==1) {
                    logger.info("{}={}", key, value[0]);
                    lastParams.put(key, value[0]);
                }else{
                    lastParams.put(key,Arrays.asList(value)+"");
                    logger.info("{}={}",key,value);
                }
            });
            if(time==null){
                time=50L;
            }
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping("/last")
    public String last() {
        String html="<html><head></head>\n"+
                "<body>\n" +
                "\t<table>" +
                "\t\t<thead>" +
                "\t\t\t<tr><th>Chiave</th><th>Valore</th></tr>"+
                "\t\t</thead>"+
                "\t\t</tbody>";
        for(String key:lastParams.keySet()){
            html+=String.format("\t\t\t<tr><td>%s</td><td>%s</td></tr>",key,lastParams.get(key));
        }
        html+=  "\t\t</tbody>"+
                "\t</table>"+
                "</body>\n";
        return html;
    }

    @RequestMapping(value ="/json/*", produces = "application/json")
    public String json(@RequestParam(name="time",required=false) Long time) {
        try {
            if(time==null){
                time=50L;
            }
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    @RequestMapping(value ="/oneapi/sms/1/outbound/{senderAddress}/requests/", produces = "application/json")
    public String oneapi(@PathVariable("senderAddress") String senderAddress,
            @RequestParam(name="time",required=false) Long time) {
        try {
            if(time==null){
                time=150L;
            }
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "{}";
    }



    @RequestMapping(value ="/cache", produces = "application/json", method = RequestMethod.GET)
    public String cache(@RequestParam("msisdn") String msisdn,@RequestParam(value = "itCrm",required = false,defaultValue = "004") String itCrm,
                        @RequestParam(value = "brand",required = false) String brand,
                        @RequestParam(name="time",required=false) Long time) {
        try {
            if(time==null){
                time=1L;
            }
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("msisdn={},itCrm={},brand={}",msisdn,itCrm,brand);
        if("wind".equalsIgnoreCase(brand) || "tre".equalsIgnoreCase(brand)){
            brand="\""+brand+"\"";
        } else if("005".equals(itCrm)){
            brand="\"wind\"";
        } else{
            brand=null;
        }
        return "{\"msisdn\":\""+msisdn+"\",\"it_crm\":\""+itCrm+"\", \"brand\":"+brand+"}";
    }


    @RequestMapping(value = "/xml/{channel}",produces = "text/xml")
    public String xml(@PathVariable("channel") String channel,
                      @RequestParam(name="time",required=false) Long time) {
        Integer random=(int)(Math.random() * (2000000000 - 1000000) + 1000000);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyymmddHHMMss");
        try {
            if(time==null){
                time=150L;
            }
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format(config.getXml(),random,channel,sdf.format(new Date()));
    }



    static Long tid=0L;

    @RequestMapping(value = "/40400/{time}",produces = "text/xml")
    public String r40400(@PathVariable(name="time",required=false) Long time) {
        tid=tid+1;
        try {
            if(time==null){
                time=50L;
            }
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format(config.getXml40400(),tid);
    }




    @RequestMapping("/telnet")
    public String telnet() {
        String result="";
        result+=socketTelnet.listen("10.139.70.32",8001)+"\n";
        result+=socketTelnet.listen("10.139.70.33",8001)+"\n";
        result+=socketTelnet.listen("10.139.65.83",26000)+"\n";
        result+=socketTelnet.listen("10.139.65.83",27010)+"\n";
        result+= socketTelnet.listen("10.139.65.83",5002)+"\n";
        result+=socketTelnet.listen("10.139.65.83",38080)+"\n";

        result+=socketTelnet.listen("10.139.70.42",26000)+"\n";
        result+= socketTelnet.listen("10.139.70.43",26000)+"\n";
        result+= socketTelnet.listen("10.139.70.44",26000)+"\n";
        result+= socketTelnet.listen("10.139.70.45",26000)+"\n";
        result+=socketTelnet.listen("10.139.70.46",26000)+"\n";
        result+=socketTelnet.listen("10.139.70.47",26000)+"\n";

        result+=socketTelnet.listen("10.139.70.42",27010)+"\n";
        result+=socketTelnet.listen("10.139.70.43",27010)+"\n";
        result+= socketTelnet.listen("10.139.70.44",27010)+"\n";
        result+=socketTelnet.listen("10.139.70.45",27010)+"\n";
        result+=socketTelnet.listen("10.139.70.46",27010)+"\n";
        result+=socketTelnet.listen("10.139.70.47",27010)+"\n";

        result+= socketTelnet.listen("10.139.70.42",5002)+"\n";
        result+= socketTelnet.listen("10.139.70.43",5002)+"\n";
        result+= socketTelnet.listen("10.139.70.44",5002)+"\n";
        result+= socketTelnet.listen("10.139.70.45",5002)+"\n";
        result+=socketTelnet.listen("10.139.70.46",5002)+"\n";
        result+= socketTelnet.listen("10.139.70.47",5002)+"\n";

        result+=socketTelnet.listen("10.139.70.42",38080)+"\n";
        result+= socketTelnet.listen("10.139.70.43",38080)+"\n";
        result+=socketTelnet.listen("10.139.70.44",38080)+"\n";
        result+=socketTelnet.listen("10.139.70.45",38080)+"\n";
        result+= socketTelnet.listen("10.139.70.46",38080)+"\n";
        result+= socketTelnet.listen("10.139.70.47",38080)+"\n";

        result+=socketTelnet.listen("10.139.113.31",8888)+"\n";
        result+= socketTelnet.listen("10.139.113.32",8888)+"\n";
        result+= socketTelnet.listen("10.139.112.20",8888)+"\n";


        return result;
    }



    @RequestMapping("/air/request")
    protected void request(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String reqBody = getRequestBody(request);
        Matcher m = p.matcher(reqBody);
        String subscriberNumber = null;
        if(m.find())
            subscriberNumber = m.group(1);

        List<String> noCrediMSISDNList = config.noCrediMSISDNList;

        Long timeout = config.timeout;
        if(timeout != null && timeout > 0) {
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Integer accountValue = config.accountValue;

        String filename = "AirResponseTemplate.xml";
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        PrintWriter writer = response.getWriter();
        if (is != null) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String body = "";
            String text = "";
            while ((text = reader.readLine()) != null) {
                body = body + text;
            }

            body = body.replace("${responseCode}", "0");

            if(noCrediMSISDNList != null && noCrediMSISDNList.size() > 0 && accountValue != null && accountValue >= 0) {
                if(noCrediMSISDNList.contains(subscriberNumber))
                    body = body.replace("${accountValue1}", "0");
                else
                    body = body.replace("${accountValue1}", accountValue.toString());
            } else if(accountValue != null && accountValue >= 0) {
                body = body.replace("${accountValue1}", accountValue.toString());
            } else {
                body = body.replace("${accountValue1}", accountValue1.toString());

                if(accountValue1 == 0)
                    accountValue1 = 1000;
                else
                    accountValue1 = 0;
            }
            writer.write(body);
        }
    }


    @RequestMapping("/multipleHeader")
    protected void multipleHeader(HttpServletRequest request,
    HttpServletResponse response) {
        String test = request.getHeader("test");
        System.out.println("getHeader: " + test);
        Enumeration<String> tests = request.getHeaders("test");

        while (tests.hasMoreElements()) {
            test = tests.nextElement();
            System.out.println("getHeaders " + test);
        }


        Enumeration<String> names = request.getHeaderNames();

        while (names.hasMoreElements()) {
            String name = names.nextElement();
            System.out.println("name " + name);
        }


    }

    private String getRequestBody(HttpServletRequest request) {
        StringBuffer body = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                body.append(line);
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.error("STACK:",e);
        }
        return body.toString();
    }

    @Bean
    public ServletRegistrationBean<MultipleHeaderServlet> servletRegistrationBean(){
        return new ServletRegistrationBean<MultipleHeaderServlet>(new MultipleHeaderServlet(),"/test/*");
    }

}
