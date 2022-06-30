package com.drool.example.controller;

import java.util.Map;

import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drool.example.config.DroolsConfiguration;
import com.drool.example.domain.GenericObject;

@RestController
public class DroolsRuleController {

	public DroolsRuleController() {
	}

	@PostMapping(value = "/fireRules", produces = "application/json")
	public GenericObject fireRules(@RequestBody Map<String, Object> input,
			@RequestParam(required = true) String tenant) {
		InternalKnowledgeBase knowledgeBase = DroolsConfiguration.hmKnowledgebase.get(tenant.trim());
		KieSession kieSession = knowledgeBase.newKieSession();
		GenericObject fdRequest = new GenericObject();

		fdRequest.setFields(input);
		kieSession.insert(fdRequest);
		kieSession.fireAllRules();
		kieSession.dispose();

		return fdRequest;
	}

	@RequestMapping(value = "/reload", method = RequestMethod.GET, produces = "application/json")
	public String getQuestions(@RequestParam(required = true) String tenant) {

		try {
			DroolsConfiguration.reloadKnowledgeBase(tenant);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "Failure";
		}

		return "config for " + tenant + " updated";
	}
}
