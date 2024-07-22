<script>
import axios from 'axios'

export default {
  name: "SviProizvodiView",
  data() {
    return {
      proizvodi: [],
      nadjeniProizvod: null,
      searchTerm: '',
      kategorije: [],
      najmanjaCena: "",
      najvecaCena: "",
      izabranaKategorija: "",
      nacinProdaje: "",
      currentPage: 0,
      pageSize: 4,
    };
  },
  mounted() {
    this.getProizvodi();
    this.getKategorije();
    this.isUserLogged();
    
  },
  methods: {
    getProizvodi() {
      axios.get(`http://localhost:8081/api/products-page?page=${this.currentPage}&size=${this.pageSize}`, {withCredentials: true})
      .then((response) => {
        console.log(response.data);
        this.proizvodi = response.data.content;
        localStorage.removeItem("ulogovaniKorisnik");
      })
      .catch((error) => {
        console.log(error);
        alert(error.message);
      });
    },
    getKategorije() {
      axios.get('http://localhost:8081/api/kategorije', {withCredentials: true})
      .then((response) => {
        this.kategorije = response.data;
      })
      .catch((error) => {
        console.log(error);
        alert(error.message);
      });
    },
    checkSession: function () {
     
     axios
       .get(`http://localhost:8081/api/check-session`, { withCredentials: true })
       .then((response) => {
       alert(response.data.korisnickoIme)
       
     })
       .catch((err) => {
       console.log(err);
       alert(err.response.data);
     });
     

     
   },
   previousPage: function(){
    if(this.currentPage > 0){
      this.currentPage--;
      this.getProizvodi();
    }

   },
   nextPage: function(){
    
      this.currentPage++;
      this.getProizvodi();
      console.log(this.proizvodi);
      if(this.proizvodi.length==0){
        this.currentPage--;
      } 


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

    filter: function () {
      if(this.najmanjaCena == ""){
        this.najmanjaCena = -1;
      }
      if(this.najvecaCena == ""){
        this.najvecaCena = -1;
      }
      if(this.izabranaKategorija == ""){
        this.izabranaKategorija = -1;
      }
      if(this.nacinProdaje == ""){
        this.nacinProdaje = -1;
      }

      
      axios
        .get(`http://localhost:8081/api/filter-products?cenaOd=${this.najmanjaCena}&cenaDo=${this.najvecaCena}&kategorija=${this.izabranaKategorija}&tipProdaje=${this.nacinProdaje}`, { withCredentials: true })
        .then((response) => {
        this.proizvodi = response.data;
        
      })
        .catch((err) => {
        console.log(err);
        alert("Something went wrong!");
      });

      
     
    },
    seeMore: function (id) {
      this.$router.push("/api/products/" + id);
    },
    login: function () {
      this.$router.push("/api/login");
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
  }
};
</script>

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
        <form class="d-flex" role="search" @submit.prevent="search">
          <input class="form-control me-2" type="search" v-model="searchTerm" placeholder="Search" aria-label="Search">
          <button class="btn btn-outline-success">Search</button>
        </form>
        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
          <button id="loginBtn" class="btn btn-primary me-md-2" style="margin-left: 40px" type="button" v-on:click="login()">Login</button>
          <button id="registerBtn" class="btn btn-primary" type="button" v-on:click="register()">Register</button>
        </div>
      </div>
    </div>
  </nav>

  <div class="container mt-5" @submit.prevent="filter">
    <form>
      <div class="mb-4">
        <label for="minPrice" class="form-label">Najmanja cena:</label>
        <input type="number" class="form-control" v-model="najmanjaCena" id="minPrice" name="minPrice" placeholder="Enter lowest price">
      </div>

      <div class="mb-4">
        <label for="maxPrice" class="form-label">Najveca cena:</label>
        <input type="number" class="form-control" id="maxPrice" v-model="najvecaCena" name="maxPrice" placeholder="Enter highest price">
      </div>

      <div class="mb-4">
        <label class="form-label">Kategorije:</label>
        <div v-for="kategorija in kategorije" class="form-check">
          <input class="form-check-input" type="radio" name="category" v-model="izabranaKategorija" id={{kategorija.id}} :value="kategorija.naziv">
          <label class="form-check-label" for="category1">
            {{kategorija.naziv}}
          </label>
        </div>
       
      </div>

      <div class="mb-4">
        <label class="form-label">Nacin prodaje:</label>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="sellingType" v-model="nacinProdaje" id="sellingType1" value="fiksnaCena">
          <label class="form-check-label" for="fiksnaCena">
            Fiksna cena
          </label>
        </div>
        <div class="form-check">
          <input class="form-check-input" type="radio" name="sellingType" v-model="nacinProdaje" id="sellingType2" value="aukcija">
          <label class="form-check-label" for="aukcija">
            Aukcija
          </label>
        </div>
      </div>

      <button  class="btn btn-primary">Filtriraj</button>
    </form>
  </div>

  <div><h2 class="proizvod">Proizvodi</h2></div>
  <div class="card-deck">
    <div v-for="proizvod in proizvodi" class="card " style="width: 18rem;">
      <img :src="proizvod.slika" class="card-img-top" alt="slika">
      <div class="card-body">
        <h5 class="card-title">{{proizvod.naziv}}</h5>
        <!--<p class="card-text">{{proizvod.opis}}</p> -->
        <p class="card-text p-cena">{{proizvod.cena}} RSD</p>
        <a href="#" class="btn btn-primary" v-on:click="seeMore(proizvod.id)">Vidi još</a>
      </div>
    </div>
  </div>

  <div class="pagination">
    <button @click="previousPage" :disabled="currentPage === 0">Prethodna stranica</button>
    <button @click="nextPage" :disabled="this.proizvodi.length === 0" id="btn-sledeca">Sledeca stranica</button>
  </div>

  
</template>

<style scoped>

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

.card-deck {
  display: flex;
  height: fit-content;
  flex-wrap: wrap;
  align-items: stretch;
  text-align: left;
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
.btn-primary {
  background-color: #6a716e;
  border-color: #7f7f7f;
}

.btn-primary:hover {
  background-color: #12613e;
}

.d-flex {
  margin-right: auto;
}

.mt-5 {
  width: 15%;
  height: 100%;
  position: fixed;
  display:block;
  margin-left: 50px;
  margin-right: 50px;

 
  

}
.card-deck {
  
  display: inline-block;
  width: 70%;
  margin-left: 300px;
  
}


footer {
  margin-top: 5%;
}

.p-cena {
  text-align: center;
}

.navbar{
  padding-top: 16px;
  padding-bottom: 16px;
}

.pagination{
  display: block;
  margin: auto;
  text-align: center;
}
</style>