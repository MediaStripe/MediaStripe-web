package com.imie.util;

import static org.junit.Assert.assertEquals;
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
	
	@Test
	public void testIsNumber() {
		assertTrue(RegexUtil.isNumber("0123456789"));
		
		assertFalse(RegexUtil.isNumber("a"));
		assertFalse(RegexUtil.isNumber("a0"));
		assertFalse(RegexUtil.isNumber("0a"));
		assertFalse(RegexUtil.isNumber("&"));
		assertFalse(RegexUtil.isNumber("&0"));
		assertFalse(RegexUtil.isNumber("0&"));
	}
	
	@Test
	public void testReplaceSlashs() {
		assertEquals("aaaaaa", RegexUtil.replaceSequence("//\\\\//", "[\\\\\\/]", "a"));
	}
}
