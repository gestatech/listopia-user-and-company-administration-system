package florian_haas.lucas.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

@FacesConverter(CharArrayConverter.CONVERTER_ID)
public class CharArrayConverter implements Converter {

	public static final String CONVERTER_ID = "lucas:charArrayConverter";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value != null ? value.toCharArray() : null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value != null ? new String((char[]) value) : null;
	}

}
