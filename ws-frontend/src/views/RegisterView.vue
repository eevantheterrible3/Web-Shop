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
        <form class="d-flex" role="search" >
          
        </form>
        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
          <button id="loginBtn" class="btn btn-primary me-md-2" style="margin-left: 40px" type="button" v-on:click="login()">Login</button>
          <button id="registerBtn" class="btn btn-primary" type="button">Register</button>
        </div>
      </div>
    </div>
  </nav>
        <h1>Register</h1>
    <form class="row g-3" @submit.prevent="register">
  <div class="col-md-6">
    <label for="inputIme" class="form-label">Ime</label>
    <input type="text" class="form-control" id="inputIme" v-model="korisnik.ime">
  </div>
  <div class="col-md-6">
    <label for="inputPrezime" class="form-label">Prezime</label>
    <input type="text" class="form-control" id="inputPrezime" v-model="korisnik.prezime">
  </div>
  <div class="col-md-6">
    <label for="inputEmail4" class="form-label">Email</label>
    <input type="email" class="form-control" id="inputEmail4" v-model="korisnik.email">
  </div>
  <div class="col-md-6">
    <label for="inputUsername" class="form-label">Korisnicko ime</label>
    <input type="text" class="form-control" id="inputUsername" v-model="korisnik.username">
  </div>
  <div class="col-12">
    <label for="inputPhone" class="form-label">Telefon</label>
    <input type="text" class="form-control" id="inputPhone" placeholder="+381 64..." v-model="korisnik.telefon">
  </div>
  <div class="col-md-6">
    <label for="inputPassword" class="form-label">Lozinka</label>
    <input type="password" class="form-control" id="inputPassword" v-model="korisnik.lozinka">
  </div>
  <div class="col-md-6">
    <label for="inputRole" class="form-label">Uloga</label>
    <select id="inputRole" class="form-select" v-model="korisnik.uloga">
      <option selected>Izaberi...</option>
      <option>Kupac</option>
      <option>Prodavac</option>

    </select>
  </div>
  <div class="col-md-6">
    <label for="inputPassword2" class="form-label">Ponovljena lozinka</label>
    <input type="password" class="form-control" id="inputPassword2" v-model="korisnik.lozinkaPonovljena">
  </div>

 

  <div class="col-md-6">
    <button class="btn btn-primary" id="btn-register">Registruj se</button>
  </div>
</form>
  </template>
  
  <script>
  import axios from "axios";
  export default {
    name: "RegisterView",
    data: function () {
      return {
        korisnik: {
        ime: "",
        prezime: "",
        email: "",
        username: "",
        telefon: "",
        lozinka: "",
        uloga: "",
        lozinkaPonovljena: "",
        },
      };
    },
    mounted (){
      this.isUserLogged();
    },
    methods: {
      register: function () {
        axios
          .post("http://localhost:8081/api/register", this.korisnik, {
            withCredentials: true,
          })
          .then((res) => {
            console.log(res.data);
            alert("" + res.data);
            
            this.$router.push("/api/home");
          })
          .catch((err) => {
            alert(err.response.data);
          });
  
      },
      search: function () {
      if(this.searchTerm === ""){
        this.getProizvodi();

      } else{
        axios
        .get("http://localhost:8081/api/search-products/"+ this.searchTerm, { withCredentials: true })
        .then((response) => {
        this.proizvodi = response.data;
        
      })
        .catch((err) => {
        console.log(err);
        alert("Something went wrong!");
      });

      }
     
    },
    seeMore: function (id) {
      this.$router.push("/api/products/" + id);
    },
    login: function () {
      this.$router.push("/api/login");
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
          this.$router.push('/prodavac_view');
        }
        else {
          this.$router.push('/administrator_view');
        }
      }
    },
    },
  };
  </script>

  <style scoped>
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

.d-flex {
  margin-right: auto;
}
.navbar{
  padding-top: 16px;
  padding-bottom: 16px;
}
</style>