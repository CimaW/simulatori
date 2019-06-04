package it.sep.broker.simulator.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

@Service
public class SocketTelnet {


    Logger logger = LoggerFactory.getLogger(SocketTelnet.class);

    public String listen(String ip,Integer port)  {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip,port), 10000);
            logger.info("ip {} port {} is open!",ip,port);
            return ip+":"+port+": OK";
        }
        catch (SocketTimeoutException e) {
            logger.info("ip {} port {} is closed!",ip,port);
            return ip+":"+port+": KO - closed";
        }
        catch (IOException e) {
            logger.error("ip {} port {} error {}",ip,port,e.getMessage());
            return ip+":"+port+": KO - "+e.getMessage();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args) throws Exception {
        SocketTelnet socketTelnet=new SocketTelnet();
        socketTelnet.listen("10.139.70.32",8001);
        socketTelnet.listen("10.139.70.33",8001);
        socketTelnet.listen("10.139.65.83",26000);
        socketTelnet.listen("10.139.65.83",27010);
        socketTelnet.listen("10.139.65.83",5002);
        socketTelnet.listen("10.139.65.83",38080);

        socketTelnet.listen("10.139.70.42",26000);
        socketTelnet.listen("10.139.70.43",26000);
        socketTelnet.listen("10.139.70.44",26000);
        socketTelnet.listen("10.139.70.45",26000);
        socketTelnet.listen("10.139.70.46",26000);
        socketTelnet.listen("10.139.70.47",26000);

        socketTelnet.listen("10.139.70.42",27010);
        socketTelnet.listen("10.139.70.43",27010);
        socketTelnet.listen("10.139.70.44",27010);
        socketTelnet.listen("10.139.70.45",27010);
        socketTelnet.listen("10.139.70.46",27010);
        socketTelnet.listen("10.139.70.47",27010);

        socketTelnet.listen("10.139.70.42",5002);
        socketTelnet.listen("10.139.70.43",5002);
        socketTelnet.listen("10.139.70.44",5002);
        socketTelnet.listen("10.139.70.45",5002);
        socketTelnet.listen("10.139.70.46",5002);
        socketTelnet.listen("10.139.70.47",5002);

        socketTelnet.listen("10.139.70.42",38080);
        socketTelnet.listen("10.139.70.43",38080);
        socketTelnet.listen("10.139.70.44",38080);
        socketTelnet.listen("10.139.70.45",38080);
        socketTelnet.listen("10.139.70.46",38080);
        socketTelnet.listen("10.139.70.47",38080);

        socketTelnet.listen("10.139.113.31",8888);
        socketTelnet.listen("10.139.113.32",8888);
        socketTelnet.listen("10.139.112.20",8888);

    }
}