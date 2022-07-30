package com.example.demo.student;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository underTest;

  @AfterEach
  void tearDown() {
    underTest.deleteAll();
  }

  @Test
  void itShouldCheckWhenStudentEmailExists() {

    // given
    String email = "test@example.com";
    Student student = new Student(
        "Jamila",
        email,
        Gender.FEMALE
    );
    underTest.save(student);

    // when
    boolean exists = underTest.selectExistsEmail(email);

    // then
    assertThat(exists).isTrue();
  }

  @Test
  void itShouldCheckWhenStudentEmailDoesNotExist() {
    // given
    String email = "test@example.com";

    // when
    boolean expected = underTest.selectExistsEmail(email);

    // then
    assertThat(expected).isFalse();
  }


}