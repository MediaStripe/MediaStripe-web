package com.imie.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test unitaires relatifs à la classe {@link RegexUtil}.
 * @author takiguchi
 *
 */
public class RegexUtilTest {
	
	/**
	 * Test la méthode {@link RegexUtil#isEmail(String)}.
	 */
	@Test
	public void testIsEmail() {
		final String emailOk = "toto.titi@gmail.com";
		assertTrue(RegexUtil.isEmail(emailOk));
		
		final String emailKO = "azerty";
		assertFalse(RegexUtil.isEmail(emailKO));
	}
}
