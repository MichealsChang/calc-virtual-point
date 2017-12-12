package com.iot.calcvirtualpoint.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLFileUtils {
    
    /**
     <?xml version="1.0" encoding="GB2312"?>
    <学生花名册>
         <学生 性别 = "男">
             <姓名>李华</姓名>
             <年龄>14</年龄>
         </学生>
         <学生 性别 = "男">
             <姓名>张三</姓名>
             <年龄>16</年龄> 
         </学生>
    </学生花名册>
     */

    public static Document readXMLDomFile(String file) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        try {
            builder = dbf.newDocumentBuilder();
            doc = builder.parse(file); // 获取到xml文件
        } catch (Exception e1) {
            LogUtils.error(e1.getMessage(), e1);
        }
        return doc;
    }
    
    public static Document readXMLDomFile(File file) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        try {
            builder = dbf.newDocumentBuilder();
            doc = builder.parse(file); // 获取到xml文件
        } catch (Exception e1) {
            LogUtils.error(e1.getMessage(), e1);
        }
        return doc;
    }
    
    @SuppressWarnings({ "unused", "rawtypes" })
	private Vector readXMLFile(String file) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        try {
            builder = dbf.newDocumentBuilder();
            doc = builder.parse(file); // 获取到xml文件
        } catch (Exception e1) {
            LogUtils.error(e1.getMessage(), e1);
            return new Vector();
        }
        // 下面开始读取
        Element root = doc.getDocumentElement(); // 获取根元素
        NodeList students = root.getElementsByTagName("学生");
        Vector students_Vector = new Vector();
        for (int i = 0; i < students.getLength(); i++) {
            // 一次取得每一个学生元素
            Element ss = (Element) students.item(i);
            // 创建一个学生的实例
//            student stu = new student();
//            stu.setSex(ss.getAttribute("性别"));
            NodeList names = ss.getElementsByTagName("姓名");
            Element e = (Element) names.item(0);
            Node t = e.getFirstChild();
//            stu.setName(t.getNodeValue());

            NodeList ages = ss.getElementsByTagName("年龄");
            e = (Element) ages.item(0);
            t = e.getFirstChild();
//            stu.setAge(Integer.parseInt(t.getNodeValue()));

//            students_Vector.add(stu);
        }
        return students_Vector;
    }

    

    // 将Document内容 写入XML字符串并返回
    private String callWriteXmlString(Document doc, String encoding) {
        try {
            Source source = new DOMSource(doc);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            OutputStreamWriter write = new OutputStreamWriter(outStream);
            Result result = new StreamResult(write);

            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setOutputProperty(OutputKeys.ENCODING, encoding);

            xformer.transform(source, result);
            return outStream.toString();

        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * 功能：生成XML格式的字符串
     */
    public String writeXMLString() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = dbf.newDocumentBuilder();
        } catch (Exception e) {
        }
        Document doc = builder.newDocument();

        Element root = doc.createElement("学生花名册");
        doc.appendChild(root); // 将根元素添加到文档上

        // 获取学生信息
        /*
        for (int i = 0; i < students_Vector.size(); i++) {
            student s = (student) students_Vector.get(i);
            // 创建一个学生
            Element stu = doc.createElement("学生");
            stu.setAttribute("性别", s.getSex());
            root.appendChild(stu);// 添加属性

            // 创建文本姓名节点
            Element name = doc.createElement("姓名");
            stu.appendChild(name);
            Text tname = doc.createTextNode(s.getName());
            name.appendChild(tname);

            // 创建文本年龄节点
            Element age = doc.createElement("年龄");
            stu.appendChild(age); // 将age添加到学生节点上
            Text tage = doc.createTextNode(String.valueOf(s.getAge()));
            age.appendChild(tage); // 将文本节点放在age节点上
        }
        */
        try {
            String result = callWriteXmlString(doc, "gb2312");
            return result;
        } catch (Exception e) {
            LogUtils.error(e.getMessage(),e);
            return null;
        }
    }
    
    public static boolean Move(File srcFile, String destPath) {
        // Destination directory
        File dir = new File(destPath);
        // Move file to new directory
        boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));
        return success;
    }

    public static boolean Move(String srcFile, String destPath) {
        // File (or directory) to be moved
        File file = new File(srcFile);
        // Destination directory
        File dir = new File(destPath);
        // Move file to new directory
        boolean success = file.renameTo(new File(dir, file.getName()));
        return success;
    }

    @SuppressWarnings("resource")
	public static void Copy(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
	public static void Copy(File oldfile, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            // File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        }
    }

    /**
     * 主函数
     */
    public static void main(String args[]) {
//        String str = "xml\\student.xml";
        /*
        XMLFileUtils t = new XMLFileUtils();
        System.out.println("解析原始XML文件：");
        try {
            Vector v = t.readXMLFile(str);
            Iterator it = v.iterator();
            while (it.hasNext()) {
                student s = (student) it.next();
                System.out.println(s.getName() + "\t" + s.getAge() + "\t"
                        + s.getSex());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        String xmlStr = t.writeXMLString();
        System.out.println("\n生成的XML字符串：\n" + xmlStr);
        try {
            Vector v = t.readXMLString(xmlStr);
            Iterator it = v.iterator();
            System.out.println("\n解析生成的XML字符串：");
            while (it.hasNext()) {
                student s = (student) it.next();
                System.out.println(s.getName() + "\t" + s.getAge() + "\t"
                        + s.getSex());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

}

