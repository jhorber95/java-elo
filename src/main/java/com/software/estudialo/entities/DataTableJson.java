/**
 * 
 */
package com.software.estudialo.entities;

import java.util.List;

/**
 * @author LUIS
 *
 */
public class DataTableJson {

	private int start;
	private int length;
	private JsonSearchObject search;
	private List<ItemOrder> order;
	private int draw;

	public DataTableJson() {
		super();
	}

	public DataTableJson(int start, int length, JsonSearchObject search, List<ItemOrder> order, int draw) {
		super();
		this.start = start;
		this.length = length;
		this.search = search;
		this.order = order;
		this.draw = draw;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public JsonSearchObject getSearch() {
		return search;
	}

	public void setSearch(JsonSearchObject search) {
		this.search = search;
	}

	public List<ItemOrder> getOrder() {
		return order;
	}

	public void setOrder(List<ItemOrder> order) {
		this.order = order;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

}
