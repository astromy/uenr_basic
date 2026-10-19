/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UBSDB.UnerBasicSchoolFinancialSystem.Admission;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


/**
 *
 * @author lys
 */
@Entity
public class ApplicationForm {
    private String FirstName;
    private String OtherNames;
    private String Surname;
    private String Date_Of_Birth;
    private String PlaceOfBirth;
    private String RegionOFBirth;
    private String sex;
    private String nationality;
    private String languageSpoken;
    private String physicalDisability;
    private String NHISCardNo;
    private String classSeekingFor;
    private String previousSchoolAttended;
    private String previousClass;
    private String NameOfFather;
    private String Address;
    private String occupation;
    private String phoneNumber;
    private String ResidentialAddress;
    private String NameOfMother;
    private String OccupationParent;
    private String phonenumberParent;
    private String ResidentialAddressParent;
    private String EmergencyContact;
    @Id
    private String ID;

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public ApplicationForm(String FirstName, String OtherNames, String Surname, String Date_Of_Birth, String PlaceOfBirth, String RegionOFBirth, String sex, String nationality, String languageSpoken, String physicalDisability, String NHISCardNo, String classSeekingFor, String previousSchoolAttended, String previousClass, String NameOfFather, String Address, String occupation, String phoneNumber, String ResidentialAddress, String NameOfMother, String OccupationParent, String phonenumberParent, String ResidentialAddressParent, String EmergencyContact) {
        this.FirstName = FirstName;
        this.OtherNames = OtherNames;
        this.Surname = Surname;
        this.Date_Of_Birth = Date_Of_Birth;
        this.PlaceOfBirth = PlaceOfBirth;
        this.RegionOFBirth = RegionOFBirth;
        this.sex = sex;
        this.nationality = nationality;
        this.languageSpoken = languageSpoken;
        this.physicalDisability = physicalDisability;
        this.NHISCardNo = NHISCardNo;
        this.classSeekingFor = classSeekingFor;
        this.previousSchoolAttended = previousSchoolAttended;
        this.previousClass = previousClass;
        this.NameOfFather = NameOfFather;
        this.Address = Address;
        this.occupation = occupation;
        this.phoneNumber = phoneNumber;
        this.ResidentialAddress = ResidentialAddress;
        this.NameOfMother = NameOfMother;
        this.OccupationParent = OccupationParent;
        this.phonenumberParent = phonenumberParent;
        this.ResidentialAddressParent = ResidentialAddressParent;
        this.EmergencyContact = EmergencyContact;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getOtherNames() {
        return this.OtherNames;
    }

    public void setOtherNames(String OtherNames) {
        this.OtherNames = OtherNames;
    }

    public String getSurname() {
        return this.Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getDate_Of_Birth() {
        return this.Date_Of_Birth;
    }

    public void setDate_Of_Birth(String Date_Of_Birth) {
        this.Date_Of_Birth = Date_Of_Birth;
    }

    public String getPlaceOfBirth() {
        return this.PlaceOfBirth;
    }

    public void setPlaceOfBirth(String PlaceOfBirth) {
        this.PlaceOfBirth = PlaceOfBirth;
    }

    public String getRegionOFBirth() {
        return this.RegionOFBirth;
    }

    public void setRegionOFBirth(String RegionOFBirth) {
        this.RegionOFBirth = RegionOFBirth;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLanguageSpoken() {
        return this.languageSpoken;
    }

    public void setLanguageSpoken(String languageSpoken) {
        this.languageSpoken = languageSpoken;
    }

    public String getPhysicalDisability() {
        return this.physicalDisability;
    }

    public void setPhysicalDisability(String physicalDisability) {
        this.physicalDisability = physicalDisability;
    }

    public String getNHISCardNo() {
        return this.NHISCardNo;
    }

    public void setNHISCardNo(String NHISCardNo) {
        this.NHISCardNo = NHISCardNo;
    }

    public String getClassSeekingFor() {
        return this.classSeekingFor;
    }

    public void setClassSeekingFor(String classSeekingFor) {
        this.classSeekingFor = classSeekingFor;
    }

    public String getPreviousSchoolAttended() {
        return this.previousSchoolAttended;
    }

    public void setPreviousSchoolAttended(String previousSchoolAttended) {
        this.previousSchoolAttended = previousSchoolAttended;
    }

    public String getPreviousClass() {
        return this.previousClass;
    }

    public void setPreviousClass(String previousClass) {
        this.previousClass = previousClass;
    }

    public String getNameOfFather() {
        return this.NameOfFather;
    }

    public void setNameOfFather(String NameOfFather) {
        this.NameOfFather = NameOfFather;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResidentialAddress() {
        return this.ResidentialAddress;
    }

    public void setResidentialAddress(String ResidentialAddress) {
        this.ResidentialAddress = ResidentialAddress;
    }

    public String getNameOfMother() {
        return this.NameOfMother;
    }

    public void setNameOfMother(String NameOfMother) {
        this.NameOfMother = NameOfMother;
    }

    public String getOccupationParent() {
        return this.OccupationParent;
    }

    public void setOccupationParent(String OccupationParent) {
        this.OccupationParent = OccupationParent;
    }

    public String getPhonenumberParent() {
        return this.phonenumberParent;
    }

    public void setPhonenumberParent(String phonenumberParent) {
        this.phonenumberParent = phonenumberParent;
    }

    public String getResidentialAddressParent() {
        return this.ResidentialAddressParent;
    }

    public void setResidentialAddressParent(String ResidentialAddressParent) {
        this.ResidentialAddressParent = ResidentialAddressParent;
    }

    public String getEmergencyContact() {
        return this.EmergencyContact;
    }

    public void setEmergencyContact(String EmergencyContact) {
        this.EmergencyContact = EmergencyContact;
    }

    public ApplicationForm() {
    }
}
