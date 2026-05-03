package isry.itgcms.sysmgmt.messagesource.web;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.messagesource.service.MessageService;
import isry.itgcms.sysmgmt.messagesource.vo.MessageVO;

public class DatabaseMessageSource extends ReloadableResourceBundleMessageSource {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "messageService")
	private MessageService messageService;

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {

		MessageVO messageVo = null;
		try {
			messageVo = messageService.getMessageBundle(code, locale);
		} catch (IOException e) {
			log.debug(e.getMessage());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		MessageFormat format = null;

		if (messageVo != null && messageVo.getId() != null) {
			format = new MessageFormat(messageVo.getValue(), locale);
		} else {
			format = super.resolveCode(code, locale);
		}

		return format;
	}

	@Override
	protected String resolveCodeWithoutArguments(String code, Locale locale) {

		MessageVO messageVo = null;
		try {
			messageVo = messageService.getMessageBundle(code, locale);
		} catch (IOException e) {
			log.debug(e.getMessage());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		String format = null;

		if (messageVo != null && messageVo.getId() != null) {
			format = messageVo.getValue();
		} else {
			format = super.resolveCodeWithoutArguments(code, locale);
		}

		return format;
	}

}
