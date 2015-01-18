package mta.se.proiectchat.mainpackage.tcpconnctionpackage;

/**
 * Created by ADY on 07.01.2015.
 */
public interface ISocketConnection {
    public void Open();

    public byte[] Read();

    public void Write(byte[] information);

    public void Close();
}
