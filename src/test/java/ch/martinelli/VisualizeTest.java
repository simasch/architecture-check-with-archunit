package ch.martinelli;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.Test;

public class VisualizeTest {

    JavaClasses classes = new ClassFileImporter().importPackages("ch.martinelli.api", "ch.martinelli.control", "ch.martinelli.entity");

    @Test
    public void draw() {
        for (JavaClass javaClass : classes) {
            System.out.println(javaClass.getName());
            System.out.println("->");
            for (Dependency dependency : javaClass.getDirectDependenciesFromSelf()) {
                if (!dependency.getTargetClass().getName().equals(Object.class.getName())) {
                    System.out.println("   | " + dependency.getTargetClass().getName());
                }
            }
        }
    }
}
