package org.example;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DependencyAssertionsTest {

    static final String BASE_PACKAGE = "org.example";
    static final String AND_SUBPACKAGES = "..";

    JavaClasses classes = new ClassFileImporter().importPackages(BASE_PACKAGE + AND_SUBPACKAGES);

    @Test
    void ruleFailsForCDependsA() {
        assertThatThrownBy(
                () -> noClasses().that().resideInAPackage(BASE_PACKAGE + ".c..").should().dependOnClassesThat()
                        .resideInAPackage(BASE_PACKAGE + ".a..").check(classes)).isInstanceOf(AssertionError.class);
    }

    @Test
    void ruleFailsForDDependsOnA() { // fails
        assertThatThrownBy(
                () -> noClasses().that().resideInAPackage(BASE_PACKAGE + ".d..").should().dependOnClassesThat()
                        .resideInAPackage(BASE_PACKAGE + ".a..").check(classes)).isInstanceOf(AssertionError.class);
    }

    @Test
    void ruleFailsForEDependsOnA() {
        assertThatThrownBy(
                () -> noClasses().that().resideInAPackage(BASE_PACKAGE + ".e..").should().dependOnClassesThat()
                        .resideInAPackage(BASE_PACKAGE + ".a..").check(classes)).isInstanceOf(AssertionError.class);
    }

    @Test
    void ruleFailsForFDependsOnA() { // fails
        assertThatThrownBy(
                () -> noClasses().that().resideInAPackage(BASE_PACKAGE + ".f..").should().dependOnClassesThat()
                        .resideInAPackage(BASE_PACKAGE + ".a..").check(classes)).isInstanceOf(AssertionError.class);
    }

    @Test
    void ruleFailsForGDependsOnA() {
        assertThatThrownBy(
                () -> noClasses().that().resideInAPackage(BASE_PACKAGE + ".g..").should().dependOnClassesThat()
                        .resideInAPackage(BASE_PACKAGE + ".a..").check(classes)).isInstanceOf(AssertionError.class);
    }

    @Test
    void ruleFailsForHDependsOnA() { // fails
        assertThatThrownBy(
                () -> noClasses().that().resideInAPackage(BASE_PACKAGE + ".h..").should().dependOnClassesThat()
                        .resideInAPackage(BASE_PACKAGE + ".a..").check(classes)).isInstanceOf(AssertionError.class);
    }

}
