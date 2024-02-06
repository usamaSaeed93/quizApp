package com.springBoot.quizApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Question text cannot be blank")
    @Column(name = "question")
    private String question;

    @NotBlank(message = "Option 1 cannot be blank")
    @Column(name = "option_1")
    private String option1;

    @NotBlank(message = "Option 2 cannot be blank")
    @Column(name = "option_2")
    private String option2;

    @NotBlank(message = "Option 3 cannot be blank")
    @Column(name = "option_3")
    private String option3;

    @NotBlank(message = "Option 4 cannot be blank")
    @Column(name = "option_4")
    private String option4;

    @NotBlank(message = "Right answer cannot be blank")
    @Column(name = "right_answer")
    private String rightAnswer;

    @NotBlank(message = "Difficulty level cannot be blank")
    @Column(name = "difficulty_level")
    private String difficultyLevel;

    @NotBlank(message = "Category cannot be blank")
    @Column(name = "category")
    private String category;
}
