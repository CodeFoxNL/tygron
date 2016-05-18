package tygronenv.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.eis2java.translation.Translator;
import eis.iilang.Function;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import nl.tytech.data.engine.item.GlobalIndicator;
import nl.tytech.data.engine.serializable.MapType;

/**
 * Translate {@link GlobalIndicator} into zone_link(zoneID, indID, cur_Val, tar_Val).
 * 
 * @author M.Houtman
 *
 */
public class J2ZoneLink implements Java2Parameter<GlobalIndicator> {

	private final Translator translator = Translator.getInstance();

	@Override
	public Parameter[] translate(GlobalIndicator g) throws TranslationException {
		return new Parameter[] { new Function("zone_link", new Numeral(g.getID()), translator.translate2Parameter(g.getZoneScores(MapType.MAQUETTE).keySet())[0],
				translator.translate2Parameter(g.getZoneScores(MapType.MAQUETTE).values())[0], new Numeral(g.getTarget()))};
	}

	@Override
	public Class<? extends GlobalIndicator> translatesFrom() {
		return GlobalIndicator.class;
	}

}
