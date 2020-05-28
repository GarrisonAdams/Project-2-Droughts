package org.group4.struct;

import java.io.Serializable;
import java.sql.Date;

public class County implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private Date releaseDate;
  private String fips;
  private String county;
  private String state;
  private double NONE;
  private double D0;
  private double D1;
  private double D2;
  private double D3;
  private double D4;
  private Date validStart;
  private Date validEnd;

  public County(Date releaseDate, String fips, String county, String state, double nONE, double d0, double d1, double d2, double d3,
  double d4, Date validStart, Date validEnd) {
    this.releaseDate = releaseDate;
    this.fips = fips;
    this.county = county;
    this.state = state;
    NONE = nONE;
    D0 = d0;
    D1 = d1;
    D2 = d2;
    D3 = d3;
    D4 = d4;
    this.validStart = validStart;
    this.validEnd = validEnd;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getFips() {
    return fips;
  }

  public void setFips(String fips) {
    this.fips = fips;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public double getNONE() {
    return NONE;
  }

  public void setNONE(double nONE) {
    NONE = nONE;
  }

  public double getD0() {
    return D0;
  }

  public void setD0(double d0) {
    D0 = d0;
  }

  public double getD1() {
    return D1;
  }

  public void setD1(double d1) {
    D1 = d1;
  }

  public double getD2() {
    return D2;
  }

  public void setD2(double d2) {
    D2 = d2;
  }

  public double getD3() {
    return D3;
  }

  public void setD3(double d3) {
    D3 = d3;
  }

  public double getD4() {
    return D4;
  }

  public void setD4(double d4) {
    D4 = d4;
  }

  public Date getValidStart() {
    return validStart;
  }

  public void setValidStart(Date validStart) {
    this.validStart = validStart;
  }

  public Date getValidEnd() {
    return validEnd;
  }

  public void setValidEnd(Date validEnd) {
    this.validEnd = validEnd;
  }
public String toString()
{
  return getReleaseDate() + "," + getFips()  + "," + getCounty() + "," + getState() + "," + getNONE() + "," + 
    getD0()+ "," + getD1()+ "," + getD2() + "," + getD3() + "," + getD4() + "," + getValidStart() + "," + getValidEnd();
}


}