package tygronenv.translators;

import java.util.Set;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.eis2java.translation.Translator;
import eis.iilang.Function;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import nl.tytech.data.engine.item.GlobalIndicator;

/**
 * Translate {@link GlobalIndicator} into zone_link(<zoneID>, <indID>, <curVal>, <tarVal>).
 * 
 * @author M.Houtman
 *
 */
public class J2ZoneLink implements Java2Parameter<GlobalIndicator> {

	private final Translator translator = Translator.getInstance();
	
	/**
	 * translates GlobalIndicator into zone_link(<zoneID>, <tarVal>);
	 */
	@Override
	public Parameter[] translate(GlobalIndicator g) throws TranslationException {
		return new Parameter[] { new Function("zone_link", new Numeral(g.getID()), new Numeral(g.getTarget()))};
	}
	
	@Override
	public Class<? extends GlobalIndicator> translatesFrom() {
		return GlobalIndicator.class;
	}

}
