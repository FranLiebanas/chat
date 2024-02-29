package com.fran.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
public class Mensaje {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "emisor_nombre", referencedColumnName = "nombre")
	
	private Contacto emisor;

	@ManyToOne
	@JoinColumn(name = "receptor_nombre", referencedColumnName = "nombre")
	private Contacto receptor;

	@Column(name = "fecha_hora")
	private LocalDateTime fechaHora;

	@Column(name = "texto") // Se define como campo de texto
	private String texto;
	
	public Mensaje() {}
	

	public Mensaje(Contacto emisor, Contacto receptor, LocalDateTime fechaHora, String texto) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.fechaHora = fechaHora;
		this.texto = texto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Contacto getEmisor() {
		return emisor;
	}

	public void setEmisor(Contacto emisor) {
		this.emisor = emisor;
	}

	public Contacto getReceptor() {
		return receptor;
	}

	public void setReceptor(Contacto receptor) {
		this.receptor = receptor;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}


	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", emisor=" + emisor + ", receptor=" + receptor + ", fechaHora=" + fechaHora
				+ ", texto=" + texto + ", getEmisor()=" + getEmisor() + ", getReceptor()=" + getReceptor() + "]";
	}

	
}