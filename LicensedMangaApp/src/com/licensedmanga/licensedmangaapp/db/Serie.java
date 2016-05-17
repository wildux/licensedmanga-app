/**
 * 
 */
package com.licensedmanga.licensedmangaapp.db;

/**
 * @author Wildux
 *
 */
public class Serie {
	
	private long id;
	private String name;
	private int type;
	private int state;
	private int total_vol;
	private String day;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the total_vol
	 */
	public int getTotal_vol() {
		return total_vol;
	}

	/**
	 * @param total_vol the total_vol to set
	 */
	public void setTotal_vol(int total_vol) {
		this.total_vol = total_vol;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * Will be used by the ArrayAdapter in the ListView
	 */
	@Override
	public String toString() {
		return name;
	}
	
}
