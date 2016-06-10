package contextvh.translators;

import nl.tytech.core.net.Lord;
import nl.tytech.data.engine.item.Indicator;
import nl.tytech.data.engine.serializable.MapType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eis.eis2java.exception.TranslationException;
import eis.iilang.Function;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for the J2Indicator class.
 *
 * @author Stefan de Vringer, Marco Houtman.
 */
public class J2IndicatorTest {
    /**
     * translator the J2Indicator to test.
     */
    private J2Indicator translator;
    /**
     * the indicator to translate.
     */
    private TestIndicator indicator;
    private Lord lord;
    private ParameterList pl;
    private Parameter[] a;
    private Parameter[] b;
	private final double ten = 10.0;
	private final double hundred = 100;
	private final double seven = 7.0;
	private final double five = 5.0;
    /**
     * Initialise before every test.
     */
    @Before
    public void init() {
        translator = new J2Indicator();
        lord = Mockito.mock(Lord.class);
        pl = new ParameterList();
    }

    /**
     * TestIndicator to override some of the Indicator classes.
     * @author Marco
     *
     */
    class TestIndicator extends Indicator {
		private static final long serialVersionUID = 1L;
    	private String explanation;
    	private double current;
    	private double[] targets;

    	/**
    	 * TestIndicator constructor.
    	 *
    	 * @param indicatorID ID of the indicator.
    	 * @param currentv Current value.
    	 * @param targetv Target value.
    	 * @param explanationv Explanation string.
    	 */
    	TestIndicator(final int indicatorID, final double currentv, final double targetv, final String explanationv) {
    		this.setLord(lord);
    		this.setId(indicatorID);
    		this.current = currentv;
    		this.explanation = explanationv;
    		this.targets = (new double[] {targetv, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
    	}

    	@Override
    	public double getTarget() {
    		return getTargets()[0];
    	}

    	@Override
    	public double[] getTargets() {
    		return targets;
    	}

    	@Override
    	public String getExplanation() {
    		return explanation;
    	}

    	@Override
    	public Double getExactNumberValue(final MapType mp) {
    		return current;
    	}

    	@Override
		public TypeInterface getType() {
			return null;
		}
    }

    /**
     * Test the new TestIndicator class.
     */
    @Test
    public void overrideTestIndicatorTest() {
    	indicator = new TestIndicator(0, ten, hundred, "TEST");
    	assertTrue(indicator.getExplanation().equals("TEST"));
    	assertEquals(0, indicator.getAbsoluteValue(MapType.CURRENT), ten);
    	assertTrue(indicator.getID() == 0);
    	assertTrue(indicator.getTarget() == hundred);
    }

    /**
     * Test whether the translation method asks for the correct properties of the indicator.
     * and whether the string is correctly handled.
     *
     * @throws TranslationException thrown if the translate method fails.
     */
    @Test
    public void translatorSingleTest() throws TranslationException {
    	final double twentyfive = 25.0;
    	indicator = new TestIndicator(0, ten, hundred, "<p hidden>7\\t25single0\\t-0.21%\\n1\\t1</p>");
        a = translator.translate(indicator);
        b = new Parameter[] {new Function("indicator", new Numeral(0),
        		new Numeral(seven), new Numeral(twentyfive), new ParameterList())};
        System.out.println(a[0]);
        assertTrue(a[0].equals(b[0]));
    }

    /**
     * Tests if the string without targets is parsed correct.
     *
     * @throws TranslationException thrown if the translate method fails.
     */
    @Test
    public void translatorMultiNoTargetTest() throws TranslationException {
    	indicator = new TestIndicator(0, ten, hundred, "<p hidden>7multiN0\\t-1%\\n</p>");
        a = translator.translate(indicator);
        pl.add(new Function("zone_link", new Numeral(0), new Numeral(0), new Numeral(-1.0), new Numeral(hundred)));
        b = new Parameter[] {new Function("indicator", new Numeral(0), new Numeral(seven), new Numeral(hundred), pl)};
        System.out.println(a[0]);
        assertTrue(a[0].equals(b[0]));
    }

    /**
     * Tests if the string with multiple targets is parsed correct.
     *
     * @throws TranslationException thrown if the translate method fails.
     */
    @Test
    public void translatorMultiTargetTest() throws TranslationException {
    	final double twentyfive = 25.0;
    	final double seventyfive = 75.0;
    	indicator = new TestIndicator(0, ten, hundred, "<p hidden>7\\t75multiT0\\t5\\t25\\n</p>");
    	a = translator.translate(indicator);
    	pl.add(new Function("zone_link", new Numeral(0), new Numeral(0), new Numeral(five), new Numeral(twentyfive)));
    	b = new Parameter[] {new Function("indicator", new Numeral(0), new Numeral(seven),
    			new Numeral(seventyfive), pl)};
    	System.out.println(a[0]);
    	assertTrue(a[0].equals(b[0]));
    }

    /**
     * Tests if the string with only a target on the global indicator is correctly parsed.
     *
     * @throws TranslationException thrown if the translate method fails.
     */
    @Test
    public void translatornoSingleTargetTest() throws TranslationException {
    	final double eight = 8.0;
    	final double eightteen = 18.0;
    	indicator = new TestIndicator(0, ten, seven, "<p hidden>7\\t18multiT0\\t8</p>");
    	a = translator.translate(indicator);
    	pl.add(new Function("zone_link", new Numeral(0), new Numeral(0), new Numeral(eight), new Numeral(eightteen)));
    	b = new Parameter[] {new Function("indicator", new Numeral(0), new Numeral(seven), new Numeral(eightteen), pl)};
    	assertTrue(a[0].equals(b[0]));
    }

    /**
     * Tests if the string with targets on the zone_links is correctly parsed.
     *
     * @throws TranslationException thrown if the translate method fails.
     */
    @Test
    public void translatorOnlySingleTargetTest() throws TranslationException {
    	final double eight = 8.0;
    	indicator = new TestIndicator(0, ten, seven, "<p hidden>7multiN0\\t5\\t8</p>");
    	a = translator.translate(indicator);
    	pl.add(new Function("zone_link", new Numeral(0), new Numeral(0), new Numeral(five), new Numeral(eight)));
    	b = new Parameter[] {new Function("indicator", new Numeral(0), new Numeral(seven), new Numeral(seven), pl)};
    	assertTrue(a[0].equals(b[0]));
    }
}
