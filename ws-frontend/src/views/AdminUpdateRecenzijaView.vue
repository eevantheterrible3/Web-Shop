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
            <a class="nav-link" href="#" v-on:click="viewReviews()">Recenzije</a>
          </li>
          
            <li class="nav-item">
            <a class="nav-link" href="#" v-on:click="viewReports()">Prijave</a>
          </li>
         

        
        </ul>
        <form class="d-flex" role="search">
          
        </form>
        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
         <p id="log-out" v-on:click="logOut()">Log out</p>

        </div>
      </div>
    </div>
  </nav>

  <h3>Izmeni recenziju</h3>
 <div class="col-md-6 izmena">
   <label for="inputOcena" class="form-label">Ocena</label>
   <input min="1" max="5" type="number" class="form-control" id="inputOcena" v-model="this.Recenzija.ocena" >
 </div>

 <div class="col-md-6 izmena">
   <label for="inputKomentar" class="form-label">Komentar</label>
   <input type="text" class="form-control" id="inputKomentar" v-model="this.Recenzija.komentar" >
   
  
 </div>
 <button class="btn btn-primary" id="btn-oceni" v-on:click="izmeni()" >Izmeni</button>
     

 
 </template>
 
 <script>
 import axios from "axios";
 export default {
   name: "SeeProdavacView",
   data: function () {
     return {
       product: {},
       korisnik:{},
       sviProizvodi: {},
       OceniProdavca:{
        prosecnaOcena: 0.0,
       },
       PrijavaDto: {},
       recenzije: [],
       Recenzija: {},
       isProdavac : false,
     };
     
   },
   mounted: function () {
    this.initialize();
    //this.sveRecenzije();
    //this.isProdavacF();
       
            
  },
   methods: {

    initialize: function(){
        axios
        .get(`http://localhost:8081/api/onereview/${this.$route.params.id}`, {
        withCredentials: true,
            })
            .then((res) => {
                this.Recenzija = res.data;
                console.log(this.Recenzija);

              
            })
            .catch((err) => {
                console.log(err);
                alert(err.response.data);
            });

    },

    obrisiRecenziju: function(id){
      axios
        .delete(`http://localhost:8081/api/delete-review/${id}?logovaniUsername=`+this.$route.params.username, {
        withCredentials: true,
            })
            .then((res) => {
                alert(res.data);
               

            
            })
            .catch((err) => {
                console.log(err);
                //alert(err.response.data);
            });


    },
    izmeni: function(){
        axios
        .put(`http://localhost:8081/api/update-review?logovaniUsername=`+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme, this.Recenzija, {
        withCredentials: true,
            })
            .then((res) => {
                alert(res.data);
               

            
            })
            .catch((err) => {
                console.log(err);
                //alert(err.response.data);
            });

    },
    sveRecenzije: function(){
        axios
        .get(`http://localhost:8081/api/reviews?logovaniUsername=`+this.$route.params.username, {
        withCredentials: true,
            })
            .then((res) => {
                this.recenzije = res.data;
                console.log(this.recenzije);

            
            })
            .catch((err) => {
                console.log(err);
                //alert(err.response.data);
            });


    },
    isProdavacF: function(){
      if(JSON.parse(localStorage.getItem("loggedUser")).uloga == "PRODAVAC"){
        this.isProdavac = true;
      }
      else{
        this.isProdavac = false;

      }
    },
    viewUsers: function(){
      this.$router.push("/api/kupac/korisnici");

    },
  
    addProduct: function () {
      this.$router.push("/api/prodavac/add-product/" + JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
    },
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
    
   seeMore: function (id) {
     this.$router.push("/api/kupac/products/" + id);
   },
   updateKupac: function () {
      this.$router.push("/api/admin/update/"+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
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
   viewReviews: function(){
      this.$router.push("/api/admin/recenzije/"+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
    },
    viewReports: function(){
      this.$router.push("/api/admin/prijave/"+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
    },
   logOut: function () {
      localStorage.removeItem('loggedUser');
      this.ulogovaniKorisnik = null;
     

      alert("Uspesno ste se odjavili!");
      this.$router.push('/api/home');
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
    
    
   }
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
#inputRazlogPrijave{
  
}
#log-out{
  margin-right:20px;
  margin-left: 10px;
  margin-top: auto;
  margin-bottom: auto;
}

.obrisi{
  margin-bottom: 20px;
}

.izmena{
    margin: auto;
}
.navbar{
  padding-top: 16px;
  padding-bottom: 16px;
}
</style>