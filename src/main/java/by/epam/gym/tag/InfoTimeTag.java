package by.epam.gym.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Locale;

@SuppressWarnings("serial")
public class InfoTimeTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        String time = "<hr/>Time : <b> " + gregorianCalendar.getTime() + "</b><hr/>";
        String locale = "Locale : <b> " + Locale.getDefault() + " </b><hr/> ";

        try{
            JspWriter writer = pageContext.getOut();
            writer.write(time + locale);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
