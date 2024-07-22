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
          <li class="nav-item">
            <a class="nav-link" href="#" v-on:click="addProduct()">Dodaj proizvod</a>
          </li>
       
        </ul>
        <form class="d-flex" role="search">
          
        </form>
        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <img v-on:click="updateProdavac()" src="https://cdn-icons-png.flaticon.com/256/6522/6522516.png" id="profile-photo">
            <p id="log-out" v-on:click="logOut()">Log out</p>
        </div>
      </div>
    </div>
  </nav>
       <h1>Informacije o prodavcu</h1>
   <form class="row g-3" @submit.prevent="oceni">
 <div class="col-md-6">
   <label for="inputIme" class="form-label">Ime</label>
   <input disabled="true" type="text" class="form-control" id="inputIme" v-model="korisnik.ime">
 </div>
 <div class="col-md-6">
   <label for="inputPrezime" class="form-label">Prezime</label>
   <input disabled="true" type="text" class="form-control" id="inputPrezime" v-model="korisnik.prezime">
 </div>
 <div class="col-md-6">
   <label for="inputEmail4" class="form-label">Email</label>
   <input disabled="true" type="email" class="form-control" id="inputEmail4" v-model="korisnik.email">
 </div>
 <div class="col-md-6">
   <label for="inputUsername" class="form-label">Korisnicko ime</label>
   <input disabled="true" type="text" class="form-control" id="inputUsername" v-model="korisnik.korisnickoIme">
 </div>
 <div class="col-md-6">
   <label for="inputPhone" class="form-label">Telefon</label>
   <input disabled="true" type="text" class="form-control" id="inputPhone" placeholder="+381 64..." v-model="korisnik.telefon">
 </div>

 <div class="col-md-6">
   <label for="inputProsecnaOcena" class="form-label">Prosecna ocena</label>
   <input disabled="true" type="text" class="form-control" id="inputProsecnaOcena"  v-model="korisnik.prosecnaOcena">
 </div>


 <div class="col-md-6">
   <label for="inputDate" class="form-label">Datum rodjenja</label>
   <input disabled="true" type="date" class="form-control" id="inputDate" v-model="korisnik.datumRodjenja">
 </div>

 <div class="col-md-6">
   <label for="inputOpis" class="form-label">Opis</label>
   <input disabled="true" type="text" class="form-control" id="inputOpis" v-model="korisnik.opis">
 </div>
 </form>

 <!-- <h3>Oceni prodavca</h3>
 <div class="col-md-6">
   <label for="inputOcena" class="form-label">Ocena</label>
   <input min="1" max="5" type="number" class="form-control" id="inputOcena" v-model="this.OceniProdavca.ocena" >
 </div>

 <div class="col-md-6">
   <label for="inputKomentar" class="form-label">Komentar</label>
   <input type="text" class="form-control" id="inputKomentar" v-model="this.OceniProdavca.komentar" >
   
  
 </div>
 <button class="btn btn-primary" id="btn-oceni" >Oceni</button>
 

 


</form>
<div class="div-prijavi">
   <input type="text" class="form-control" id="inputRazlogPrijave" placeholder="Razlog prijave" v-model="this.PrijavaDto.razlogPrijave" >
   <button class="btn btn-primary" id="btn-prijavi" v-on:click="prijavi()" >Prijavi</button>

 </div> -->


<div><h2 class="proizvod">Proizvodi</h2></div>
  <div class="card-deck">
    <div v-for="proizvod in sviProizvodiProdavca" class="card " style="width: 18rem;">
      <img :src="proizvod.slika" class="card-img-top" alt="slika">
      <div class="card-body">
        <h5 class="card-title">{{proizvod.naziv}}</h5>
        <!--<p class="card-text">{{proizvod.opis}}</p> -->
        <p class="card-text p-cena">{{proizvod.cena}} RSD</p>
        <a href="#" class="btn btn-primary" v-on:click="seeMore(proizvod.id)">Vidi još</a>
      </div>
    </div>
  </div>
 </template>
 
 <script>
 import axios from "axios";
 export default {
   name: "SeeProdavacView",
   data: function () {
     return {
       product: {},
       korisnik:{},
       sviProizvodiProdavca: {},
       OceniProdavca:{
        prosecnaOcena: 0.0,
       },
       PrijavaDto: {},
     };
     
   },

   mounted: function () {
    this.isUserLogged();
    

    axios
      .get("http://localhost:8081/api/products/"+this.$route.params.id, {
          withCredentials: true,
      })
      .then((response) => {
        console.log("Success:", response.data);
        this.product = response.data;

        axios
            .get("http://localhost:8081/api/korisnik/view/"+this.product.prodavac.korisnickoIme, {
                withCredentials: true,
            })
            .then((res) => {
                
                this.korisnik = res.data;
                console.log(this.korisnik);
               
                this.sviProizvodiProdavca = this.korisnik.proizvodi;
                
            })
            .catch((err) => {
                console.log(err);
                alert(err.response.data);
            });
            
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  },
   methods: {
     update: function () {
       axios
         .put("http://localhost:8081/logged-user/update", this.korisnik, {
           withCredentials: true,
         })
         .then((res) => {
           console.log(res.data);
           alert("Uspesno izvrseno azuriranje kupca!");
           
           this.$router.push("/api/kupac/home/"+this.korisnik.korisnickoIme);
         })
         .catch((err) => {
           alert(err.response.data);
         });
 
     },
     updateProdavac: function () {
      this.$router.push("/api/prodavac/update/"+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
    },
    addProduct: function () {
      this.$router.push("/api/prodavac/add-product/" + JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
    },
   seeMore: function (id) {
     this.$router.push("/api/products/" + id);
   },
   viewUsers: function(){
      this.$router.push("/api/kupac/korisnici");

    },
   login: function () {
     this.$router.push("/api/login");
   },
  
   goHome: function () {
     this.$router.push("/api/home");
   },

   oceni: function(){
    this.OceniProdavca.proizvodId = this.$route.params.id;
    const logovaniKorisnik = JSON.parse(localStorage.getItem("loggedUser"));
    console.log(this.OceniProdavca);
    console.log(logovaniKorisnik);
    const requestData = {
        oceniProdavcaDto: this.OceniProdavca,
        logovani: JSON.parse(localStorage.getItem("loggedUser"))
      };
    axios
       .post("http://localhost:8081/api/oceni-prodavca",requestData, {withCredentials: true })
       .then((response) => {
       alert(response.data);
       
     })
       .catch((err) => {
       console.log(err);
       alert(err.response.data);
     });

   },
   prijavi: function(){
   if(localStorage.getItem("loggedUser") != null){
    axios
       .post("http://localhost:8081/api/reportseller/"+this.$route.params.id+"?loggedUsername="+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme,this.PrijavaDto, {withCredentials: true })
       .then((response) => {
       alert(response.data);
       
     })
       .catch((err) => {
       console.log(err);
       alert(err.response.data);
     });
   } else{
    alert("Niste ulogovani!");
   }
    
    
   },
   logOut: function () {
      localStorage.removeItem('loggedUser');
      this.ulogovaniKorisnik = null;
     

      alert("Uspesno ste se odjavili!");
      this.$router.push('/api/home');
    },
   isUserLogged: function () {
      if (localStorage.getItem('loggedUser') == null) {
        this.$router.push('/api/home');
      }
      else {
        const korisnik = JSON.parse(localStorage.getItem('loggedUser'));
        if (korisnik.uloga == 'PRODAVAC') {
          
        }
        else if (korisnik.uloga == 'KUPAC') {
          this.$router.push('/kupac/home/'+korisnik.korisnickoIme);
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

#profile-photo{
    width: 15%;
    margin-right: 30px;
}

.card-deck {
  display: flex;
  height: fit-content;
  flex-wrap: wrap;
  align-items: center;
  text-align: center;
  margin: auto;
  padding-left: 200px;
  padding-right: 200px;
  margin-top: 50px;
}

.card {
  display: inline-block;
  padding: 5px;
  margin: 0.5%;
}

.card-text {
  height: 50px;
}

.card-img-top {
  width: 100%;
}
.btn-primary{
  margin: auto;
}

#btn-oceni{
  margin-top: 30px;
  margin-bottom: 30px;
  width: 20%;

}
.div-prijavi{
  display: flex;
  width: 50%;
  margin: auto;
  margin-bottom: 50px;
  
}

#btn-prijavi{
  display: inline;
  margin: 10px;
  width: 20%;

}
#log-out{
  margin-right:20px;
  margin-left: 10px;
  margin-top: auto;
  margin-bottom: auto;
}
#inputRazlogPrijave{
  
}
</style>