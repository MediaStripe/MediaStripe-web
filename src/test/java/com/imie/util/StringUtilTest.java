package com.imie.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

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
	public void test2() {
		List<String> liste = new LinkedList<String>();
		liste.addAll(Arrays.asList("ogg,mp4".split(",")));
		
		assertTrue(liste.contains("mp4"));
	}
}
