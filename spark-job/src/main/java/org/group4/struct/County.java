package org.group4.struct;

import java.io.Serializable;
import java.sql.Date;

public class County implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private String releaseDate;
  private String fips;
  private String county;
  private String state;
  private int NONE;
  private int D0;
  private int D1;
  private int D2;
  private int D3;
  private int D4;
  private Date validStart;
  private Date validEnd;

  public County(String releaseDate, String fips, String county, String state, int nONE, int d0, int d1, int d2, int d3,
      int d4, Date validStart, Date validEnd) {
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

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
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

  public int getNONE() {
    return NONE;
  }

  public void setNONE(int nONE) {
    NONE = nONE;
  }

  public int getD0() {
    return D0;
  }

  public void setD0(int d0) {
    D0 = d0;
  }

  public int getD1() {
    return D1;
  }

  public void setD1(int d1) {
    D1 = d1;
  }

  public int getD2() {
    return D2;
  }

  public void setD2(int d2) {
    D2 = d2;
  }

  public int getD3() {
    return D3;
  }

  public void setD3(int d3) {
    D3 = d3;
  }

  public int getD4() {
    return D4;
  }

  public void setD4(int d4) {
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

}