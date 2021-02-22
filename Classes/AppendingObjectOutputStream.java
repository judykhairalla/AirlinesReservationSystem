package AirlinesReservationSystem;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AppendingObjectOutputStream extends ObjectOutputStream {

  public AppendingObjectOutputStream(OutputStream out) throws IOException {
    super(out);
  }
  
  // TO prevent rewriting the header
  @Override
  protected void writeStreamHeader() throws IOException {
    // do not write a header, but reset:
  }
} 