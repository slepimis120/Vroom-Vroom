package myplugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;

import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;

public class MyPlugin extends com.nomagic.magicdraw.plugins.Plugin {

    String pluginDir = null; 

    public void init() {
        pluginDir = getDescriptor().getPluginDirectory().getPath();

        ActionsConfiguratorsManager manager =
                ActionsConfiguratorsManager.getInstance();

        manager.addMainMenuConfigurator(
                new MainMenuConfigurator(getSubmenuActions())
        );

        GeneratorOptions ejbOptions = new GeneratorOptions(
                "c:/temp",
                "ejbclass",
                "templates",
                "{0}.java",
                true,
                "ejb"
        );

        ProjectOptions.getProjectOptions()
                .getGeneratorOptions()
                .put("EJBGenerator", ejbOptions);

        ejbOptions.setTemplateDir(
                pluginDir + File.separator + ejbOptions.getTemplateDir()
        );
        
        String outputPath = "c:/temp";

        File configFile = new File(pluginDir, "generator.properties");

        if (configFile.exists()) {
            try {
                Properties props = new Properties();
                FileInputStream in = new FileInputStream(configFile);
                props.load(in);
                in.close();
                outputPath = props.getProperty("OUTPUT_PATH", outputPath);
            } catch (IOException e) {
                System.err.println(
                        "Not able to read generator.properties: "
                        + e.getMessage());
            }
        }


        // OUR TRANSPORT GENERATOR

        GeneratorOptions transportOptions = new GeneratorOptions(
                outputPath,
                "transportclass",
                "templates",
                "{0}.java",
                true,
                "transport"
        );

        ProjectOptions.getProjectOptions()
                .getGeneratorOptions()
                .put("TransportGenerator", transportOptions);

        transportOptions.setTemplateDir(
                pluginDir + File.separator + transportOptions.getTemplateDir()
        );
        
    }

    private NMAction[] getSubmenuActions() {
        return new NMAction[]{
            new GenerateAction("Generate"),
        };
    }

    public boolean close() {
        return true;
    }

    public boolean isSupported() {
        return true;
    }
}