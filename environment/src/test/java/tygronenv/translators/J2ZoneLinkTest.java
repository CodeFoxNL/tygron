package tygronenv.translators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import eis.eis2java.exception.TranslationException;
import nl.tytech.data.engine.item.GlobalIndicator;
import nl.tytech.data.engine.serializable.MapType;

public class J2ZoneLinkTest {
	J2ZoneLink translator = new J2ZoneLink();
	GlobalIndicator globalIndicator;
	@Before
	public void setUp() throws Exception {
		globalIndicator = mock(GlobalIndicator.class);
	}

	@Test
	public void test() throws TranslationException {
		translator.translate(globalIndicator);
		verify(globalIndicator, times(1)).getTarget();
		verify(globalIndicator, times(1)).getZoneAmount();
	}

}
