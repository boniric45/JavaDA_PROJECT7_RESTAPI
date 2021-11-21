package com.nnk.springboot.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rating")
public class Rating {

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name = "moodysRating")
    String moodysRating;

    @Column(name = "sandPRating")
    String sandPRating;

    @Column(name = "fitchRating")
    String fitchRating;

    @Column(name = "orderNumber")
    Integer orderNumber;

    public Rating( String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
