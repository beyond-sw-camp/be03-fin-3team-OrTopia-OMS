<template>
    <v-container fluid>
      <!-- 회원정보 섹션 -->
      <v-row>
        <v-col cols="12">
          <v-card outlined tile class="pa-3 mb-3">
            <v-row align="center" justify="space-between">
              <v-col cols="12" md="8">
                <v-card-title class="headline mb-1">회원정보</v-card-title>
                <v-card-subtitle class="mb-1">이름: {{ userInfo.name }}</v-card-subtitle>
                <v-card-subtitle class="mb-1">아이디: {{ userInfo.id }}</v-card-subtitle>
                <v-card-subtitle class="mb-1">주소: {{ userInfo.address }}</v-card-subtitle>
                <v-card-subtitle>쿠폰: {{ userInfo.coupons.length }}개</v-card-subtitle>
              </v-col>
              <v-col cols="12" md="4" class="text-right">
                <v-btn color="primary" class="mr-2" @click="editUserInfo">수정</v-btn>
                <v-btn color="info" @click="viewCoupons">쿠폰 확인</v-btn>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
  
      <!-- 주문관리 섹션 -->
      <v-row>
        <v-col cols="12">
          <v-card outlined tile class="pa-3 mb-3">
            <v-card-title class="headline">주문관리</v-card-title>
            <!-- 주문 상태를 나타내는 서브 섹션들 -->
            <v-row>
              <v-col cols="3" v-for="(orderStatus, index) in orderInfo" :key="index">
                <v-card outlined tile class="pa-3">
                  <v-card-subtitle>{{ orderStatus.name }}</v-card-subtitle>
                  <v-card-text>{{ orderStatus.quantity }}</v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
  
      <!-- 하단 섹션: 구매내역, 판매내역, 즐겨찾기한 업체 목록 -->
      <v-row>
        <v-col cols="12" md="6">
          <v-card outlined tile class="pa-3 mb-3">
            <v-card-title class="headline">구매내역</v-card-title>
            <!-- 구매내역 내용 -->
          </v-card>
        </v-col>
        <v-col cols="12" md="6">
          <v-card outlined tile class="pa-3 mb-3">
            <v-card-title class="headline">판매내역</v-card-title>
            <!-- 판매내역 내용 -->
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <v-card outlined tile class="pa-3">
            <v-card-title class="headline">즐겨찾기한 업체 목록</v-card-title>
            <!-- 즐겨찾기한 업체 목록 내용 -->
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script setup>
  import { ref } from 'vue';

  // 가상의 사용자 정보 데이터
  const userInfo = ref({
    name: '홍길동',
    id: 'honggil',
    address: '서울시 강남구 역삼동',
    coupons: ['10% 할인 쿠폰', '무료 배송 쿠폰']
  });
  
  // 가상의 주문 정보 데이터
  const orderInfo = ref([
    { name: '입금전', quantity: 0 },
    { name: '배송준비중', quantity: 0 },
    { name: '배송중', quantity: 0 },
    { name: '배송완료', quantity: 0 }
  ]);
  </script>
  
  <style scoped>
  /* 여기에 필요한 스타일을 추가합니다 */
  </style>