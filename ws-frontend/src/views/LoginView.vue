<template>
     <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">WebShop</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="#" v-on:click="goHome()">Pocetna</a>
          </li>
         
        </ul>
        <form class="d-flex" role="search">
          
        </form>
        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
          <button id="loginBtn" class="btn btn-primary me-md-2" style="margin-left: 40px" type="button">Login</button>
          <button id="registerBtn" class="btn btn-primary" type="button" v-on:click="register()">Register</button>
        </div>
      </div>
    </div>
  </nav>
    <h1>Login</h1>
   

    <form class="row g-3" @submit.prevent="login">
        <div class="col-md-6">
            <label for="inputUsername" class="form-label">Korisnicko ime</label>
            <input type="text" class="form-control" id="inputUsername" v-model="korisnik.korisnickoIme">
        </div>
        <div class="col-md-6">
            <label for="inputPassword" class="form-label">Lozinka</label>
            <input type="password" class="form-control" id="inputPassword" v-model="korisnik.lozinka">
        </div>
        <div class="col-md-6">
            <button class="btn btn-primary" id="btn-login" >Uloguj se</button>
    </div>

   
    </form>

    
   
  </template>
  
  <script>
  import axios from "axios";
  export default {
    name: "LoginView",
    data: function () {
      return {
        korisnik: {},
        proveraSesije: {}
      };
    },
    mounted (){
      this.isUserLogged();
    },
    methods: {
      login: function () {
        axios
          .post("http://localhost:8081/api/login", this.korisnik, {
            withCredentials: true,
          })
          .then((res) => {
            console.log(res);
            alert("Uspesno ste se prijavili!");
            
                axios
            .get("http://localhost:8081/api/korisnik/view/"+this.korisnik.korisnickoIme, {
                withCredentials: true,
            })
            .then((res) => {
                
                console.log(res);
                this.korisnik = res.data;
                if(this.korisnik.uloga == "KUPAC"){
                localStorage.setItem("loggedUser", JSON.stringify(this.korisnik));
                this.$router.push("/api/kupac/home/"+this.korisnik.korisnickoIme);
                } else if(this.korisnik.uloga == "PRODAVAC"){
                  localStorage.setItem("loggedUser", JSON.stringify(this.korisnik));
                  this.$router.push("/api/prodavac/home/"+this.korisnik.korisnickoIme);
                } else if(this.korisnik.uloga == "ADMIN"){
                  localStorage.setItem("loggedUser", JSON.stringify(this.korisnik));
                  this.$router.push("/api/admin/recenzije/"+this.korisnik.korisnickoIme);
                }
            })
            .catch((err) => {
                console.log(err);
                alert(err.response.data);
            });
            
           
            
          })
          .catch((err) => {
            console.log(err);
            alert(err.response.data);
          });
  
      },

      
        

      
      
    seeMore: function (id) {
      this.$router.push("/api/products/" + id);
    },
   
    register: function () {
      this.$router.push("/api/register");
    },
    goHome: function () {
      this.$router.push("/api/home");
    },
    isUserLogged: function () {
      if (localStorage.getItem('loggedUser') == null) {
        
      }
      else {
        const korisnik = JSON.parse(localStorage.getItem('loggedUser'));
        if (korisnik.uloga == 'KUPAC') {
          this.$router.push('/api/kupac/home/'+korisnik.korisnickoIme);
        }
        else if (korisnik.uloga == 'PRODAVAC') {
          this.$router.push('/api/prodavac/home/'+korisnik.korisnickoIme);
        }
        else {
          this.$router.push('/api/admin/recenzije/'+korisnik.korisnickoIme);
        }
      }
    },
    },
  };
  </script>

<style scoped>
.d-flex {
  margin-right: auto;
}

.g-3{
        width: 50%;
        margin: auto;
    }

    #btn-register{
        margin-top: 30px;
        width: 200px;
    }

    #loginBtn {
  background-color: #858585;
  border-color: #858585;
}

#loginBtn:hover {
  background-color: #3e4240;
}

#registerBtn {
  background-color: #858585;
  border-color: #858585;
}

#registerBtn:hover {
  background-color: #3e4240;
}

.navbar{
  padding-top: 16px;
  padding-bottom: 16px;
}

</style>