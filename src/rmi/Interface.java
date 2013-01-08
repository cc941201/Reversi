package rmi;

import java.rmi.Remote;

import ui.*;

public interface Interface extends Remote {
	public void move(Coordinate c) throws Exception;

	public void mouseEntered(Coordinate c) throws Exception;

	public void mouseExited(Coordinate c) throws Exception;

	public void connect(boolean black, Interface remote) throws Exception;

	public void setMain(Main frame) throws Exception;
}
