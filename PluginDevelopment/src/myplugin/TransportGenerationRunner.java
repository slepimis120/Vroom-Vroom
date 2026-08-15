package myplugin;

import java.io.File;

import javax.swing.JOptionPane;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.project.ProjectsManager;
import com.nomagic.magicdraw.core.project.ProjectDescriptor;
import com.nomagic.magicdraw.core.project.ProjectDescriptorsFactory;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

import myplugin.analyzer.ModelAnalyzer;
import myplugin.generator.TransportGenerator;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;

public class TransportGenerationRunner {

    private static final String PROJECT_PATH =
            "D:\\Fakultet\\S9_10_Master\\Metodologija brzog razvoja softvera\\TransportFenerator\\TransportFenerator.mdzip";

    public static void run() {

        try {

            ProjectsManager projectsManager =
                    Application.getInstance().getProjectsManager();

            File file = new File(PROJECT_PATH);

            if (!file.exists()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Project file does not exist:\n" + PROJECT_PATH
                );
                return;
            }

            ProjectDescriptor descriptor =
                    ProjectDescriptorsFactory.createProjectDescriptor(
                            file.toURI()
                    );

            projectsManager.loadProject(descriptor, true);

            JOptionPane.showMessageDialog(
                    null,
                    "Project loaded successfully!"
            );

            Package root =
                    Application.getInstance()
                    .getProject()
                    .getModel();

            if (root == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Project loaded, but model is null."
                );
                return;
            }

            ModelAnalyzer analyzer =
                    new ModelAnalyzer(root, "transport");

            analyzer.prepareModel();

            GeneratorOptions options =
                    ProjectOptions.getProjectOptions()
                    .getGeneratorOptions()
                    .get("TransportGenerator");

            TransportGenerator generator =
                    new TransportGenerator(options);

            generator.generate();

            JOptionPane.showMessageDialog(
                    null,
                    "Transport code generated successfully!"
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Error:\n" + e.getMessage()
            );
        }
    }
}