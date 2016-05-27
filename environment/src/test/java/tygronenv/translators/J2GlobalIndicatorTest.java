package tygronenv.translators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import eis.eis2java.exception.TranslationException;
import nl.tytech.data.engine.item.GlobalIndicator;

public class J2GlobalIndicatorTest {
	J2GlobalIndicator translator = new J2GlobalIndicator();
	GlobalIndicator globalIndicator;
	@Before
	public void setUp() throws Exception {
		globalIndicator = mock(GlobalIndicator.class);
	}

	@Test
	public void test() throws TranslationException {
		translator.translate(globalIndicator);
		verify(globalIndicator, times(1)).getTarget();
	}

}
