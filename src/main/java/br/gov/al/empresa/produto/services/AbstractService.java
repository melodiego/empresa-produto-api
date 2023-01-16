package br.gov.al.empresa.produto.services;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import static br.gov.al.empresa.produto.util.Constants.GenericConstants.CODE_FIND_NOT_FOUND;

public class AbstractService {
    private final ResourceBundleMessageSource messageSource;

    public AbstractService(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected String getMessage(String messageCode) {
        return getMessage(messageCode, null);
    }

    protected String getMessage(String messageCode, Object[] args) {
        return messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale());
    }

    protected String getMessageFindNotFound(Object id, String type) {
        Object[] args = { type, id };
        return getMessage(CODE_FIND_NOT_FOUND, args);
    }

    protected String getMessageFindNotFoundWithCode(Object id, String type, String codeMessage) {
        Object[] args = {id};
        return getMessage(codeMessage, args);
    }

    protected String getMessageFullError(String codeError, Exception exception) {
        StringBuilder messageError = new StringBuilder(getMessage(codeError));
        messageError.append(". ");
        messageError.append(exception.getMessage());
        return messageError.toString();
    }
}