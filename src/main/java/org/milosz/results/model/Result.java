package org.milosz.results.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name= "RESULT")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Result {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_generator")
    @SequenceGenerator(name = "result_generator", /*schema = "SCOTT",*/ sequenceName = "RESULT_SEQ", allocationSize = 1)
    @Id
    private Integer id;

    @OneToOne(cascade= CascadeType.ALL, mappedBy="result")
    private Revard revard;

    @Basic
    @Column(name = "FORENAME")
    private String forename;

    @Basic
    @Column(name = "COURSE")
    private String course;

    @Basic
    @Column(name = "RESULT")
    private Integer result;

    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

}
