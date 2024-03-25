<template>
  <v-app>
    <header-component @toggle-drawer="drawer = !drawer" />
    <v-navigation-drawer app temporary v-model="drawer">
    </v-navigation-drawer>

    <home-component />

    <footer-component />
  </v-app>
</template>

<script>
import HeaderComponent from './components/HeaderComponent.vue';
import HomeComponent from './components/HomeComponent.vue';
import FooterComponent from './components/FooterComponent.vue';

export default {
  components: {
    HeaderComponent,
    HomeComponent,
    FooterComponent
  },
  data: () => ({
    drawer: false,
  }),
};
</script>