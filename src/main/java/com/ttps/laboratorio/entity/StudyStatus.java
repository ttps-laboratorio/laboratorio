package com.ttps.laboratorio.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the status of a study in the workflow
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "study_statuses")
public class StudyStatus implements Serializable {

	private static final long serialVersionUID = 5117918220377026223L;

	public static final Long ESPERANDO_COMPROBANTE_DE_PAGO = 1L;
	public static final Long ESPERANDO_VALIDACION_COMPROBANTE_DE_PAGO = 2L;
	public static final Long ENVIAR_CONSENTIMIENTO_INFORMADO = 3L;
	public static final Long ESPERANDO_CONSENTIMIENTO_INFORMADO_FIRMADO = 4L;
	public static final Long ESPERANDO_SELECCION_DE_TURNO = 5L;
	public static final Long ESPERANDO_TOMA_DE_MUESTRA = 6L;
	public static final Long ESPERANDO_RETIRO_DE_MUESTRA = 7L;
	public static final Long ESPERANDO_LOTE_DE_MUESTRA_PARA_INICIAR_PROCESAMIENTO = 8L;
	public static final Long ESPERANDO_RESULTADO_BIOTECNOLOGICO = 9L;
	public static final Long ESPERANDO_INTERPRETACION_DE_RESULTADOS = 10L;
	public static final Long ESPERANDO_SER_ENTREGADO_A_MEDICO_DERIVANTE = 11L;
	public static final Long RESULTADO_ENTREGADO = 12L;
	public static final Long ANULADO = 13L;
	public static final Long ESPERANDO_RESULTADO = 14L;
	public static final Long RESULTADO_COMPLETO = 15L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "num_order", nullable = false)
	private Integer order;

	// TODO: maybe this is not necessary
	@ManyToOne(optional = true)
	@JoinColumn(name = "next_id", referencedColumnName = "id")
	@JsonIgnore
	private StudyStatus next;

	@ManyToOne(optional = true)
	@JoinColumn(name = "previous_id", referencedColumnName = "id")
	@JsonIgnore
	private StudyStatus previous;

}
