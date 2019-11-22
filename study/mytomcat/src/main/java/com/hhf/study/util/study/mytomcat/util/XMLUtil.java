package com.hhf.study.util.study.mytomcat.util;

import com.hhf.study.util.study.mytomcat.model.Servlet;
import com.hhf.study.util.study.mytomcat.model.ServletMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/1310:24
 */
public class XMLUtil {

    public static Map<Integer,Map<String,Object>> parseWebXML()throws Exception{
        Map<Integer, Map<String, Object>> result = new HashMap<Integer, Map<String,Object>>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db =dbf.newDocumentBuilder();
        InputStream in =XMLUtil.class.getClassLoader().getResourceAsStream("web.xml");
        Document document = db.parse(in);
        Element root = document.getDocumentElement();
        NodeList xmlNodes = root.getChildNodes();
        for(int i = 0; i < xmlNodes.getLength(); i++) {
            Node config = xmlNodes.item(i);
            //判断是否为元素节点
            if(config!=null&&config.getNodeType()==Node.ELEMENT_NODE){
                String nodeName1 = config.getNodeName();
                if("servlet".equals(nodeName1)) {
                    Map<String, Object> servletMaps = null;
                    if(result.containsKey(0)) {
                        servletMaps = result.get(0);
                    }else {
                        servletMaps = new HashMap<String, Object>();
                    }
                    NodeList childNodes = config.getChildNodes();
                    //创建servlet实体类准备接受数据
                    Servlet servlet = new Servlet();
                    for(int j = 0; j < childNodes.getLength(); j++){
                        Node node = childNodes.item(j);
                        if(node!=null&&node.getNodeType()==Node.ELEMENT_NODE){
                            //读取servlet-name和servlet-class
                            String nodeName2 = node.getNodeName();
                            String textContent=node.getTextContent();
                            if(nodeName2.equals("servlet-name")){
                                servlet.setName(textContent);
                            }else if(nodeName2.equals("servlet-class")) {
                                servlet.setClazz(textContent);
                            }
                        }
                    }
                    //结果放到Map中
                    servletMaps.put(servlet.getName(), servlet);
                    result.put(0,servletMaps);
                }else if("servlet-mapping".equals(nodeName1)){
                    Map<String, Object> servletMappingMaps = null;
                    if(result.containsKey(1)) {
                        servletMappingMaps = result.get(1);
                    }else {
                        servletMappingMaps = new HashMap<String, Object>();
                    }
                    //获取元素节点的所有子节点
                    NodeList childNodes = config.getChildNodes();
                    //创建实体类
                    ServletMapping servletMapping = new ServletMapping();

                    for(int j = 0; j < childNodes.getLength(); j++) {
                        Node node = childNodes.item(j);
                        if(node!=null && node.getNodeType()==Node.ELEMENT_NODE) {
                            String nodeName2 = node.getNodeName();
                            String textContent = node.getTextContent();
                            if(nodeName2.equals("servlet-name")) {
                                servletMapping.setName(textContent);
                            }else if (nodeName2.equals("url-pattern")) {
                                servletMapping.setUrl(textContent);
                            }
                        }
                    }
                    servletMappingMaps.put(servletMapping.getUrl(), servletMapping);
                    result.put(1, servletMappingMaps);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try{
            System.out.println(parseWebXML());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
