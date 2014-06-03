package org.biouno.jmol;

import hudson.EnvVars;
import hudson.console.ConsoleNote;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.Cause;
import hudson.model.Result;
import hudson.model.StreamBuildListener;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;
import hudson.util.LogTaskListener;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import jenkins.model.Jenkins;

import org.apache.commons.lang.StringUtils;

/**
 * Build action that adds jmol summary.
 */
public class JmolBuildAction implements Action {

	private final static Logger LOGGER = Logger.getLogger(JmolBuildAction.class.getName());
	
	private final @SuppressWarnings("rawtypes") AbstractBuild build;
	private final String script;
	private final Integer width;
	
	private TaskListener listener;
	
	public JmolBuildAction(@SuppressWarnings("rawtypes") AbstractBuild build, Integer width, String script) {
		this.build = build;
		this.width = width;
		this.script = script;
		listener = new BuildListener() {
			private static final long serialVersionUID = 4413476882604250035L;
			public void hyperlink(String url, String text) throws IOException {
			}
			public PrintStream getLogger() {
				return null;
			}
			public PrintWriter fatalError(String format, Object... args) {
				return null;
			}
			public PrintWriter fatalError(String msg) {
				return null;
			}
			public PrintWriter error(String format, Object... args) {
				return null;
			}
			public PrintWriter error(String msg) {
				return null;
			}
			public void annotate(@SuppressWarnings("rawtypes") ConsoleNote ann) throws IOException {
			}
			
			public void started(List<Cause> causes) {
			}
			public void finished(Result result) {
			}
		};
	}
	
	public Object readResolve() {
		return new JmolBuildAction(build, width, script);
	}
	
	public Integer getWidth() {
		return width;
	}
	
	public String getScriptFormatted() {
		String rootURL = StringUtils.defaultIfBlank(Jenkins.getInstance().getRootUrl(), "");
		StringBuilder sb = new StringBuilder();
		
		String script = this.script;
		try {
			EnvVars vars = build.getEnvironment(listener);
			rootURL += build.getProject().getUrl();
			rootURL += "ws/";
			vars.addLine("WS_URL=" + rootURL);
			script = vars.expand(this.script);
		} catch (IOException e) {
			LOGGER.warning(e.getMessage());
		} catch (InterruptedException e) {
			LOGGER.warning(e.getMessage());
		}
		
		if (StringUtils.isNotBlank(script)) {
			StringTokenizer tokenizer = new StringTokenizer(script, "\n");
			while (tokenizer.hasMoreTokens()) {
				String element = tokenizer.nextToken();
				sb.append(element);
				sb.append(";");
			}
		}
		return sb.toString();
	}

	public String getIconFileName() {
		return "/plugin/jmol/icon/jmol-icon-24.png";
	}

	public String getDisplayName() {
		return "Jmol";
	}

	public String getUrlName() {
		return null;
	}

}
