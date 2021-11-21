package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter

@NoArgsConstructor
@Builder
@Table(name = "rating")
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

    public Rating(Integer id, String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.id = id;
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
