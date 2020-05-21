package org.group4;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        bucketOperations.getFromBucket("output.csv", "db-job/src/main/resources/downloadedIntoDb-Job.csv");
    }
}
