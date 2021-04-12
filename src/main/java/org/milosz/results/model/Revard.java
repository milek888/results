package org.milosz.results.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Revard {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revard_generator")
    @SequenceGenerator(name = "revard_generator", /*schema = "SCOTT",*/ sequenceName = "REVARD_SEQ", allocationSize = 1)
    @Id
    private Integer id;

    //https://stackoverflow.com/questions/47701172/how-to-join-multiple-querydsl-tables/47702568
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="result_id", referencedColumnName="id"/*,updatable = false, insertable = false*/)
    private Result result;

    //https://stackoverflow.com/questions/15076463/another-repeated-column-in-mapping-for-entity-error
    @Basic
    @Column(name = "RESULT_ID", insertable=false, updatable=false)
    private Integer result_id;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "COURSE")
    private String course;

    @Basic
    @Column(name = "AMOUNT")
    private Integer amount;

    @Basic
    @Column(name = "DESCRIPTION")
    private String description;
}
