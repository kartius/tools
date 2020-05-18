package com.example.common.model;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

	private Long id;

	private String name;

	private String surname;

}
