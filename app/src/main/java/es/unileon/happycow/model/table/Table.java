package es.unileon.happycow.model.table;

import java.util.HashMap;

public class Table {
	private HashMap<String, Boolean> _hashMap;
	private static Table _table;

	private Table() {
		this._hashMap = new HashMap<String, Boolean>();

		// Evaluacion
		this._hashMap.put(Entity.EVALUATION.toString() + Entity.CATEGORY.toString(), true);
		this._hashMap.put(Entity.EVALUATION.toString() + Entity.CRITERION.toString(), false);
		this._hashMap.put(Entity.EVALUATION.toString() + Entity.VALORATION.toString(), false);
		this._hashMap.put(Entity.EVALUATION.toString() + Entity.EVALUATION.toString(), false);
		// Categoria

		this._hashMap.put(Entity.CATEGORY.toString() + Entity.CRITERION.toString(), true);
		this._hashMap.put(Entity.CATEGORY.toString() + Entity.VALORATION.toString(), false);
		this._hashMap.put(Entity.CATEGORY.toString() + Entity.EVALUATION.toString(), false);
		this._hashMap.put(Entity.CATEGORY.toString() + Entity.CATEGORY.toString(), false);
		// Criterio
		this._hashMap.put(Entity.CRITERION.toString() + Entity.VALORATION.toString(), true);
		this._hashMap.put(Entity.CRITERION.toString() + Entity.EVALUATION.toString(), false);
		this._hashMap.put(Entity.CRITERION.toString() + Entity.CATEGORY.toString(), false);
		this._hashMap.put(Entity.CRITERION.toString() + Entity.CRITERION.toString(), false);
		// Valoracion
		this._hashMap.put(Entity.VALORATION.toString() + Entity.EVALUATION.toString(), false);
		this._hashMap.put(Entity.VALORATION.toString() + Entity.CATEGORY.toString(), false);
		this._hashMap.put(Entity.VALORATION.toString() + Entity.CRITERION.toString(), false);
		this._hashMap.put(Entity.VALORATION.toString() + Entity.VALORATION.toString(), false);
	}

	public boolean canAdd(Entity up, Entity down) {
		Boolean result = (Boolean) this._hashMap.get(up.toString() + down.toString());
		if(result != null) {
			return result;
		}
		return false;
	}
	
	public static Table getInstance() {
		if(_table == null) {
			_table = new Table();
		}
		return _table;
	}
}
