
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Customisation;

@Component
@Transactional
public class CustomisationToStringConverter implements Converter<Customisation, String> {

	@Override
	public String convert(final Customisation customisation) {
		String res;

		if (customisation == null)
			res = null;
		else
			res = String.valueOf(customisation.getId());
		//res = String.valueOf(category);
		return res;
	}
}