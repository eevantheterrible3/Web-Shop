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
          
        </form>
        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
          <button id="loginBtn" class="btn btn-primary me-md-2" style="margin-left: 40px" type="button" v-on:click="login()">Login</button>
          <button id="registerBtn" class="btn btn-primary" type="button" v-on:click="register()">Register</button>
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
      </ul>
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
        proizvod: null
      };
    },
    mounted() {
      this.fetchProizvod();
    },
    methods: {
        fetchProizvod() {
            const id = this.$route.params.id;
            axios.get(`http://localhost:8081/api/products/${id}`, { withCredentials: true })
                .then((response) => {
                    this.proizvod = response.data;
                })
                .catch((error) => {
                    console.log(error);
                    alert("Ne postoji proizvod sa tim ID-em");
                });
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
    vidiProdavca: function () {
      this.$router.push("/api/prodavac/proizvod/"+this.$route.params.id);
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
.navbar{
  padding-top: 16px;
  padding-bottom: 16px;
}
  </style>