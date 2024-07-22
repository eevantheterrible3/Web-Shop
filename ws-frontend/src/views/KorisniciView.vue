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
      username: "",
      ulogovaniKorisnik : null,
      korisnici: [],
      isProdavac : false,
    };
  },
  mounted() {
    this.getProizvodi();
    this.getKategorije();
    this.isUserLogged();
    this.getUsers();
    this.isProdavacF();
    
  },
  methods: {
    getProizvodi() {
      axios.get('http://localhost:8081/api/products', {withCredentials: true})
      .then((response) => {
        this.proizvodi = response.data;
      })
      .catch((error) => {
        console.log(error);
        alert(error.message);
      });
    },
    getUsers() {
      axios.get('http://localhost:8081/api/korisnici', {withCredentials: true})
      .then((response) => {
        this.korisnici = response.data;
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
    isProdavacF: function(){
      if(JSON.parse(localStorage.getItem("loggedUser")).uloga == "PRODAVAC"){
        this.isProdavac = true;
      }
      else{
        this.isProdavac = false;

      }
    },
    addProduct: function () {
      this.$router.push("/api/prodavac/add-product/" + JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
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
      this.$router.push("/api/kupac/products/" + id);
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
    updateKupac: function () {
      this.$router.push("/api/kupac/update/"+JSON.parse(localStorage.getItem("loggedUser")).korisnickoIme);
    },
    logOut: function () {
      localStorage.removeItem('loggedUser');
      this.ulogovaniKorisnik = null;
     

      alert("Uspesno ste se odjavili!");
      this.$router.push('/api/home');
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
    isUserLogged: function () {
      if (localStorage.getItem('loggedUser') == null) {
        this.$router.push('/api/home');
      }
      
    },
    seeMoreAboutUser: function(korisnickoIme, uloga){
        this.$router.push("/api/korisnik/view-more/"+korisnickoIme);
      
    }
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
          <li class="nav-item">
            <a class="nav-link" href="#">Korisnici</a>
          </li>
          <div v-if="isProdavac">
            <li class="nav-item">
            <a class="nav-link" href="#" v-on:click="addProduct()">Dodaj proizvod</a>
          </li>
          </div>

          <div v-else>

          </div>
        </ul>
        <form class="d-flex" role="search">
          
        </form>
        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
         <img v-on:click="updateKupac()" src="https://cdn-icons-png.flaticon.com/256/6522/6522516.png" id="profile-photo">
         <p id="log-out" v-on:click="logOut()">Log out</p>
        </div>
      </div>
    </div>
  </nav>

 

  <div><h2 class="korisnici">Korisnici</h2></div>
  <div class="card-deck">
    <div v-for="korisnik in korisnici" class="card " style="width: 18rem;">
      <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqb2nYh6MWqgWHi3Y49Ep4kg4qsp3HFpMqDQ&s" class="card-img-top" alt="slika">
      <div class="card-body">
        <h5 class="card-title">{{korisnik.ime}} {{korisnik.prezime}} </h5>
        <!--<p class="card-text">{{proizvod.opis}}</p> -->
        <p class="card-text p-cena">{{korisnik.korisnickoIme}}</p>
        <a href="#" class="btn btn-primary" v-on:click="seeMoreAboutUser(korisnik.korisnickoIme, korisnik.uloga)">Vidi još</a>
      </div>
    </div>
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
#profile-photo{
    width: 15%;
    margin-right: 30px;
}


footer {
  margin-top: 5%;
}

.p-cena {
  text-align: center;
}

#log-out{
  margin-right:20px;
  margin-left: 10px;
  margin-top: auto;
  margin-bottom: auto;
}
</style>