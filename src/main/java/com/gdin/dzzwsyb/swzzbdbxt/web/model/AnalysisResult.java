package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.List;

public class AnalysisResult {
	
	private List<Analysis> results;
	
	private List<SimpleAnalysis> simpleResults;

	public AnalysisResult(List<Analysis> results, List<SimpleAnalysis> simpleResults) {
		super();
		this.results = results;
		this.simpleResults = simpleResults;
	}

	public AnalysisResult() {
		super();
	}

	public List<Analysis> getResults() {
		return results;
	}

	public void setResults(List<Analysis> results) {
		this.results = results;
	}

	public List<SimpleAnalysis> getSimpleResults() {
		return simpleResults;
	}

	public void setSimpleResults(List<SimpleAnalysis> simpleResults) {
		this.simpleResults = simpleResults;
	}

}
