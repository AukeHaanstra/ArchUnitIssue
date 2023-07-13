package org.example;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaCodeUnit;
import com.tngtech.archunit.core.domain.TryCatchBlock;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import org.assertj.core.api.Assertions;
import org.example.d.D;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.SimpleConditionEvent.satisfied;
import static com.tngtech.archunit.lang.conditions.ArchConditions.dependOnClassesThat;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DependencyAssertionsTest {

    static final String BASE_PACKAGE = "org.example";
    static final String AND_SUBPACKAGES = "..";

    JavaClasses classes = new ClassFileImporter().importPackages(BASE_PACKAGE + AND_SUBPACKAGES);

    @Test
    void ruleFailsForCDependsOnA() {
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
    void ruleFailsForDDependsOnA_workaround() { // now passes with workaround
        assertThatThrownBy(() -> noClasses().that().resideInAPackage(BASE_PACKAGE + ".d..")
                .should(dependOnClassesInPackage(BASE_PACKAGE + ".a..")).check(classes)).isInstanceOf(
                AssertionError.class);
    }

    ArchCondition<JavaClass> dependOnClassesInPackage(String packageIdentifier) {
        return dependOnClassesThat(resideInAPackage(packageIdentifier)).or(new ArchCondition<>("<overridden>") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                for (JavaCodeUnit codeUnit : javaClass.getCodeUnits()) {
                    for (TryCatchBlock tryCatchBlock : codeUnit.getTryCatchBlocks()) {
                        tryCatchBlock.getCaughtThrowables().stream().filter(resideInAPackage(packageIdentifier))
                                .map(throwable -> satisfied(codeUnit,
                                        codeUnit.getDescription() + " catches " + throwable.getName() + " in " +
                                                tryCatchBlock.getSourceCodeLocation())).forEach(events::add);
                    }
                }
            }
        }).as("depend on classes in package '%s'", packageIdentifier);
    }

    @Test
    void ruleFailsForEDependsOnA() {
        assertThatThrownBy(
                () -> noClasses().that().resideInAPackage(BASE_PACKAGE + ".e..").should().dependOnClassesThat()
                        .resideInAPackage(BASE_PACKAGE + ".a..").check(classes)).isInstanceOf(AssertionError.class);
    }

    @Test
    void ruleFailsForFDependsOnA() { // fails, local variables not analyzed at bytecode level
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
    void ruleFailsForHDependsOnA() { // fails, local variables not analyzed at bytecode level
        assertThatThrownBy(
                () -> noClasses().that().resideInAPackage(BASE_PACKAGE + ".h..").should().dependOnClassesThat()
                        .resideInAPackage(BASE_PACKAGE + ".a..").check(classes)).isInstanceOf(AssertionError.class);
    }

    @Test
    void getsExceptionFromTryCatch() {
        Set<JavaClass> caughtClasses =
                classes.get(D.class).getMethod("doD").getTryCatchBlocks().iterator().next().getCaughtThrowables();
        Assertions.assertThat(caughtClasses)
                .anySatisfy(javaClass -> assertThat(javaClass.getFullName()).isEqualTo("org.example.a.Ex"));
    }

}
