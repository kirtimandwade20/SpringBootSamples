package com.easyrules.example.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleController {

	@RequestMapping(value = "/fireRules", method = RequestMethod.GET, produces = "application/json")
	public Object getQuestions(@RequestParam(required = true) String age) throws FileNotFoundException, Exception {

		Facts facts = new Facts();
		//Creating object 
		JSONObject obj = new JSONObject();
		obj.put("age", age);
		
		facts.put("person", obj);

		MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
		Rule personRule = ruleFactory.createRule(new FileReader("src/main/resources/person-rule.yml"));

		// define rules
		Rules rules = new Rules();
		rules.register(personRule);

		// fire rules on known facts
		RulesEngine rulesEngine = new DefaultRulesEngine();
		rulesEngine.fire(rules, facts);

		System.out.println("Person Objec" + facts.get("person").toString());
		return null;
	}
}
