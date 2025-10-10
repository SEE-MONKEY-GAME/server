package com.seemonkey.bananajump.costume.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class ClosetId implements Serializable {
	private Long memberId;
	private Long costumeId;
}
