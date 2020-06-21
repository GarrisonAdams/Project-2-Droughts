package org.group4.struct;

import java.io.Serializable;
import java.sql.Date;

public class County implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private String releaseDate;
  private String FIPS;
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
  private long ALAND;
  private long AWATER;
  private double AWATER_SQMI;
  private double ALAND_SQMI;
  private double INTPTLAT;
  private double INTPTLONG;

  public County(String releaseDate, String fIPS, String county, String state, int nONE, int d0, int d1, int d2, int d3,
      int d4, Date validStart, Date validEnd, long aLAND, long aWATER, double aWATER_SQMI, double aLAND_SQMI,
      double iNTPTLAT, double iNTPTLONG) {
    this.releaseDate = releaseDate;
    FIPS = fIPS;
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
    ALAND = aLAND;
    AWATER = aWATER;
    AWATER_SQMI = aWATER_SQMI;
    ALAND_SQMI = aLAND_SQMI;
    INTPTLAT = iNTPTLAT;
    INTPTLONG = iNTPTLONG;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public String getFIPS() {
    return FIPS;
  }

  public String getCounty() {
    return county;
  }

  public String getState() {
    return state;
  }

  public int getNONE() {
    return NONE;
  }

  public int getD0() {
    return D0;
  }

  public int getD1() {
    return D1;
  }

  public int getD2() {
    return D2;
  }

  public int getD3() {
    return D3;
  }

  public int getD4() {
    return D4;
  }

  public Date getValidStart() {
    return validStart;
  }

  public Date getValidEnd() {
    return validEnd;
  }

  public long getALAND() {
    return ALAND;
  }

  public long getAWATER() {
    return AWATER;
  }

  public double getAWATER_SQMI() {
    return AWATER_SQMI;
  }

  public double getALAND_SQMI() {
    return ALAND_SQMI;
  }

  public double getINTPTLAT() {
    return INTPTLAT;
  }

  public double getINTPTLONG() {
    return INTPTLONG;
  }
}