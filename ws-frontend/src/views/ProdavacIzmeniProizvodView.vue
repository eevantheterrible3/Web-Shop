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
            <a class="nav-link" href="#">Dodaj proizvod</a>
          </li>
         
       </ul>
       <form class="d-flex" role="search" >
         
       </form>
       <div class="d-grid gap-2 d-md-flex justify-content-md-end">
        <img v-on:click="updateProdavac()" src="https://cdn-icons-png.flaticon.com/256/6522/6522516.png" id="profile-photo">
        <p id="log-out" v-on:click="logOut()">Log out</p>
       </div>
     </div>
   </div>
 </nav>
       <h1>Dodaj proizvod</h1>
   <form class="row g-3" @submit.prevent="izmeniProizvod">
 <div class="col-md-12">
   <label for="inputNaziv" class="form-label">Naziv proizvoda</label>
   <input type="text" class="form-control" id="inputNaziv" v-model="proizvod.naziv">
 </div>
 <div class="col-md-12">
   <label for="inputOpis" class="form-label">Opis</label>
   <input type="text" class="form-control" id="inputOpis" v-model="proizvod.opis">
 </div>
 <div class="col-md-6">
   <label for="inputSlika" class="form-label">Slika</label>
   <input type="text" class="form-control" id="inputSlika" v-model="proizvod.slika">
 </div>
 <div class="col-md-6">
   <label for="inputCena" class="form-label">Cena</label>
   <input type="text" class="form-control" id="inputCena" v-model="proizvod.cena">
 </div>
 <div class="col-6">
   <label for="inputKategorija" class="form-label">Ime kategorije</label>
   <input type="text" class="form-control" id="inputKategorija"  v-model="proizvod.kategorija.naziv">
 </div>
 
 <div class="col-md-6">
   <label for="inputRole" class="form-label">Tip prodaje</label>
   <select id="inputRole" class="form-select" v-model="proizvod.tipProdaje">
     <option selected>Izaberi...</option>
     <option>FIKSNACENA</option>
     <option>AUKCIJA</option>

   </select>
 </div>
 



 <div class="col-md-6">
   <button class="btn btn-primary" id="btn-register">Izmeni proizvod</button>
 </div>
</form>
 </template>
 
 <script>
 import axios from "axios";
 export default {
   name: "RegisterView",
   data: function () {
     return {
       korisnik: {},
       noviProizvod: {},
       proizvod: {
        naziv:"",
        opis:"",
        cena:0,
        slika:"",
        kategorija : {
            naziv : "",
        }
       }
     };
   },
   mounted (){
     this.isUserLogged();
     this.fetchProizvod();
   },
   methods: {
    fetchProizvod() {
        const id = this.$route.params.id;
        axios.get(`http://localhost:8081/api/products/${id}`, { withCredentials: true })
            .then((response) => {
                this.proizvod = response.data;
            
            console.log(response.data);
            })
            .catch((error) => {
                console.log(error);
                //alert(error.data);
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
   izmeniProizvod: function(){
    axios.put(`http://localhost:8081/api/proizvod/update/${this.$route.params.id}?logovaniUsername=`+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme, this.proizvod, { withCredentials: true })
        .then((response) => {
        alert("Uspesno sacuvane izmene!");
        
        console.log(response.data);
        })
        .catch((error) => {
            console.log(error);
            alert(error.response.data);
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
   isUserLogged: function () {
     if (localStorage.getItem('loggedUser') == null) {
       
     }
     else {
       const korisnik = JSON.parse(localStorage.getItem('loggedUser'));
       if (korisnik.uloga == 'KUPAC') {
         this.$router.push('/api/kupac/home/'+korisnik.korisnickoIme);
       }
       else if (korisnik.uloga == 'PRODAVAC') {
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
    updateProdavac: function () {
      this.$router.push("/api/prodavac/update/"+this.$route.params.username);
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