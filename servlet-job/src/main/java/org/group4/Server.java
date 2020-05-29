package org.group4;

import java.io.File;

import org.apache.catalina.LifecycleException;
//import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

public class Server {

    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(new File("target/tomcat/").getAbsolutePath());
        tomcat.setPort(8080);
        tomcat.getConnector();
        tomcat.addWebapp("/project2", new File("src/main/resources").getAbsolutePath());
        tomcat.addServlet("/project2", "DroughtLevelServlet", new DroughtLevelServlet())
                .addMapping("/DroughtLevelServlet");

        tomcat.addServlet("/project2", "SpecificCountyServlet", new SpecificCountyServlet())
                .addMapping("/SpecificCountyServlet");

        tomcat.addServlet("/project2", "SpecificStateServlet", new SpecificStateServlet())
                .addMapping("/SpecificStateServlet");

        tomcat.addServlet("/project2", "DroughtMostServlet", new DroughtMostServlet())
                .addMapping("/DroughtMostServlet");

        tomcat.addServlet("/project2", "DroughtLevelYearServlet", new DroughtLevelYearServlet())
                .addMapping("/DroughtLevelYearServlet");

        tomcat.addServlet("/project2", "DroughtAvgCatServlet", new DroughtAvgCatServlet())
                .addMapping("/DroughtAvgCatServlet");

        tomcat.addServlet("/project2", "DroughtAvgCatColServlet", new DroughtAvgCatColServlet())
                .addMapping("/DroughtAvgCatColServlet");

        tomcat.addServlet("/project2", "DroughtPRServlet", new DroughtPRServlet()).addMapping("/DroughtPRServlet");

        tomcat.start();

        // Runtime.getRuntime().addShutdownHook(new Thread() {
        // @Override
        // public void run() {
        // try {
        // System.out.println("Shutting down tomcat");
        // tomcat.stop();

        // } catch (LifecycleException e) {
        // e.printStackTrace();
        // }
        // }
        // });

    }
}