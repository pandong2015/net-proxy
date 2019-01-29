package tech.pcloud.proxy.configure.xml.client.exceptions;

import tech.pcloud.proxy.configure.xml.client.ClientXmlConfigureModel;
import tech.pcloud.proxy.configure.xml.core.exceptions.XmlConfigureReadException;
import tech.pcloud.proxy.core.Model;

/**
 * @ClassName ClientXmlConfigureReadException
 * @Author pandong
 * @Date 2019/1/29 10:46
 **/
public class ClientXmlConfigureReadException extends XmlConfigureReadException {
    private static final int code = 1;
    private static final ExceptionLevel level = ExceptionLevel.ERROR;
    private static final Model model = ClientXmlConfigureModel.ClientXmlConfigureModelFactory.INSTANCE.getModel();

    public ClientXmlConfigureReadException() {
        super(level, model, code);
    }

    public ClientXmlConfigureReadException(String message) {
        super(message, level, model, code);
    }

    public ClientXmlConfigureReadException(String message, Throwable cause) {
        super(message, cause, level, model, code);
    }
}
