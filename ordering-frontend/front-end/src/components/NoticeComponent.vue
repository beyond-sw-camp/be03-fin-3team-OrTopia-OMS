<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h1>공지사항</h1>
        <!-- 공지사항 목록을 표시합니다 -->
        <v-list>
          <v-list-item
            v-for="(notice, index) in paginatedNotices"
            :key="index"
          >
            <v-list-item-content>
              <v-list-item-title>{{ notice.title }}</v-list-item-title>
              <v-list-item-subtitle>{{ notice.date }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <!-- 페이지네이션 컴포넌트 -->
        <v-pagination
          v-model="page"
          :length="totalPages"
          circle
        ></v-pagination>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, computed } from 'vue';

// 예시 공지사항 데이터
const notices = ref([
  { title: '공지사항 1', date: '2024-01-01' },
  { title: '공지사항 2', date: '2024-01-02' },
  // 여기에 더 많은 공지사항 추가...
]);

const page = ref(1);
const noticesPerPage = 5;

// 전체 페이지 수를 계산합니다
const totalPages = computed(() => {
  return Math.ceil(notices.value.length / noticesPerPage);
});

// 현재 페이지의 공지사항을 계산합니다
const paginatedNotices = computed(() => {
  const start = (page.value - 1) * noticesPerPage;
  const end = start + noticesPerPage;
  return notices.value.slice(start, end);
});
</script>

<style scoped>

</style>