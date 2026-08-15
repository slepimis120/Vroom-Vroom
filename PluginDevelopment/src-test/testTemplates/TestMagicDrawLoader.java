package testTemplates;

import java.io.File;

import com.nomagic.magicdraw.commandline.CommandLine;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.core.project.ProjectDescriptor;
import com.nomagic.magicdraw.core.project.ProjectDescriptorsFactory;

import myplugin.analyzer.ModelAnalyzer;
import myplugin.generator.TransportGenerator;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;

public class TestMagicDrawLoader extends CommandLine {

    public static void main(String[] args) {
        new TestMagicDrawLoader().launch(args);
    }

    @Override
    protected byte execute() {

        try {

            String projectPath =
                    "D:\\Fakultet\\S9_10_Master\\Metodologija brzog razvoja softvera\\TransportFenerator\\TransportFenerator.mdzip";

            File file = new File(projectPath);

            ProjectDescriptor descriptor =
                    ProjectDescriptorsFactory.createProjectDescriptor(
                            file.toURI()
                    );

            Application.getInstance()
                    .getProjectsManager()
                    .loadProject(descriptor, true);

            Project project =
                    Application.getInstance()
                    .getProjectsManager()
                    .getActiveProject();

            if (project == null) {
                System.out.println("Project was not loaded.");
                return 1;
            }

            System.out.println("Project loaded successfully!");

            ModelAnalyzer analyzer =
                    new ModelAnalyzer(project.getModel(), "transport");

            analyzer.prepareModel();

            GeneratorOptions transportOptions =
                    new GeneratorOptions(
                            "c:/temp",
                            "transportclass",
                            "./resources/templates/",
                            "{0}.java",
                            true,
                            "transport"
                    );

            ProjectOptions.getProjectOptions()
                    .getGeneratorOptions()
                    .put("TransportGenerator", transportOptions);

            TransportGenerator generator =
                    new TransportGenerator(transportOptions);

            generator.generate();

            System.out.println(
                    "Transport code generated successfully!"
            );

            return 0;

        } catch (Exception e) {

            e.printStackTrace();

            return 1;
        }
    }
}