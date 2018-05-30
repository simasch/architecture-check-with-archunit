package ch.martinelli;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packagesOf = Application.class)
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule layerRule = layeredArchitecture()
            .layer("boundary").definedBy("..boundary..")
            .layer("service").definedBy("..control.service..")
            .layer("repository").definedBy("..control.repository..")
            .layer("entity").definedBy("..entity..")

            .whereLayer("boundary").mayNotBeAccessedByAnyLayer()
            .whereLayer("service").mayOnlyBeAccessedByLayers("boundary")
            .whereLayer("repository").mayOnlyBeAccessedByLayers("boundary", "service")
            .whereLayer("entity").mayOnlyBeAccessedByLayers("boundary", "service");
}
