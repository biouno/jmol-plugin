package org.biouno.jmol;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class TestBuildAction {

	@Test
	public void testProperties() throws IOException, InterruptedException, ExecutionException {
		JmolBuildAction buildAction = new JmolBuildAction(null, 100, "LOAD FILES /data/1234.pdb\nselect *\nmatch 123");
		assertEquals(Integer.valueOf(100), buildAction.getWidth());
		assertEquals("LOAD FILES /data/1234.pdb;select *;match 123;", buildAction.getScriptFormatted());
	}
	
}
