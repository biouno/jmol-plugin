package org.biouno.jmol;

import static org.junit.Assert.assertEquals;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class TestBuildAction {

	@Rule
	public JenkinsRule jenkins = new JenkinsRule();
	
	@Test
	@SuppressWarnings("rawtypes")
	public void testProperties() throws IOException {
		AbstractProject project = jenkins.createFreeStyleProject("test1");
		AbstractBuild build = project.createExecutable();
		
		JmolBuildAction buildAction = new JmolBuildAction(build, 100, "data/1234.pdb", "select *\nmatch 123");
		
		assertEquals(Integer.valueOf(100), buildAction.getWidth());
		assertEquals("job/test1/ws/data/1234.pdb", buildAction.getFileURL());
		assertEquals("select *;match 123;", buildAction.getScriptFormatted());
	}
	
}
