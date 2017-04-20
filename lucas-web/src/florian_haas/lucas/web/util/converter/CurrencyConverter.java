package florian_haas.lucas.web.util.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import org.primefaces.component.inputnumber.InputNumber;

import florian_haas.lucas.business.GlobalDataBeanLocal;
import florian_haas.lucas.web.util.WebUtils;

@Named(CurrencyConverter.CONVERTER_ID)
@RequestScoped
public class CurrencyConverter implements Converter {

	public static final String CONVERTER_ID = "currencyConverter";

	public static final String NULL_KEY = "lucas.application.currencyConverter.null";

	@EJB
	private GlobalDataBeanLocal globalData;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value.equals(WebUtils.getTranslatedMessage(NULL_KEY)) ? null : new BigDecimal(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value != null
				? ((DecimalFormat) DecimalFormat.getNumberInstance(context.getViewRoot().getLocale())).format((BigDecimal) value)
						+ (!(component != null && component instanceof InputNumber) ? " " + globalData.getCurrencySymbol() : "")
				: WebUtils.getTranslatedMessage(NULL_KEY);
	}
}
