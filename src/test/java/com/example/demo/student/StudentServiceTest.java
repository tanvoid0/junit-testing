package com.example.demo.student;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.example.demo.student.exception.BadRequestException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository;
//  private AutoCloseable autoCloseable;
  private StudentService underTest;

  @BeforeEach
  void setup(){
//    autoCloseable =
        MockitoAnnotations.openMocks(this);
    underTest = new StudentService(studentRepository);
  }
//
//  @AfterEach
//  void tearDown() throws Exception {
//    autoCloseable.close();
//  }

  @Test
  void canGetAllStudents() {
    // when
    underTest.getAllStudents();

    // then
    verify(studentRepository).findAll();
  }

  @Test
//  @Disabled
  void canAddStudent() {
    // given
    Student student = new Student(
        "jamila",
        "jamila@gmail.com",
        Gender.FEMALE
    );

    // when
    underTest.addStudent(student);

    // then
    ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

    verify(studentRepository).save(studentArgumentCaptor.capture());

    Student capturedStudent = studentArgumentCaptor.getValue();
    assertThat(capturedStudent).isEqualTo(student);
  }

  @Test
  void willThrowWhenEmailIsTaken() {
    // given
    Student student = new Student(
        "jamila",
        "jamila@gmail.com",
        Gender.FEMALE
    );

    given(studentRepository.selectExistsEmail(student.getEmail()))
        .willReturn(true);

    // when
    // then
    assertThatThrownBy(() -> underTest.addStudent(student))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("Email jamila@gmail.com taken");

    verify(studentRepository, never()).save(any());
  }

  @Test
  @Disabled
  void deleteStudent() {
//    // given
//    long studentId = -1L;
//    given(studentRepository.existsById(studentId)).willReturn(true);
//
//    // when
//    verify(underTest.deleteStudent())
  }
}