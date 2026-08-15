package testTemplates;

import java.io.FileReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import myplugin.generator.TransportGenerator;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;

public class TestModelLoader {

    public static void main(String[] args) {

        try {

            XStream xstream = new XStream(new DomDriver());

            FileReader reader =
                    new FileReader("c:/temp/transport-model.xml");

            FMModel loadedModel =
                    (FMModel) xstream.fromXML(reader);

            reader.close();

            FMModel.getInstance().loadFrom(loadedModel);

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

            System.out.println("Model loaded and code generated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}