package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "rating")
@NoArgsConstructor
public class Rating {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "Id", nullable = false)
    Integer id;

    @Column(name = "moodysRating")
    String moodysRating;

    @Column(name = "sandPRating")
    String sandPRating;

    @Column(name = "fitchRating")
    String fitchRating;

    @Column(name = "orderNumber")
    Integer orderNumber;

}
