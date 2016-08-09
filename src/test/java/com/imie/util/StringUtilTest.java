package com.imie.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Test unitaires relatifs à la classe {@link StringUtil}.
 * @author takiguchi
 *
 */
public class StringUtilTest {

	/**
	 * Test la méthode {@link StringUtil#containSpecialChar(String)}.
	 */
	@Test
	public void testContainSpecialChar() {
		assertFalse(StringUtil.containSpecialChar("aBcDe1234"));

		assertTrue(StringUtil.containSpecialChar("~"));
		assertTrue(StringUtil.containSpecialChar("!"));
		assertTrue(StringUtil.containSpecialChar("@"));
		assertTrue(StringUtil.containSpecialChar("#"));
		assertTrue(StringUtil.containSpecialChar("$"));
		assertTrue(StringUtil.containSpecialChar("%"));
		assertTrue(StringUtil.containSpecialChar("^"));
		assertTrue(StringUtil.containSpecialChar("&"));
		assertTrue(StringUtil.containSpecialChar("*"));
		assertTrue(StringUtil.containSpecialChar("("));
		assertTrue(StringUtil.containSpecialChar(")"));
		assertTrue(StringUtil.containSpecialChar("-"));
		assertTrue(StringUtil.containSpecialChar("="));
		assertTrue(StringUtil.containSpecialChar("+"));
		assertTrue(StringUtil.containSpecialChar("["));
		assertTrue(StringUtil.containSpecialChar("]"));
		assertTrue(StringUtil.containSpecialChar("{"));
		assertTrue(StringUtil.containSpecialChar("}"));
		assertTrue(StringUtil.containSpecialChar(";"));
		assertTrue(StringUtil.containSpecialChar(":"));
		assertTrue(StringUtil.containSpecialChar(","));
		assertTrue(StringUtil.containSpecialChar("."));
		assertTrue(StringUtil.containSpecialChar("<"));
		assertTrue(StringUtil.containSpecialChar(">"));
		assertTrue(StringUtil.containSpecialChar("/"));
		assertTrue(StringUtil.containSpecialChar("?"));
		
		assertTrue(StringUtil.containSpecialChar("n4;7kh4-p0T0EK@>5e_PSUi%0"));
		assertTrue(StringUtil.containSpecialChar("1Vv@5Bs<35n/Wu+ZY8^0Ii,5h"));
		assertTrue(StringUtil.containSpecialChar("5#85D-8&/w+4QWnOu1q!Ip8Uf"));
		assertTrue(StringUtil.containSpecialChar("v7239@:dH.9+<g4<K9pMoiYPD"));
		assertTrue(StringUtil.containSpecialChar("AJ6%Tys;1Qq;(#8b[1o96ONa9"));
	}
	
	@Test
	public void test() {
		final String hash = BCrypt.hashpw("passe", BCrypt.gensalt(4));
		
		System.out.println(hash);

		final String regex = "([a-zA-Z0-9]+)";
		final String regex2 = "[\\.\\\\\\/]";
		
		System.out.println(Pattern.compile(regex2).matcher(hash).replaceAll("ù"));
		
		
		
//		System.out.println(bld.toString());
	}
}
