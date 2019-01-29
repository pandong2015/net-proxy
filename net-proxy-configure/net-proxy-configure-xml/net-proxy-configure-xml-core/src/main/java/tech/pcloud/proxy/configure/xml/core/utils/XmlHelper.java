package tech.pcloud.proxy.configure.xml.core.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

/**
 * @ClassName XmlHelper
 * @Author pandong
 * @Date 2019/1/28 15:37
 **/
public class XmlHelper {

    public static <T> String object2XmlString(T obj) throws Exception {
        return new String(object2XmlByteArray(obj));
    }

    public static <T> byte[] object2XmlByteArray(T obj) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(obj, out);
            return out.toByteArray();
        } catch (Exception e) {
            throw e;
        }
    }

    public static <T> T xmlString2Object(String content, Class<T> objClass) throws Exception {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(objClass);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            T t = (T) jaxbUnmarshaller.unmarshal(new StringReader(content));
            return t;
        } catch (Exception e) {
            throw e;
        }
    }

}
