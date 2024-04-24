<template>
  <v-main>
    <v-container>
      <v-row>
        <!-- 상품 이미지 -->
        <v-col cols="12" sm="6">
          <v-img src="https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/c817/00cc68d197d40ea6130488bd10bc2626eccb524e2b1abd0d744fe9479f2e.png" alt="상품 이미지" max-height="400"></v-img>
        </v-col>
        <v-col cols="12" sm="6">
          <!-- 상품 정보 -->
          <div>
            <h1 style="font-size: 40px; margin-top: 30px;">짱 멋진 운동화</h1>
            <p style="font-size: 20px; margin-top: 30px;">가격: 100원</p>
            <p style="font-size: 20px; margin-top: 30px;">카테고리</p>
            <v-btn text style="margin-top: 30px;">판매자 이름</v-btn>
            <v-btn color="primary" text style="margin-top: 30px;">즐겨찾기</v-btn>
            <p style="font-size: 20px; margin-top: 30px;">점수</p>
          </div>
        </v-col>
      </v-row>
      <v-card>
        <v-card-title>구매 옵션 및 수량 선택</v-card-title>
        <v-card-text>
          <v-select v-model="selectedOption" :items="options" label="옵션"></v-select>
          <v-row align="center">
            <v-col cols="4">
              <p>수량:</p>
            </v-col>
            <v-col>
              <v-text-field v-model.number="quantity" type="number" min="1" max="10"></v-text-field>
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" @click="buyNow">바로 구매</v-btn>
          <v-btn color="secondary" @click="addToCart">장바구니 담기</v-btn>
        </v-card-actions>
      </v-card>
    </v-container>
    <v-container>
      <H1 style="text-align: center">상품 상세 설명</H1>
    </v-container>
    <v-container>
      <H1 style="text-align: center">리뷰</H1>
    </v-container>
  </v-main>
</template>

<script>
export default {
  data() {
    return {
      selectedOption: null,
      options: ['옵션 1', '옵션 2', '옵션 3'], // 예시 옵션
      quantity: 1
    };
  },
  methods: {
    buyNow() {
      // 바로 구매 동작 구현
    },
    addToCart() {
      // 장바구니 담기 동작 구현
    }
  }
};
</script>


<style scoped>
/* 스타일을 필요에 따라 추가합니다. */
</style>