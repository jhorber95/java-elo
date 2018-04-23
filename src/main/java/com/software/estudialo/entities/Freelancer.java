/**
 * 
 */
package com.software.estudialo.entities;

// TODO: Auto-generated Javadoc
/**
 * The Class Freelancer.
 *
 * @author LUIS
 */
public class Freelancer extends Usuario {
	
	/** The id. */
	private int id;

	/**
	 * Instantiates a new freelancer.
	 */
	public Freelancer() {
		super();
	}

	/**
	 * Instantiates a new freelancer.
	 *
	 * @param id the id
	 */
	public Freelancer(int id) {
		super();
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.entities.Usuario#getId()
	 */
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.software.estudialo.entities.Usuario#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
	}	

}
