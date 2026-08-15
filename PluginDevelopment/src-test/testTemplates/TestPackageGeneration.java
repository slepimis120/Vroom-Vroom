package testTemplates;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import myplugin.generator.TransportGenerator;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;
import myplugin.generator.fmmodel.FMEnumeration;

public class TestPackageGeneration {

    public TestPackageGeneration() {
    }

    private void initModel() {

        FMModel.getInstance().getClasses().clear();
        FMModel.getInstance().getEnumerations().clear();

        // =========================
        // Enumeration
        // =========================

        FMEnumeration transportType =
                new FMEnumeration("TransportType", "transport");

        transportType.addValue("BUS");
        transportType.addValue("TRAIN");
        transportType.addValue("TRUCK");

        FMModel.getInstance()
                .getEnumerations()
                .add(transportType);


        // =========================
        // Classes
        // =========================

        List<FMClass> classes =
                FMModel.getInstance().getClasses();


        // =========================
        // Transport
        // =========================

        FMClass transport = new FMClass(
                "Transport",
                "transport",
                "public"
        );

        transport.setEntity(true);
        transport.setCrud(true);

        transport.addProperty(
                new FMProperty(
                        "id",
                        "Long",
                        "private",
                        1,
                        1
                )
        );

        transport.addProperty(
                new FMProperty(
                        "name",
                        "String",
                        "private",
                        1,
                        1
                )
        );

        transport.addProperty(
                new FMProperty(
                        "origin",
                        "String",
                        "private",
                        1,
                        1
                )
        );

        transport.addProperty(
                new FMProperty(
                        "destination",
                        "String",
                        "private",
                        1,
                        1
                )
        );

        transport.addProperty(
                new FMProperty(
                        "type",
                        "TransportType",
                        "private",
                        1,
                        1
                )
        );


        // Schedule 0..* ---- 1 Transport
        // Transport -> Schedule : 1
        // Schedule -> Transport : 0..*

        FMProperty transportSchedule =
                new FMProperty(
                        "schedule",
                        "Schedule",
                        "private",
                        1,
                        1
                );

        transportSchedule.setAssociation(true);
        transportSchedule.setOppositeUpper(-1);

        transport.addProperty(transportSchedule);


        // Transport 1..* ---- 1 TransportStop
        // Transport -> TransportStop : 1..*
        // TransportStop -> Transport : 1

        FMProperty transportStops =
                new FMProperty(
                        "transportStops",
                        "TransportStop",
                        "private",
                        1,
                        -1
                );

        transportStops.setAssociation(true);
        transportStops.setOppositeUpper(1);

        transport.addProperty(transportStops);


        // =========================
        // Station
        // =========================

        FMClass station = new FMClass(
                "Station",
                "transport",
                "public"
        );

        station.setEntity(true);
        station.setCrud(true);

        station.addProperty(
                new FMProperty(
                        "id",
                        "Long",
                        "private",
                        1,
                        1
                )
        );

        station.addProperty(
                new FMProperty(
                        "name",
                        "String",
                        "private",
                        1,
                        1
                )
        );


        // TransportStop 0..* ---- 1 Station
        // Station -> TransportStop : 0..*
        // TransportStop -> Station : 1

        FMProperty stationTransportStops =
                new FMProperty(
                        "transportStops",
                        "TransportStop",
                        "private",
                        0,
                        -1
                );

        stationTransportStops.setAssociation(true);
        stationTransportStops.setOppositeUpper(1);

        station.addProperty(stationTransportStops);


        // =========================
        // TransportStop
        // =========================

        FMClass transportStop = new FMClass(
                "TransportStop",
                "transport",
                "public"
        );

        transportStop.setEntity(true);
        transportStop.setCrud(true);

        transportStop.addProperty(
                new FMProperty(
                        "id",
                        "Long",
                        "private",
                        1,
                        1
                )
        );

        transportStop.addProperty(
                new FMProperty(
                        "sequence",
                        "Integer",
                        "private",
                        1,
                        1
                )
        );


        // TransportStop -> Transport : 1

        FMProperty transportStopTransport =
                new FMProperty(
                        "transport",
                        "Transport",
                        "private",
                        1,
                        1
                );

        transportStopTransport.setAssociation(true);
        transportStopTransport.setOppositeUpper(-1);

        transportStop.addProperty(
                transportStopTransport
        );


        // TransportStop -> Station : 1

        FMProperty transportStopStation =
                new FMProperty(
                        "station",
                        "Station",
                        "private",
                        1,
                        1
                );

        transportStopStation.setAssociation(true);
        transportStopStation.setOppositeUpper(-1);

        transportStop.addProperty(
                transportStopStation
        );


        // =========================
        // Schedule
        // =========================

        FMClass schedule = new FMClass(
                "Schedule",
                "transport",
                "public"
        );

        schedule.setEntity(true);
        schedule.setCrud(true);

        schedule.addProperty(
                new FMProperty(
                        "id",
                        "Long",
                        "private",
                        1,
                        1
                )
        );

        schedule.addProperty(
                new FMProperty(
                        "departure",
                        "Date",
                        "private",
                        1,
                        1
                )
        );

        schedule.addProperty(
                new FMProperty(
                        "arrival",
                        "Date",
                        "private",
                        1,
                        1
                )
        );


        // Schedule -> Transport : 0..*

        FMProperty scheduleTransports =
                new FMProperty(
                        "transports",
                        "Transport",
                        "private",
                        0,
                        -1
                );

        scheduleTransports.setAssociation(true);
        scheduleTransports.setOppositeUpper(1);

        schedule.addProperty(
                scheduleTransports
        );


        // =========================
        // Add classes to model
        // =========================

        classes.add(transport);
        classes.add(station);
        classes.add(transportStop);
        classes.add(schedule);
    }


    private void exportModelToXml() {

        try {

            XStream xstream =
                    new XStream(new DomDriver());

            BufferedWriter writer =
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(
                                            "c:/temp/transport-model.xml"
                                    ),
                                    "UTF-8"
                            )
                    );

            xstream.toXML(
                    FMModel.getInstance(),
                    writer
            );

            writer.close();

            System.out.println(
                    "FMModel exported to c:/temp/transport-model.xml"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public void testGenerator() {

        initModel();

        GeneratorOptions go =
                ProjectOptions
                        .getProjectOptions()
                        .getGeneratorOptions()
                        .get("TransportGenerator");

        TransportGenerator generator =
                new TransportGenerator(go);

        generator.generate();

        exportModelToXml();
    }


    public static void main(String[] args) {

        TestPackageGeneration tg =
                new TestPackageGeneration();

        GeneratorOptions transportOptions =
                new GeneratorOptions(
                        "c:/temp",
                        "transportclass",
                        "./resources/templates/",
                        "{0}.java",
                        true,
                        "transport"
                );

        ProjectOptions
                .getProjectOptions()
                .getGeneratorOptions()
                .put(
                        "TransportGenerator",
                        transportOptions
                );

        tg.testGenerator();
    }
}