package org.xiaoyu.utils.webservice;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class WSClientKit {

  public static Object getClient(Class serviceClass, String url) {
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setAddress(url);
    factory.setServiceClass(serviceClass);
    return factory.create();
  }

  public static Object getClient(Class serviceClass, String url, String userName, String password) {
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setAddress(url);
    factory.setUsername(userName);
    factory.setPassword(password);
    factory.setServiceClass(serviceClass);
    return factory.create();
  }

  public static Object getClient(Class serviceClass, String url,int connectTimeOut, int readTimeOut) {
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setAddress(url);
    factory.setServiceClass(serviceClass);
    Object object = factory.create();
    setClientConfig(object, connectTimeOut, readTimeOut);
    return object;
  }

  public static Object getClient(Class serviceClass, String url,
                                 String userName, String password, int connectTimeOut, int readTimeOut) {
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setAddress(url);
    factory.setUsername(userName);
    factory.setPassword(password);
    factory.setServiceClass(serviceClass);
    Object object = factory.create();
    setClientConfig(object, connectTimeOut, readTimeOut);
    return object;
  }

  private static void setClientConfig(Object object, int connectTimeOut, int readTimeOut) {
    Client proxy = ClientProxy.getClient(object);
    HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
    HTTPClientPolicy policy = new HTTPClientPolicy();
    policy.setConnectionTimeout(connectTimeOut); //连接超时时间(毫秒)
    policy.setReceiveTimeout(readTimeOut);//请求超时时间(毫秒)
    conduit.setClient(policy);
  }
}
