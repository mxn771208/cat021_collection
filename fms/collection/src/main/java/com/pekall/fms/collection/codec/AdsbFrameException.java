package com.pekall.fms.collection.codec;
import io.netty.handler.codec.DecoderException;

/**
 * Created by maxl on 15-9-23.
 */
public class AdsbFrameException extends DecoderException{
  private static final long serialVersionUID = 2549060321711793348L;
  /**
   * Creates a new instance.
   */
  public AdsbFrameException() {
  }

  /**
   * Creates a new instance.
   */
  public AdsbFrameException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new instance.
   */
  public AdsbFrameException(String message) {
    super(message);
  }

  /**
   * Creates a new instance.
   */
  public AdsbFrameException(Throwable cause) {
    super(cause);
  }
}
