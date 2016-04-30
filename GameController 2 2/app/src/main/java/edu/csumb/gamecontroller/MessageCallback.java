<<<<<<< HEAD
package edu.csumb.gamecontroller;

import org.json.JSONObject;

public interface MessageCallback {
	public void on(String event, JSONObject... data);
	public void onMessage(String message);
	public void onMessage(JSONObject json);
	public void onConnect();
	public void onDisconnect();
	public void onConnectFailure();
}
=======
package edu.csumb.gamecontroller;

import org.json.JSONObject;

public interface MessageCallback {
	public void on(String event, JSONObject... data);
	public void onMessage(String message);
	public void onMessage(JSONObject json);
	public void onConnect();
	public void onDisconnect();
	public void onConnectFailure();
}
>>>>>>> 01ef4e7646051b289110c424693e9f2685168e42
