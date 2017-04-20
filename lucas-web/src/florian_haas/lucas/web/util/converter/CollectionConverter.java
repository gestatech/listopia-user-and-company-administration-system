package florian_haas.lucas.web.util.converter;

import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import florian_haas.lucas.web.util.WebUtils;

public abstract class CollectionConverter<T> extends BasicConverter<Collection<T>> {

	private final String emptyLangKey;

	protected CollectionConverter(String emptyLangKey) {
		super(Boolean.FALSE);
		this.emptyLangKey = emptyLangKey;
	}

	@Override
	protected Class<?> getValueClass() {
		return Collection.class;
	}

	@Override
	public String getString(FacesContext context, UIComponent component, Collection<T> value) {
		StringBuilder builder = new StringBuilder();
		if (value.isEmpty()) {
			builder.append(WebUtils.getTranslatedMessage(emptyLangKey));
		} else {
			value.forEach(entry -> {
				builder.append(entryToString(entry) + "<br />");
			});
		}
		return builder.toString().trim();
	}

	protected String entryToString(T entry) {
		return entry.toString();
	}

}
