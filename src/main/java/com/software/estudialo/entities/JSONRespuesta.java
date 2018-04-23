package com.software.estudialo.entities;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class JSONRespuesta.
 */
public class JSONRespuesta {
	
	/** The draw. */
	int draw;
	
	/** The records total. */
	int recordsTotal;
	
	/** The records filtered. */
	int recordsFiltered;
	
	/** The data. */
	List data;

	/**
	 * Instantiates a new JSON respuesta.
	 */
	public JSONRespuesta() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new JSON respuesta.
	 *
	 * @param draw the draw
	 * @param recordsTotal the records total
	 * @param recordsFiltered the records filtered
	 * @param data the data
	 */
	public JSONRespuesta(int draw, int recordsTotal, int recordsFiltered, List data) {
		super();
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
	}

	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public int getDraw() {
		return draw;
	}

	/**
	 * Sets the draw.
	 *
	 * @param draw            the draw to set
	 */
	public void setDraw(int draw) {
		this.draw = draw;
	}

	/**
	 * Gets the records total.
	 *
	 * @return the recordsTotal
	 */
	public int getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * Sets the records total.
	 *
	 * @param recordsTotal            the recordsTotal to set
	 */
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	/**
	 * Gets the records filtered.
	 *
	 * @return the recordsFiltered
	 */
	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	/**
	 * Sets the records filtered.
	 *
	 * @param recordsFiltered            the recordsFiltered to set
	 */
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data            the data to set
	 */
	public void setData(List data) {
		this.data = data;
	}

}
