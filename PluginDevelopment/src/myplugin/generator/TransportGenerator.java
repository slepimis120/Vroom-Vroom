package myplugin.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.StringWriter;
import java.io.File;

import javax.swing.JOptionPane;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.fmmodel.FMEnumeration;

public class TransportGenerator extends BasicGenerator {

    public TransportGenerator(GeneratorOptions generatorOptions) {
        super(generatorOptions);
    }

    public void generate() {

        try {
            super.generate();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

        List<FMClass> classes = FMModel.getInstance().getClasses();
        
        for (FMEnumeration en : FMModel.getInstance().getEnumerations()) {
            generateEnumeration(en);
        }

        for (FMClass cl : classes) {

        	if (cl.isEntity()) {
        	    generateEntity(cl);
        	}

        	if (cl.isCrud()) {
        	    generateRepository(cl);
        	    generateService(cl);
        	    generateController(cl);
        	}
        }
    }

    /*private void generateEntity(FMClass cl) {

        try {
            Writer out = getWriter(cl.getName(), cl.getTypePackage());

            if (out == null) {
                return;
            }

            Map<String, Object> context = new HashMap<String, Object>();
            context.put("class", cl);
            context.put("properties", cl.getProperties());
            context.put("importedPackages", cl.getImportedPackages());

            getTemplate().process(context, out);

            out.flush();
            out.close();

        } catch (TemplateException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }*/
    
    private void generateEntity(FMClass cl) {

        try {

            Map<String, Object> context =
                    new HashMap<String, Object>();

            context.put("class", cl);
            context.put("properties", cl.getProperties());
            context.put("importedPackages", cl.getImportedPackages());

            StringWriter generatedWriter =
                    new StringWriter();

            getTemplate().process(
                    context,
                    generatedWriter
            );

            writeGeneratedFile(
                    cl.getName(),
                    cl.getTypePackage(),
                    generatedWriter.toString()
            );

        } catch (TemplateException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
    }

    private void generateRepository(FMClass cl) {

        try {

            Configuration cfg = getCfg();

            Template repositoryTemplate =
                    cfg.getTemplate("repository.ftl");

            Map<String, Object> context =
                    new HashMap<String, Object>();

            context.put("class", cl);

            StringWriter generatedWriter =
                    new StringWriter();

            repositoryTemplate.process(
                    context,
                    generatedWriter
            );

            String oldOutputFileName =
                    getOutputFileName();

            setOutputFileName("{0}Repository.java");

            writeGeneratedFile(
                    cl.getName(),
                    cl.getTypePackage(),
                    generatedWriter.toString()
            );

            setOutputFileName(oldOutputFileName);

        } catch (TemplateException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
    }
    
    private void generateService(FMClass cl) {

        try {

            Configuration cfg = getCfg();

            Template serviceTemplate =
                    cfg.getTemplate("service.ftl");

            Map<String, Object> context =
                    new HashMap<String, Object>();

            context.put("class", cl);

            StringWriter generatedWriter =
                    new StringWriter();

            serviceTemplate.process(
                    context,
                    generatedWriter
            );

            String oldOutputFileName =
                    getOutputFileName();

            setOutputFileName("{0}Service.java");

            writeGeneratedFile(
                    cl.getName(),
                    cl.getTypePackage(),
                    generatedWriter.toString()
            );

            setOutputFileName(oldOutputFileName);

        } catch (TemplateException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
    }
    
    private void generateController(FMClass cl) {

        try {

            Configuration cfg = getCfg();

            Template controllerTemplate =
                    cfg.getTemplate("controller.ftl");

            Map<String, Object> context =
                    new HashMap<String, Object>();

            context.put("class", cl);

            StringWriter generatedWriter =
                    new StringWriter();

            controllerTemplate.process(
                    context,
                    generatedWriter
            );

            String oldOutputFileName =
                    getOutputFileName();

            setOutputFileName("{0}Controller.java");

            writeGeneratedFile(
                    cl.getName(),
                    cl.getTypePackage(),
                    generatedWriter.toString()
            );

            setOutputFileName(oldOutputFileName);

        } catch (TemplateException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
    }
    
    private void generateEnumeration(FMEnumeration en) {

        try {

            Configuration cfg = getCfg();

            Template enumerationTemplate =
                    cfg.getTemplate("enumeration.ftl");

            String oldOutputFileName =
                    getOutputFileName();

            setOutputFileName("{0}.java");

            Map<String, Object> context =
                    new HashMap<String, Object>();

            context.put("enumeration", en);

            StringWriter generatedWriter =
                    new StringWriter();

            enumerationTemplate.process(
                    context,
                    generatedWriter
            );

            writeGeneratedFile(
                    en.getName(),
                    en.getTypePackage(),
                    generatedWriter.toString()
            );

            setOutputFileName(oldOutputFileName);

        } catch (TemplateException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage()
            );
        }
    }
    
}