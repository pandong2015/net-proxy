package tech.pcloud.proxy.configure.xml.client.exceptions;

import tech.pcloud.proxy.configure.xml.client.ClientXmlConfigureModel;
import tech.pcloud.proxy.configure.xml.core.exceptions.XmlConfigureReadException;
import tech.pcloud.proxy.configure.xml.core.exceptions.XmlConfigureSaveException;
import tech.pcloud.proxy.core.Model;

/**
 * @ClassName ClientXmlConfigureReadException
 * @Author pandong
 * @Date 2019/1/29 10:46
 **/
public class ClientXmlConfigureSaveException extends XmlConfigureSaveException {
    private static final int code = 2;
    private static final ExceptionLevel level = ExceptionLevel.ERROR;
    private static final Model model = ClientXmlConfigureModel.ClientXmlConfigureModelFactory.INSTANCE.getModel();

    public ClientXmlConfigureSaveException() {
        super(level, model, code);
    }

    public ClientXmlConfigureSaveException(String message) {
        super(message, level, model, code);
    }

    public ClientXmlConfigureSaveException(String message, Throwable cause) {
        super(message, cause, level, model, code);
    }
}
