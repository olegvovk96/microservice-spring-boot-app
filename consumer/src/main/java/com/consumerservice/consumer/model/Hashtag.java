package com.consumerservice.consumer.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "hashtag")
@Getter @Setter
public class Hashtag {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NonNull
    private String hashtag;

}
