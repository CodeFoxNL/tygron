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
		if(g.getZoneAmount()==0){
			return new Parameter[] {new Function("zone_link", new Numeral(g.getID()), new Numeral(g.getTarget()))};
		}
		
		return new Parameter[] { new Function("zone_link", new Numeral(g.getID()), zones(g.getMaquetteScores().keySet(), g), new Numeral(g.getTarget()))};
	}
	public ParameterList zones(Set<Integer> zone, GlobalIndicator g) {
		ParameterList pList = new ParameterList();
		for(int z: zone){
			pList.add(new Function("zoneWeights", new Numeral(z), new Numeral(g.getMaquetteScores().get(z))));
		}
		return pList;
	}
	@Override
	public Class<? extends GlobalIndicator> translatesFrom() {
		return GlobalIndicator.class;
	}

}
