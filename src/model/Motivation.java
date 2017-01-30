package model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "motivation")
@XmlType(propOrder = {"idMotivation", "text"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Motivation implements Serializable{
private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "idMotivation")
	private int idMotivation;
	
	@XmlElement(name = "text")
	private String text;
	
	public Motivation() {
		
	}

	public int getIdMotivation() {
		return idMotivation;
	}

	public void setIdMotivation(int idMotivation) {
		this.idMotivation = idMotivation;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
