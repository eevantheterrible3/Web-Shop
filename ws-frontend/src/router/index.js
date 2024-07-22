import { createRouter, createWebHistory } from 'vue-router'
import SviProizvodiView from '../views/SviProizvodiView.vue'
import ViewMoreView from '../views/ViewMoreView.vue'
import KupacViewMoreView from '../views/KupacViewMoreView.vue'

import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import KupacHomeView from '../views/KupacHomeView.vue'
import KupacUpdateView from '../views/KupacUpdateView.vue'
import ProdavacUpdateView from '../views/ProdavacUpdateView.vue'

import SeeProdavacView from '../views/SeeProdavacView.vue'
import KupacSeeProdavacView from '../views/KupacSeeProdavacView.vue'
import ProdavacHomeView from '../views/ProdavacHomeView.vue'
import ProdavacViewMoreView from '../views/ProdavacViewMoreView.vue'
import ProdavacSeeProdavacView from '../views/ProdavacSeeProdavacView.vue'
import KorisniciView from '../views/KorisniciView.vue'
import KorisnikViewMoreView from '../views/KorisnikViewMoreView.vue'
import ProdavacAddProductView from '../views/ProdavacAddProductView.vue'
import ProdavacIzmeniProizvodView from '../views/ProdavacIzmeniProizvodView.vue'
import AdminRecenzijeView from '../views/AdminRecenzijeView.vue'
import AdminUpdateRecenzijaView from '../views/AdminUpdateRecenzijaView.vue'
import AdminUpdateView from '../views/AdminUpdateView.vue'
import AdminPrijaveView from '../views/AdminPrijaveView.vue'

const routes = [
  {
    path: '/api/home',
    name: 'home',
    component: SviProizvodiView
  },
  {
    path: '/api/products/:id',
    name: 'SeeMore',
    component: ViewMoreView
  },
  {
    path: '/api/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/api/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/api/kupac/home/:username',
    name: 'kupacHome',
    component: KupacHomeView
  },
  {
    path: '/api/prodavac/home/:username',
    name: 'prodavacHome',
    component: ProdavacHomeView
  },
  {
    path: '/api/kupac/update/:username',
    name: 'kupacUpdate',
    component: KupacUpdateView
  },
  {
    path: '/api/prodavac/update/:username',
    name: 'prodavacUpdate',
    component: ProdavacUpdateView
  },
  {
    path: '/api/prodavac/proizvod/:id',
    name: 'seeProdavacView',
    component: SeeProdavacView
  },
  {
    path: '/api/prodavac/prodavac/proizvod/:id',
    name: 'ProdavacseeProdavacView',
    component: ProdavacSeeProdavacView
  },
  {
    path: '/api/kupac/prodavac/proizvod/:id',
    name: 'KupacSeeProdavacView',
    component: KupacSeeProdavacView
  },
  {
    path: '/api/kupac/products/:id',
    name: 'KupacSeeMore',
    component: KupacViewMoreView
  },
  {
    path: '/api/prodavac/products/:id',
    name: 'ProdavacSeeMore',
    component: ProdavacViewMoreView
  },
  {
    path: '/api/kupac/korisnici',
    name: 'KupacKorisnici',
    component: KorisniciView
  },
  {
    path: '/api/korisnik/view-more/:username',
    name: 'KorisnikViewMoreView',
    component: KorisnikViewMoreView
  },
  {
    path: '/api/prodavac/add-product/:username',
    name: 'ProdavacAddProductView',
    component: ProdavacAddProductView
  },
  {
    path: '/api/prodavac/izmeniproizvod/:id',
    name: 'ProdavacIzmeniProizvodView',
    component: ProdavacIzmeniProizvodView
  },
  {
    path: '/api/admin/recenzije/:username',
    name: 'AdminRecenzijeView',
    component: AdminRecenzijeView
  },
  {
    path: '/api/admin/prijave/:username',
    name: 'AdminPrijaveView',
    component: AdminPrijaveView
  },
  {
    path: '/api/admin/recenzije/update/:id',
    name: 'AdminUpdateRecenzijaView',
    component: AdminUpdateRecenzijaView
  },
  {
    path: '/api/admin/update/:username',
    name: 'AdminUpdateView',
    component: AdminUpdateView
  },


]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
