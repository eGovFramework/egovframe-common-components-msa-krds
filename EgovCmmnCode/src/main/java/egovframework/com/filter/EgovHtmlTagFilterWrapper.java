package egovframework.com.filter;

import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class EgovHtmlTagFilterWrapper extends HttpServletRequestWrapper {

    public EgovHtmlTagFilterWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return sanitize(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] = sanitize(values[i]);
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        Map<String, String[]> sanitizedMap = new HashMap<>();
        parameterMap.forEach((key, value) -> {
            String[] sanitizedValues = new String[value.length];
            for (int i = 0; i < value.length; i++) {
                sanitizedValues[i] = sanitize(value[i]);
            }
            sanitizedMap.put(key, sanitizedValues);
        });
        return sanitizedMap;
    }

    private String sanitize(String value) {
        if (value == null) {
            return null;
        }
        return StringEscapeUtils.escapeHtml4(value);
    }

}
