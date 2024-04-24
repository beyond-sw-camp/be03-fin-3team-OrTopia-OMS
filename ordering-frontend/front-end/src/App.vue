<template>
  <v-app>
    <v-main>
      <HeaderComponent/>
      <router-view></router-view> <!-- 라우터 뷰 추가 -->
      <FooterComponent/>
    </v-main>
  </v-app>
</template>

<script>
import HeaderComponent from './components/HeaderComponent.vue'
import FooterComponent from './components/FooterComponent.vue'

export default {
  name: 'App',

  components: {
    HeaderComponent,
    FooterComponent,
  },

  data: () => ({
    //
  }),
}

</script>
