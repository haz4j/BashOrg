package com.bash.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name="Quote")
public class Quote extends Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6041830190992083482L;

}
