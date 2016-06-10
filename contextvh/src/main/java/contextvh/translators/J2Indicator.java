package contextvh.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Function;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import nl.tytech.data.engine.item.Indicator;
import nl.tytech.data.engine.serializable.MapType;


/**
 * Translates {@link Indicator} into indicator(ID, Current, Target).
 *
 * @author Stefan de Vringer
 */
public class J2Indicator implements Java2Parameter<Indicator> {

	private double target;
	private Double currentValue;
    /**
     * Translate the indicator into a parameter.
     */
    @Override
    public Parameter[] translate(final Indicator indicator) throws TranslationException {
        String explanation = indicator.getExplanation();
        setValues(indicator.getTarget(), indicator.getExactNumberValue(MapType.MAQUETTE));

        ParameterList pl = new ParameterList();

        if (explanation.contains("<p hidden>")) {
            // Get the String between <p hidden> and </p>
            explanation = indicator.getExplanation().split("<p hidden>")[1].split("</p>")[0];

            // If the indicator is an indicator with zones, we add multi
            if (explanation.contains("multiN")) {
            	setValues(target, Double.parseDouble(explanation.split("multiN")[0]));
                pl = zoneLink(indicator, target, explanation.split("multiN")[1]);
            } else if (explanation.contains("multiT")) {
                String[] targetValues = explanation.split("multiT")[0].split("\\\\t");
                setValues(Double.parseDouble(targetValues[1]), Double.parseDouble(targetValues[0]));
                pl = zoneLink(indicator, target, explanation.split("multiT")[1]);
            } else if (explanation.contains("single")) {
                String[] targetValues = explanation.split("single")[0].split("\\\\t");
                setValues(Double.parseDouble(targetValues[1]), Double.parseDouble(targetValues[0]));
            }
        }

        return new Parameter[] {new Function("indicator",
                new Numeral(indicator.getID()),
                new Numeral(currentValue),
                new Numeral(target), pl)};
    }

    /**
     * Sets the target and current values.
     *
     * @param targetv New target value.
     * @param currentValuev New current value.
     */
    private void setValues(final double targetv, final Double currentValuev) {
    	if (currentValue == null) {
            currentValue = 0.0;
        }
    	this.target = targetv;
    	this.currentValue = currentValuev;
    }

    /**
     * Translates a list of items into a ParameterList of zonelinks.
     *
     * @param i        The indicator.
     * @param targetv   The target of the indicator.
     * @param itemList The list of items to parse.
     * @return ParameterList of zoneLinks
     */
    public ParameterList zoneLink(final Indicator i, final double targetv, final String itemList) {
        ParameterList pList = new ParameterList();
        final int three = 3;

        // Get all different zones into array
        String[] items = itemList.split("\\\\n");

        for (String item : items) {
            // Split zone index, current value and potential custom target into array
            String[] types = item.split("\\\\t");

            if (types.length == 2) {
                // Length 2 if there is no custom target for each zone
                pList.add(new Function("zone_link", new Numeral(Integer.parseInt(types[0])),
                        new Numeral(i.getID()), new Numeral(Double.parseDouble(types[1].replaceAll("[^0-9.,-]", ""))),
                        new Numeral(targetv)));
            } else if (types.length == three) {
                // Length 3 if there are custom targets for each zone
                pList.add(new Function("zone_link", new Numeral(Integer.parseInt(types[0])),
                        new Numeral(i.getID()), new Numeral(Double.parseDouble(types[1].replaceAll("[^0-9.,-]", ""))),
                        new Numeral(Double.parseDouble(types[2].replaceAll("[^0-9.,-]", "")))));
            }
        }

        return pList;
    }

    /**
     * Get the class which is translated from.
     */
    @Override
    public Class<? extends Indicator> translatesFrom() {
        return Indicator.class;
    }

}
