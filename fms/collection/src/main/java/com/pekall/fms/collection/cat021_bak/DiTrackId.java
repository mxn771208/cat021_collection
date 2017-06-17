package com.pekall.fms.collection.cat021_bak;
/**
 * Created by maxl on 15-9-21.
 */

public final class DiTrackId {
  private short value;


  public short getValue() {
    return value;
  }
  public void setValue(short value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (getClass() != o.getClass())
      return false;
    DiTrackId tmp = (DiTrackId) o;
    return value == tmp.value;
  }
}
