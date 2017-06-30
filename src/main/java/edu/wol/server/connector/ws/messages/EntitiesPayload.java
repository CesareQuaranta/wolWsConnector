package edu.wol.server.connector.ws.messages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.wol.dom.Power;
import edu.wol.dom.WolEntity;
import edu.wol.dom.space.iCoordinate;

public class EntitiesPayload<E extends WolEntity,P extends iCoordinate> {
private Map<E,P> positions=new HashMap<E,P>(1);
private Map<E,Power> physics=new HashMap<E,Power>(1);

public boolean addEntity(E entity ,P position){
	return positions.put(entity, position)!=null;
}
public boolean addPhysic(E entity ,Power physic){
	if(positions.containsKey(entity)){
		return physics.put(entity, physic)!=null;
	}else{
		return false;
	}
}
public Iterator<E> iterator(){
	return positions.keySet().iterator();
} 

public P getPosition(E entity){
	return positions.get(entity);
}

}
