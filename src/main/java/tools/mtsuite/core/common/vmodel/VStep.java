package tools.mtsuite.core.common.vmodel;

import tools.mtsuite.core.common.model.GenericObject;

public class VStep  extends  VGenericObject {
	/****************
	 * Relationships**
	 *****************/

	/****************
	 * Attributes **
	 *****************/

	private String name;
	private String order;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
