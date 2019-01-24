
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CustomisationRepository;
import domain.Customisation;

@Component
@Transactional
public class StringToCustomisationConverter implements Converter<String, Customisation> {

	@Autowired
	CustomisationRepository	customisationRepository;


	@Override
	public Customisation convert(final String text) {
		Customisation res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.customisationRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
