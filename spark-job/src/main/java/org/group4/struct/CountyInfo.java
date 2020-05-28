package org.group4.struct;

import java.io.Serializable;

public class CountyInfo implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private String usps;
  private String fips;
  private String ansi;
  private String name;
  private double areaLandMeters;
  private double areaWaterMeters;
  private double areaLandMiles;
  private double areaWaterMiles;
  private double latitude;
  private double longitude;

  public CountyInfo() {

  }

  public CountyInfo(String usps, String fips, String ansi, String name, double areaLandMeters, double areaWaterMeters,
      double areaLandMiles, double areaWaterMiles, double latitude, double longitude) {
    this.usps = usps;
    this.fips = fips;
    this.ansi = ansi;
    this.name = name;
    this.areaLandMeters = areaLandMeters;
    this.areaWaterMeters = areaWaterMeters;
    this.areaLandMiles = areaLandMiles;
    this.areaWaterMiles = areaWaterMiles;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getUsps() {
    return usps;
  }

  public String getFips() {
    return fips;
  }

  public String getAnsi() {
    return ansi;
  }

  public String getName() {
    return name;
  }

  public double getAreaLandMeters() {
    return areaLandMeters;
  }

  public double getAreaWaterMeters() {
    return areaWaterMeters;
  }

  public double getAreaLandMiles() {
    return areaLandMiles;
  }

  public double getAreaWaterMiles() {
    return areaWaterMiles;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

}