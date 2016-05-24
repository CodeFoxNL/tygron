package tygronenv.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.eis2java.translation.Translator;
import eis.iilang.Function;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import nl.tytech.data.engine.item.UpgradeType;
import nl.tytech.data.engine.serializable.UpgradePair;

/**
 * Translate {@link UpgradeType} into UpgradeType(UpgradeTypeID, BuildingSourceID, BuildingTargetID).
 * UpgradeTypes without pairs are returned as UpgradeType(UpgradeTypeID).
 * 
 * @author M.Houtman
 *
 */
public class J2UpgradeType implements Java2Parameter<UpgradeType> {

	private final Translator translator = Translator.getInstance();

	@Override
	public Parameter[] translate(UpgradeType u) throws TranslationException {
		if(u.getPairs().size()>0){
			UpgradePair pair = u.getPairs().get(0);
			return new Parameter[] { new Function("upgrade_type", new Numeral(u.getID()), 
					new Numeral(pair.getSourceFunctionID()), 
					new Numeral(pair.getTargetFunctionID())) };
		}
		return new Parameter[] { new Function("upgrade_type", new Numeral(u.getID()))};
	}
	
	@Override
	public Class<? extends UpgradeType> translatesFrom() {
		return UpgradeType.class;
	}

}
