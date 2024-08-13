package com.example.game_service.commons.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "games")
public class Game {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;
     private String name;
     public Game(String name) {
          this.name = name;
     }
}


