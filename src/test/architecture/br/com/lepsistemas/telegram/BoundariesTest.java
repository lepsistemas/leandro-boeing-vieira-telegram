package br.com.lepsistemas.telegram;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "br.com.lepsistemas.telegram")
public class BoundariesTest {
	
	@ArchTest
	static final ArchRule domain_objects_should_not_access_infrastructure_objects = 
		noClasses().that().resideInAPackage("..domain..")
			.should().accessClassesThat().resideInAPackage("..infrastructure..");

}
