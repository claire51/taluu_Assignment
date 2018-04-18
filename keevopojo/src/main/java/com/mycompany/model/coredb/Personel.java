/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.model.coredb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ADMIN
 */
@Entity
@Table(name = "personel")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Personel.findAll", query = "SELECT p FROM Personel p")
        , @NamedQuery(name = "Personel.findByFirstname", query = "SELECT p FROM Personel p WHERE p.firstname = :firstname")
        , @NamedQuery(name = "Personel.findByLastname", query = "SELECT p FROM Personel p WHERE p.lastname = :lastname")
        , @NamedQuery(name = "Personel.findByAge", query = "SELECT p FROM Personel p WHERE p.age = :age")
        , @NamedQuery(name = "Personel.findByPassword", query = "SELECT p FROM Personel p WHERE p.firstname = ? AND p.lastname =?")
        , @NamedQuery(name = "Personel.findById", query = "SELECT p FROM Personel p WHERE p.id = :id")})
public class Personel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "firstname")
    private String firstname;
    @Size(max = 255)
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "age")
    private Integer age;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Personel() {
    }

    public Personel(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personel)) {
            return false;
        }
        Personel other = (Personel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.model.coredb.Personel[ id=" + id + " ]";
    }

}
