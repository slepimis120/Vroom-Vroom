package myplugin.analyzer;

import java.util.Iterator;
import java.util.List;

import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMProperty;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Association;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type;


/**
 * Model Analyzer extracts metadata from the MagicDraw model
 * and stores it in the intermediate FMModel structure.
 */
public class ModelAnalyzer {

    private Package root;

    private String filePackage;

    public ModelAnalyzer(Package root, String filePackage) {
        super();
        this.root = root;
        this.filePackage = filePackage;
    }

    public Package getRoot() {
        return root;
    }

    public void prepareModel() throws AnalyzeException {

        FMModel.getInstance().getClasses().clear();
        FMModel.getInstance().getEnumerations().clear();

        processPackage(root, filePackage);
    }

    private void processPackage(
            Package pack,
            String packageOwner) throws AnalyzeException {

        if (pack.getName() == null) {
            throw new AnalyzeException("Packages must have names!");
        }

        String packageName = packageOwner;

        if (pack != root) {
            packageName += "." + pack.getName();
        }

        if (pack.hasOwnedElement()) {

            for (Iterator<Element> it =
                    pack.getOwnedElement().iterator(); it.hasNext();) {

                Element ownedElement = it.next();

                if (ownedElement instanceof Class) {

                    Class cl = (Class) ownedElement;

                    if (StereotypesHelper
                            .getAppliedStereotypeByString(cl, "Entity") != null) {

                        FMClass fmClass =
                                getClassData(cl, packageName);

                        FMModel.getInstance()
                                .getClasses()
                                .add(fmClass);
                    }
                }

                if (ownedElement instanceof Enumeration) {

                    Enumeration en =
                            (Enumeration) ownedElement;

                    FMEnumeration fmEnumeration =
                            getEnumerationData(en, packageName);

                    FMModel.getInstance()
                            .getEnumerations()
                            .add(fmEnumeration);
                }
            }

            for (Iterator<Element> it =
                    pack.getOwnedElement().iterator(); it.hasNext();) {

                Element ownedElement = it.next();

                if (ownedElement instanceof Package) {

                    Package ownedPackage =
                            (Package) ownedElement;

                    if (StereotypesHelper
                            .getAppliedStereotypeByString(
                                    ownedPackage,
                                    "BusinessApp") != null) {

                        processPackage(
                                ownedPackage,
                                packageName);
                    }
                }
            }
        }
    }

    private FMClass getClassData(
            Class cl,
            String packageName) throws AnalyzeException {

        if (cl.getName() == null) {
            throw new AnalyzeException(
                    "Classes must have names!");
        }

        FMClass fmClass =
                new FMClass(
                        cl.getName(),
                        packageName,
                        cl.getVisibility().toString());

        fmClass.setCrud(
                StereotypesHelper
                        .getAppliedStereotypeByString(
                                cl,
                                "CRUD") != null
        );

        fmClass.setEntity(
                StereotypesHelper
                        .getAppliedStereotypeByString(
                                cl,
                                "Entity") != null
        );

        /*
         * Ordinary attributes
         */
        Iterator<Property> it =
                ModelHelper.attributes(cl);

        while (it.hasNext()) {

            Property p = it.next();

            /*
             * Association ends are processed separately.
             */
            if (p.getAssociation() != null) {
                continue;
            }

            FMProperty prop =
                    getPropertyData(p, cl, false);

            fmClass.addProperty(prop);
        }

        /*
         * Association ends
         */
        for (Property p : cl.getOwnedAttribute()) {

            if (p.getAssociation() != null) {

                FMProperty prop =
                        getPropertyData(p, cl, true);

                fmClass.addProperty(prop);
            }
        }

        return fmClass;
    }

    private FMProperty getPropertyData(
            Property p,
            Class cl,
            boolean association) throws AnalyzeException {

        String attName = p.getName();

        /*
         * Association ends without a name are given
         * a name based on the target class.
         */
        if (attName == null || attName.trim().isEmpty()) {

            Type targetType = p.getType();

            if (targetType != null &&
                    targetType.getName() != null) {

                String targetName =
                        targetType.getName();

                attName =
                        Character.toLowerCase(
                                targetName.charAt(0))
                        + targetName.substring(1);

            } else {

                throw new AnalyzeException(
                        "Property of class "
                        + cl.getName()
                        + " must have a name!");
            }
        }

        Type attType = p.getType();

        if (attType == null) {

            throw new AnalyzeException(
                    "Property "
                    + cl.getName()
                    + "."
                    + attName
                    + " must have type!");
        }

        String typeName = attType.getName();

        if (typeName == null) {

            throw new AnalyzeException(
                    "Type of property "
                    + cl.getName()
                    + "."
                    + attName
                    + " must have name!");
        }

        int lower = p.getLower();
        int upper = p.getUpper();

        FMProperty prop =
                new FMProperty(
                        attName,
                        typeName,
                        p.getVisibility().toString(),
                        lower,
                        upper
                );

        prop.setAssociation(association);

        /*
         * ----------------------------------------------------
         * Find opposite end of the association
         * ----------------------------------------------------
         */
        if (association) {

            Association assoc = p.getAssociation();

            if (assoc != null) {

                for (Property opposite :
                        assoc.getMemberEnd()) {

                    /*
                     * Skip current association end.
                     */
                    if (opposite == p) {
                        continue;
                    }

                    /*
                     * Store the upper multiplicity of
                     * the opposite association end.
                     */
                    prop.setOppositeUpper(
                            opposite.getUpper()
                    );

                    break;
                }
            }
        }

        return prop;
    }

    private FMEnumeration getEnumerationData(
            Enumeration enumeration,
            String packageName)
            throws AnalyzeException {

        FMEnumeration fmEnum =
                new FMEnumeration(
                        enumeration.getName(),
                        packageName);

        List<EnumerationLiteral> list =
                enumeration.getOwnedLiteral();

        for (int i = 0; i < list.size(); i++) {

            EnumerationLiteral literal =
                    list.get(i);

            if (literal.getName() == null) {

                throw new AnalyzeException(
                        "Items of the enumeration "
                        + enumeration.getName()
                        + " must have names!");
            }

            fmEnum.addValue(
                    literal.getName());
        }

        return fmEnum;
    }
}