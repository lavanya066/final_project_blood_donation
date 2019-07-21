package com.example.blooddonation.Models;

public class Model_Registration {

    public static final String id="id";

        public static final String create = "registation";
        public static final String names = "names";
        public static final String phonenumber = "phonenumber";
        public static final String location = "location";
        public static final String username = "username";
        public static final String password = "password";
        public static final String email = "email";
        public static final String age = "age";
        public static final String gender = "gender";

        public static final String Create_Table = "Create Table " +create + "( "+
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                names + " text," +
                age + " INTEGER," +
                gender + " text," +
                email + " text," +
                username + " text," +
                password + " text," +
                phonenumber + " text," +
                location + " text" +
                 ")";

        private int id1;
        private String na;
        private String usr;
        private String pwd;
        private String el;
        private Integer ag;
        private String gdr;
        private String loc;
        private String phno;
        public Model_Registration() {

        }

        public Model_Registration(int id1,String na,Integer ag,String gdr,String el,String usr,String pwd,String phno,String loc) {
            this.na=na;
            this.phno=phno;
            this.loc=loc;
            this.pwd=pwd;
            this.usr=usr;
            this.el=el;
            this.ag=ag;
            this.gdr=gdr;
            this.id1=id1;
        }
        public int getId1(){return id1;}
        public String getName()
        {
            return na;
        }
        public String getLocation()
    {
        return loc;
    }
        public String getPhonenumber()
    {
        return phno;
    }

        public String getUsr()
        {
            return usr;
        }
        public String getEl()
        {
            return el;
        }

        public Integer getAg() {
            return ag;
        }

        public String getGdr() {
            return gdr;
        }

        public String getPwd() {
            return pwd;
        }
        public void setId1(int id1){this.id1=id1;}
        public void setName(String na) {
            this.na = na;
        }
        public void setLocation(String loc) {
        this.loc = loc;
    }
        public void setPhonenumber(String phno) {
        this.phno = phno;
    }

        public void setAg(Integer ag) {
            this.ag = ag;
        }

        public void setEl(String el) {
            this.el = el;
        }

        public void setGdr(String gdr) {
            this.gdr = gdr;
        }

        public void setUsr(String usr) {
            this.usr = usr;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }



    }


