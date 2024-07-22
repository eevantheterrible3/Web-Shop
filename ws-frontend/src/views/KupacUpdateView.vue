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
          <li class="nav-item">
            <a class="nav-link" href="#" v-on:click="viewUsers()">Korisnici</a>
          </li>
          
        </ul>
        <form class="d-flex" role="search">
          
        </form>
        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
         <img src="https://cdn-icons-png.flaticon.com/256/6522/6522516.png" id="profile-photo">
         <p id="log-out" v-on:click="logOut()">Log out</p>

        </div>
      </div>
    </div>
  </nav>
       <h1>Izmena podataka</h1>
   <form class="row g-3" @submit.prevent="update">
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
   <input type="text" class="form-control" id="inputUsername" v-model="korisnik.korisnickoIme">
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
   <label for="inputPassword2" class="form-label">Nova lozinka</label>
   <input type="password" class="form-control" id="inputPassword2" v-model="korisnik.novaLozinka">
 </div>

 <div class="col-md-6">
   <label for="inputDate" class="form-label">Datum rodjenja</label>
   <input type="date" class="form-control" id="inputDate" v-model="korisnik.datumRodjenja">
 </div>

 <div class="col-md-6">
   <label for="inputOpis" class="form-label">Opis</label>
   <input type="text" class="form-control" id="inputOpis" v-model="korisnik.opis">
 </div>


 <div class="col-md-6">
   <button class="btn btn-primary" id="btn-register">Azuriraj</button>
 </div>
</form>

 </template>
 
 <script>
 import axios from "axios";
 export default {
   name: "UpdateView",
   data: function () {
     return {
       korisnik: {},
       username: "",
       sveRecenzije:{},
     };
     
   },
   mounted: function () {
    this.isUserLogged();
    //this.popuniRecenzije();
    this.username = this.$route.params.username;
    

    axios
      .get("http://localhost:8081/api/korisnik/view/"+this.username, {
          withCredentials: true,
      })
      .then((response) => {
        console.log("Success:", response.data);
        this.korisnik = response.data;
      })
      .catch((error) => {
        console.error("Error:", error);
      });
      
  },
   methods: {
    update: function () {
       axios
         .put("http://localhost:8081/logged-user/update?logovaniUsername="+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme, this.korisnik, {
           withCredentials: true,
         })
         .then((res) => {
           console.log(res.data);
           alert("Uspesno izvrseno azuriranje profila!");
           
           this.$router.push("/api/kupac/home/"+this.korisnik.korisnickoIme);
         })
         .catch((err) => {
           alert(err.response.data);
         });
 
     },
     popuniRecenzije: function(){
      axios
         .get("http://localhost:8081/korisnik/reviews", JSON.parse(localStorage.getItem("loggedUser")), {
           withCredentials: true,
         })
         .then((res) => {
           console.log(res.data);
           this.sveRecenzije = res.data;
           
           
         })
         .catch((err) => {
           alert(err.response.data);
         });

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
   viewUsers: function(){
      this.$router.push("/api/kupac/korisnici");

    },
   isUserLogged: function () {
      if (localStorage.getItem('loggedUser') == null) {
        this.$router.push('/api/home');
      }
      else {
        const korisnik = JSON.parse(localStorage.getItem('loggedUser'));
        if (korisnik.uloga == 'KUPAC') {
          
        }
        else if (korisnik.uloga == 'PRODAVAC') {
          this.$router.push('/api/prodavac/home/'+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
        }
        else {
          this.$router.push('/administrator_view');
        }
      }
    },
    logOut: function () {
      localStorage.removeItem('loggedUser');
      this.ulogovaniKorisnik = null;
     

      alert("Uspesno ste se odjavili!");
      this.$router.push('/api/home');
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

#profile-photo{
    width: 15%;
    margin-right: 30px;
}
#log-out{
  margin-right:20px;
  margin-left: 10px;
  margin-top: auto;
  margin-bottom: auto;
}
</style>