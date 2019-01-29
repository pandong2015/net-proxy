package tech.pcloud.proxy.configure.xml.server.exceptions;

import tech.pcloud.proxy.configure.xml.core.exceptions.XmlConfigureSaveException;
import tech.pcloud.proxy.configure.xml.server.ServerXmlConfigureModel;
import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.core.ProxyException;

/**
 * @ClassName ClientXmlConfigureReadException
 * @Author pandong
 * @Date 2019/1/29 10:46
 **/
public class ServerXmlConfigureSaveException extends XmlConfigureSaveException {
    private static final int code = 2;
    private static final ProxyException.ExceptionLevel level = ProxyException.ExceptionLevel.ERROR;
    private static final Model model = ServerXmlConfigureModel.ServerXmlConfigureModelFactory.INSTANCE.getModel();

    public ServerXmlConfigureSaveException() {
        super(level, model, code);
    }

    public ServerXmlConfigureSaveException(String message) {
        super(message, level, model, code);
    }

    public ServerXmlConfigureSaveException(String message, Throwable cause) {
        super(message, cause, level, model, code);
    }
}
