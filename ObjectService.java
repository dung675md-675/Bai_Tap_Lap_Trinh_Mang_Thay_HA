package RMI;

public interface ObjectService extends java.rmi.Remote {
    java.io.Serializable requestObject(String studentCode, String qCode) throws java.rmi.RemoteException;
    void submitObject(String studentCode, String qCode, java.io.Serializable object) throws java.rmi.RemoteException;
}