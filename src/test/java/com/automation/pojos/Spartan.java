package com.automation.pojos;
/*
  {
	 “id”: 393,
	“name”: “Michael Scott”,
	“gender”: “Male”,
	 “phone”: 6969696969
					}
 */

import java.util.Objects;

public class Spartan {


private  int    id;
private  String name;
private  String gender;
private  long   phone;

    public Spartan() {

    }

    public Spartan(String name, String gender, long phone) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    public Spartan(int id, String name, String gender, long phone) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "spartan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spartan spartan = (Spartan) o;
        return id == spartan.id &&
                phone == spartan.phone &&
                Objects.equals(name, spartan.name) &&
                Objects.equals(gender, spartan.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, phone);
    }

}
