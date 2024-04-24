<template>
    <v-app-bar app color="orange" dark>
      <!-- 로고 이미지 -->
      <v-img
      src="@/assets/logo.svg"
      class="my-3"
      contain
      height="40"
      @click="redirectToHome"
    ></v-img>
  
      <v-spacer></v-spacer>
  
      <!-- 알람 종 아이콘 -->
      <v-btn icon>
        <v-icon color="white">mdi-bell</v-icon>
      </v-btn>
  
      <!-- 장바구니 아이콘 -->
      <v-btn icon>
        <v-icon color="white">mdi-cart</v-icon>
      </v-btn>
  
      <!-- 내 정보 아이콘 -->
      <v-btn icon @click="goToMypageComponent">
        <v-icon color="white">mdi-account-circle</v-icon>
      </v-btn>
    </v-app-bar>
  </template>
  
  <script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const redirectToHome = () => {
  router.push('/');
};
const goToMypageComponent = () => { // 사용자 정보 페이지로 이동하는 함수
  router.push('/mypage');
};
  </script>
  
  <style scoped>
  </style>
