package org.biouno.jmol;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

import java.io.IOException;
import java.io.PrintStream;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Publishes mol, pdb and other files using Jmol's Javascript library.
 * 
 * @since 0.1
 */
public class JmolPublisher extends Recorder {

	private final String script;
	private final Integer width;

	@DataBoundConstructor
	public JmolPublisher(String script, Integer width) {
		this.script = script;
		this.width = width;
	}
	
	public Object readResolve() {
		return new JmolPublisher(script, width);
	}

	/**
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}
	
	/**
	 * Gathers the Jmol parameters and adds a build action to produce the Javascript 
	 * applet.
	 */
	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
			BuildListener listener) throws InterruptedException, IOException {
		PrintStream logger = listener.getLogger();
		logger.println("Publishing Jmol file");
		
		logger.println("Creating WS_URL env var");

		build.addAction(new JmolBuildAction(build, getWidth(), getScript()));
		
		return true;
	}

	@Extension
	public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {
		public DescriptorImpl() {
			super(JmolPublisher.class);
			load();
		}
		@Override
		public String getDisplayName() {
			return "Publish Jmol graph";
		}
		@Override
		public boolean isApplicable(@SuppressWarnings("rawtypes") Class<? extends AbstractProject> jobType) {
			return true;
		}
		
	}

}
