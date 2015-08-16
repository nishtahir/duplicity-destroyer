package com.wolfden.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase

{
	public static final String TEST_DIRECTORY = "src/test/TestDirectory";

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
        System.out.println("Os name: " + System.getProperty("os.name"));
        System.out.println("Os arch: " + System.getProperty("os.arch"));
        System.out.println("Os version: " + System.getProperty("os.version"));
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}
}
