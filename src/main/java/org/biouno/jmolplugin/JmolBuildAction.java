package org.biouno.jmolplugin;

import hudson.model.AbstractBuild;
import hudson.triggers.SCMTrigger.BuildAction;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

/**
 * Build action that adds jmol summary.
 */
public class JmolBuildAction extends BuildAction {

	private final String file;
	private final String script;
	private final Integer width;
	
	public JmolBuildAction(@SuppressWarnings("rawtypes") AbstractBuild build, Integer width, String file, String script) {
		super(build);
		this.width = width;
		this.file = file;
		this.script = script;
	}
	
	public Integer getWidth() {
		return width;
	}
	
	public String getFileURL() {
		return build.getProject().getUrl() + "/ws/" + file;
	}
	
	public String getScriptFormatted() {
		StringBuilder sb = new StringBuilder();
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

}
