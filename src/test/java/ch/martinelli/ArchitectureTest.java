package ch.martinelli;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packagesOf = Application.class)
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule layerRule = layeredArchitecture()
            .layer("api").definedBy("..api..")
            .layer("service").definedBy("..service..")
            .layer("repository").definedBy("..repository..")
            .layer("entity").definedBy("..entity..")

            .whereLayer("api").mayNotBeAccessedByAnyLayer()
            .whereLayer("service").mayOnlyBeAccessedByLayers("api")
            .whereLayer("repository").mayOnlyBeAccessedByLayers("api", "service")
            .whereLayer("entity").mayOnlyBeAccessedByLayers("api", "service", "repository");

    @ArchTest
    public static final ArchRule cycleRule = slices()
            .matching("ch.martinelli.(*)..")
            .should().beFreeOfCycles();
}
