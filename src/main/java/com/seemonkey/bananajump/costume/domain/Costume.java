package com.seemonkey.bananajump.costume.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "costume")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Costume {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "costume_id")
	private Long id;

	@Column(name = "costume_name", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "costume_type", nullable = false)
	private CostumeType type;

	@Column(name = "costume_cost", nullable = false)
	private Long cost;

	@Column(name = "costume_code", nullable = false)
	private String code;

}
