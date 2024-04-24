<template>
    <v-main>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="상품을 검색해보세요"
              single-line
              hide-details
            ></v-text-field>
          </v-col>
        </v-row>
        
        <v-row>
          <v-col cols="12">
            <v-carousel cycle hide-delimiters height="200">
              <v-carousel-item
                v-for="n in 3"
                :key="n"
                src="https://static.coupangcdn.com/ra/cmg_paperboy/image/1710923773901/PC.jpg"
                @click="goToNotice"
              ></v-carousel-item>
            </v-carousel>
          </v-col>
        </v-row>
  
        <v-row>
          <v-col cols="12">
            <v-subheader>판매중인 상품들</v-subheader>
          </v-col>
          <v-col v-for="item in items" :key="item.id" cols="12" sm="6" md="4" lg="3">
            <v-card>
              <v-img :src="item.thumbnail" height="200px"></v-img>
              <v-card-title>{{ item.title }}</v-card-title>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  
  const search = ref('')
  const items = ref([
    { id: 1, title: 'Item 1', thumbnail: 'https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/c817/00cc68d197d40ea6130488bd10bc2626eccb524e2b1abd0d744fe9479f2e.png' },
    { id: 2, title: 'Item 2', thumbnail: 'https://thumbnail8.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/2f11/4e0a0a9be1cf210a6e6ac9a1b68cab60200f8210e89ba76488e39f2a352c.jpg' },
    { id: 3, title: 'Item 3', thumbnail: 'https://thumbnail7.coupangcdn.com/thumbnails/remote/292x292ex/image/vendor_inventory/26f8/f885b892d7231bc74b825737c6615cf493e38b53cbb1c0bd17403d3d41d6.png' },
    // 추가 상품 데이터...
  ])
  
  const router = useRouter()
  
  const goToNotice = () => {
    router.push('/notice')
  }
  
  
  </script>