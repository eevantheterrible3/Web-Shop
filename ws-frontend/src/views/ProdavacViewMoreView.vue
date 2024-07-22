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
        
         <div class="d-grid gap-2 d-md-flex justify-content-md-end">
          <img v-on:click="updateProdavac()" src="https://cdn-icons-png.flaticon.com/256/6522/6522516.png" id="profile-photo">
          <p id="log-out" v-on:click="logOut()">Log out</p>
         </div>
       </div>
     </div>
   </nav>
     <div v-if="proizvod">
       <h1 class="nazivProizvoda">{{ proizvod.naziv }}</h1>
       <img :src="proizvod.slika" alt="slika proizvoda" class="product-image">
       <p v-on:click="vidiProdavca()">Profil prodavca</p>
       <p><strong>{{ proizvod.opis }}</strong></p>
       <ul>
         <li><strong>Cena:</strong> {{ proizvod.cena }} RSD</li>
         <li><strong>Kategorija:</strong> {{ proizvod.kategorija.naziv }}</li>
         <li><strong>Tip prodaje:</strong> {{ proizvod.tipProdaje }}</li>
         <div v-if="seeButton" id="div-aukcija">
        <button class="btn btn-primary" id="btn-aukcija" v-on:click="krajAukcija">Zavrsi aukciju</button>
      </div>
      <div v-if="seeButton2" id="div-aukcija">
        <button class="btn btn-primary" id="btn-izmeni" v-on:click="izmeniProizvod">Izmeni proizvod</button>
      </div>
      <form class="row g-3" @submit.prevent="oceniKupca">
        <h3 id="h3-ocena">Oceni kupca</h3>
      <div class="col-md-6">
        <label for="inputOcena" class="form-label">Ocena</label>
        <input min="1" max="5" type="number" class="form-control" id="inputOcena" v-model="this.ocenaKupcaDto.ocena" >
      </div>

      <div class="col-md-6">
        <label for="inputKomentar" class="form-label">Komentar</label>
        <input type="text" class="form-control" id="inputKomentar" v-model="this.ocenaKupcaDto.komentar" >
      </div>
      <button class="btn btn-primary" id="btn-oceni">Oceni</button>
      </form>

      <div class="div-prijavi">
        <input type="text" class="form-control" id="inputRazlogPrijave" placeholder="Razlog prijave" v-model="this.PrijavaDto.razlogPrijave" >
        <button class="btn btn-primary" id="btn-prijavi" v-on:click="prijavi()" >Prijavi</button>

      </div>
 
       </ul>
       <!-- <div v-if="proizvod.tipProdaje == 'FIKSNACENA'" id="div-fiksna">
         <button class="btn btn-primary" id="btn-fiksna" v-on:click="kupiFiksna()">Kupi</button>
       </div>
       <div id="aukcija" v-else>
         <div class="col-md-6">
             <label for="inputNajvecaCena" class="form-label">Najveca ponuda</label>
             <input disabled="true" type="text" class="form-control" id="inputNajvecaCena" v-model="najvecaPonuda">
         </div>
         <div class="col-md-6">
             <label for="inputPonuda" class="form-label">Ponuda</label>
             <input type="number" class="form-control" id="inputPonuda" v-model="vrednostPonude">
             <button class="btn btn-primary" id="btn-aukcija" v-on:click="ponudi()" >Ponudi</button>
         </div> -->
 
       
     </div>
     <div v-else>
       <p>Proizvod se učitava ili ne postoji.</p>
     </div>
   </template>
   
   <script>
   import axios from 'axios';
   
   export default {
     name: "ViewMoreView",
     data() {
       return {
         proizvod: null,
         najvecaPonuda : 0,
         vrednostPonude : 0,
         ponuda : {},
         seeButton: false,
         seeButton2: false,
         ocenaKupcaDto: {},
         PrijavaDto: {},
       };
     },
     mounted() {
       this.fetchProizvod();
       this.isUserLogged();
      this.ocenaKupcaDto.proizvodId = this.$route.params.id;       
     },
     methods: {
         fetchProizvod() {
             const id = this.$route.params.id;
             axios.get(`http://localhost:8081/api/products/${id}`, { withCredentials: true })
                 .then((response) => {
                     this.proizvod = response.data;
                     if(this.proizvod.prodavac.korisnickoIme == JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme){
                      this.seeButton2 = true;
                     }
                     else{
                      this.seeButton2 = false;
                     }
                     if(this.proizvod.tipProdaje == 'AUKCIJA' && this.proizvod.prodavac.korisnickoIme == JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme){
                        this.seeButton = true;
                        console.log(this.seeButton);
                      }
                      else{
                        this.seeButton = false;
                        console.log(this.seeButton);

                      }
                    
                     for(let ponuda of this.proizvod.ponude){
                         console.log(ponuda.cena);
                         if(ponuda.cena > this.najvecaPonuda){
                             this.najvecaPonuda = ponuda.cena;
                         }
                     }
                    console.log(response.data);
                 })
                 .catch((error) => {
                     console.log(error);
                     //alert(error.data);
                 });
       },
       login: function () {
       this.$router.push("/api/login");
     },

     krajAukcija: function(){
      axios
        .post(`http://localhost:8081/api/aukcija-kraj/`+this.proizvod.id+`?logovaniUsername=`+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme, { withCredentials: true })
        .then((response) => {
        console.log(response.data);
        alert(response.data);
        this.$router.push("/api/prodavac/home/"+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
        
      })
        .catch((err) => {
        console.log(err);
        alert(err.response.data);
      });

     },
     oceniKupca: function(){
      axios
        .post(`http://localhost:8081/api/oceni-kupca?logovaniUsername=`+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme, this.ocenaKupcaDto, { withCredentials: true })
        .then((response) => {
        console.log(response.data);
        alert(response.data);
        
        
      })
        .catch((err) => {
        console.log(err);
        alert(err.response.data);
      });

     },
     register: function () {
       this.$router.push("/api/register");
     },
     goHome: function () {
       this.$router.push("/api/home");
     },
     addProduct: function () {
      this.$router.push("/api/prodavac/add-product/" + JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
    },
     vidiProdavca: function () {
       this.$router.push("/api/prodavac/prodavac/proizvod/"+this.$route.params.id);
     },
     izmeniProizvod: function(){
      this.$router.push("/api/prodavac/izmeniproizvod/"+this.$route.params.id);
     },
     
     updateProdavac: function () {
       const korisnik = JSON.parse(localStorage.getItem('loggedUser'));
       this.$router.push("/api/prodavac/update/"+korisnik.korisnickoIme);
     },
     checkSession: function () {
      
      axios
        .get(`http://localhost:8081/api/check-session`, { withCredentials: true })
        .then((response) => {
        console.log(response.data);
        
      })
        .catch((err) => {
        console.log(err);
        alert(err.response.data);
      });
      
 
      
    },
    viewUsers: function(){
      this.$router.push("/api/kupac/korisnici");

    },
 
    ponudi: function(){
     const logovani = JSON.parse(localStorage.getItem("loggedUser"));
     console.log(this.vrednostPonude);
         axios
         .post("http://localhost:8081/api/proizvod-aukcija/"+ this.proizvod.id+"?ponuda="+this.vrednostPonude, logovani, { withCredentials: true })
         .then((response) => {
         alert(response.data);
         
       })
         .catch((err) => {
         console.log(err);
         alert(err.response.data);
       });
 
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
    prijavi: function(){
    if(localStorage.getItem("loggedUser") != null){
      axios
        .post("http://localhost:8081/api/reportbuyer/"+this.$route.params.id+"?logovaniUsername="+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme,this.PrijavaDto, {withCredentials: true })
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
     kupiFiksna: function (){
       const logovani = JSON.parse(localStorage.getItem("loggedUser"));
         axios
         .post("http://localhost:8081/api/proizvod-fiksnacena/"+ this.proizvod.id, logovani, { withCredentials: true })
         .then((response) => {
         alert(response.data);
         
       })
         .catch((err) => {
         console.log(err);
         alert(err.response.data);
       });
 
     }
     }
   };
   </script>
   
   <style scoped>
   ul li{
     list-style-type: none;
     padding: 0;
     margin: 0;
   }
   .product-image {
     max-width: 100%;
     height: auto;
   }
   .nazivProizvoda{
     padding: 15px;
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
 #div-aukcija{
 margin: auto;
 }
 
 .mt-5 {
   width: 15%;
   height: 100%;
   position: fixed;
   display:block;
   margin-left: 50px;
   margin-right: 50px;
 
  
   
 
 }
 
 .justify-content-md-end{
     margin-left: auto;
 }
 
 #log-out{
  margin-right:20px;
  margin-left: 10px;
  margin-top: auto;
  margin-bottom: auto;
}
 
 .col-md-6{
     margin: auto;
     margin-top: 30px;
     margin-bottom: 30px;
 }
 
 #btn-aukcija{
     margin-top: 30px;
     display: inline;
 }

 #btn-izmeni{
  margin-top: 30px;
  display: inline;

 }

 #btn-oceniKupca{
  margin-top: 30px;
 }

 #h3-ocena{
  margin-top: 30px;
 }

 .g-3{
  width: 50%;
  margin: auto;
 }

 #btn-oceni{
  width: 30%;
  text-align: center;
  margin: auto;
  margin-bottom: 30px;
 }

 .div-prijavi{
  margin-top: 30px;
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
   </style>