<script>
  import { RouterView, RouterLink } from 'vue-router';
  import loginService from './services/login-service';
  export default {
    components: {
      RouterView,
      RouterLink
    },
    methods: {
      logout() {
        return loginService.logout();
      },
      scrollFunction() {
    if (document.body.scrollTop > 0 || document.documentElement.scrollTop > 0) {
      mybutton.style.display = "block";
    } else {
      mybutton.style.display = "none";
    }
  },

  topFunction() {
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
  }
    }
  }
</script>

<template>
  <header>
    <nav>
      <menu>
        <li>
          <router-link to="/">Home</router-link>
        </li>
        <li>
          <router-link to="/player">Players</router-link>
        </li>
        <li>
          <router-link to="/coach">Coaches</router-link>
        </li>
        <li>
          <router-link to="/team">Teams</router-link>
        </li>

      </menu>
      <menu>
        <li v-if="$store.state.user">
          Welcome, {{ $store.state.user?.username }}
        </li>

        <li v-if="$store.state.token != null">
          <a href="#" @click="logout">Logout</a>
        </li>
        <li v-if="$store.state.token == null">
          <router-link to="/register">Register</router-link>
        </li>
        <li v-if="$store.state.token == null">
          <router-link to="/login">Login</router-link>
        </li>
      </menu>
    </nav>
  </header>
  <main>
    <RouterView />
  </main>
  <footer>
    <button @click="topFunction()" class="top-button">Back to Top</button>
    <p class="copyright">&copy; 2024</p>
  </footer>
</template>

<style scoped>
  /* Styles for content in this component are in main.css, as they are top-level (directly below #app) */
</style>