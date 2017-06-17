package com.pekall.fms.collection.cat021_bak;
/**
 * Created by maxl on 15-9-21.
 */

public final class DiServiceIdentification {
  private byte value;


  public byte getValue() {
    return value;
  }
  public void setValue(byte value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (getClass() != o.getClass())
      return false;
    DiServiceIdentification tmp = (DiServiceIdentification) o;
    return value == tmp.value;
  }
}
