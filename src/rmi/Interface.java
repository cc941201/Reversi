package rmi;

import java.rmi.Remote;

import ui.*;

public interface Interface extends Remote {
	public void move(Coordinate c) throws Exception;

	public void mouseEntered(Coordinate c) throws Exception;

	public void mouseExited() throws Exception;

	public void connect(boolean black, Interface remote, String map)
			throws Exception;

	public void set(Main frame, String map) throws Exception;

	public void close() throws Exception;

	public void restart() throws Exception;
}
