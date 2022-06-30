package com.drool.example.config;

import java.util.HashMap;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfiguration {

	private final KieServices kieServices = KieServices.Factory.get();
	public static HashMap<String, InternalKnowledgeBase> hmKnowledgebase = new HashMap();

	public static HashMap<String, String> hmDrlFileNames = new HashMap();

	static {
		hmDrlFileNames.put("tenant1", "FDInterestRate.drl");
		hmDrlFileNames.put("tenant2", "AgeFinderRule.drl");

	}
	

	public static KnowledgeBuilder knowledgeBuilder(String filename) throws Exception {
		KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//		builder.add(ResourceFactory.newUrlResource(filename), ResourceType.DRL);
		builder.add(ResourceFactory.newClassPathResource(filename), ResourceType.DRL);
		if (builder.hasErrors()) {
			throw new Exception(String.valueOf(builder.getErrors()));
		}
		return builder;
	}

	@Bean
	public void knowledgeBase() throws Exception {
		InternalKnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addPackages(knowledgeBuilder(hmDrlFileNames.get("tenant1")).getKnowledgePackages());
		hmKnowledgebase.put("tenant1", knowledgeBase);
		InternalKnowledgeBase knowledgeBase2 = KnowledgeBaseFactory.newKnowledgeBase();

		knowledgeBase2.addPackages(knowledgeBuilder(hmDrlFileNames.get("tenant2")).getKnowledgePackages());
		hmKnowledgebase.put("tenant2", knowledgeBase2);

	}

	public static void reloadKnowledgeBase(String tenant) throws Exception {
		hmKnowledgebase.get(tenant);
		InternalKnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addPackages(knowledgeBuilder(hmDrlFileNames.get(tenant)).getKnowledgePackages());
		hmKnowledgebase.put(tenant, knowledgeBase);

	}

}