package ch.martinelli;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class ArchitectureTest {

    JavaClasses importedClasses = new ClassFileImporter().importPackages("ch.martinelli");

    @Test
    public void layering() {
        ArchRule layerRule = layeredArchitecture()
                .layer("api").definedBy("..api..")
                .layer("service").definedBy("..service..")
                .layer("repository").definedBy("..repository..")
                .layer("entity").definedBy("..entity..")

                .whereLayer("api").mayNotBeAccessedByAnyLayer()
                .whereLayer("service").mayOnlyBeAccessedByLayers("api")
                .whereLayer("repository").mayOnlyBeAccessedByLayers("api", "service")
                .whereLayer("entity").mayOnlyBeAccessedByLayers("api", "service", "repository");

        layerRule.check(importedClasses);
    }

    @Test
    public void cylces() {
        ArchRule cycleRule = slices()
                .matching("ch.martinelli.(*)..")
                .should().beFreeOfCycles();

        cycleRule.check(importedClasses);
    }
}
