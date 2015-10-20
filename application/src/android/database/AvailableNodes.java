package android.database;

import java.util.ArrayList;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class AvailableNodes implements KvmSerializable {

	private ArrayList<String> nodes;

	public AvailableNodes() {

		nodes = new ArrayList<String>();
	}

	public ArrayList<String> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<String> nodes) {
		this.nodes = nodes;
	}

	public void insert(String a) {
		nodes.add(a);
	}

	@Override
	public Object getProperty(int arg0) {

		return nodes.get(arg0);

	}

	@Override
	public int getPropertyCount() {
		return nodes.size();
	}

	@Override
	public void getPropertyInfo(int arg0,
			@SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {

		arg2.type = PropertyInfo.STRING_CLASS;
		arg2.name = "nodes";

	}

	@SuppressWarnings("unchecked")
	@Override
	public void setProperty(int arg0, Object arg1) {

		this.nodes = (ArrayList<String>) arg1;

	}
}