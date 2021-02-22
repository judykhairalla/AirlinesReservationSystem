package AirlinesReservationSystem;

import java.io.IOException;


public interface FileInterface {

    public void addObject(Object o)throws IOException, ClassNotFoundException;
    public void removeObject(String objectName) throws IOException, ClassNotFoundException;
    
}