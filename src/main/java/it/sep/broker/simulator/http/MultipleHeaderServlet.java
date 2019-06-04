package it.sep.broker.simulator.http;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class MultipleHeaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response){
        String test=request.getHeader("test");
        System.out.println("test"+test);
        Enumeration<String> tests=request.getHeaders("test");

        while(tests.hasMoreElements()){
            test=tests.nextElement();
            System.out.println("test"+test);
        }

    }


}
