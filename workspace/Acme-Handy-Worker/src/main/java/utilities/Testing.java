
package utilities;

import java.util.ArrayList;
import java.util.List;

public class Testing {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		final List<String> cusSpam = new ArrayList<>();
		cusSpam.add("Nigeria");
		cusSpam.add("africa");
		cusSpam.add("pene");
		cusSpam.add("sida");

		final List<String> spam = cusSpam;
		String spamCommas = "";
		int j = 0;
		while (j < spam.size()) {
			spamCommas = spamCommas + spam.get(j) + ", ";
			j++;
		}
		spamCommas = spamCommas.substring(0, spamCommas.length() - 2);
		System.out.println(spamCommas);
		System.out.println("=====================");
		final String param = "Nigeria, africa, pene, sida";
		String[] spamWithEmpty;
		final List<String> spamWordsNoEmpty = new ArrayList<String>();
		if (param.contains(",")) {
			spamWithEmpty = param.split("\\s*,\\s*");
			for (int i = 0; i < spamWithEmpty.length; i++)
				if (!spamWithEmpty[i].isEmpty())
					spamWordsNoEmpty.add(spamWithEmpty[i]);
		} else if (!param.isEmpty())
			spamWordsNoEmpty.add(param);

		System.out.println(spamWordsNoEmpty);

	}

}
