package com.drool.example.domain;

import java.util.HashMap;
import java.util.Map;

public class GenericObject {
   private Map<String, Object> fields;

   private Map<String, Object> results = new HashMap();
   public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}

	public Map<String, Object> getResults() {
		return results;
	}

	public void setResults(Map<String, Object> results) {
		this.results = results;
	}
    
    
}
