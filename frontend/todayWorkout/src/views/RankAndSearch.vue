<template>
  <div class="rAnds">
    <div class="card p-4 mt-4 rank-and-search__item">
      <MainRankingView />
    </div>
    <div class="card p-4 mt-4 rank-and-search__item">
      <SearchYouTube />
    </div>
  </div>
</template>

<script setup>
import MainRankingView from "@/components/MainRankingView.vue";
import SearchYouTube from "@/components/SearchYouTube.vue";
</script>

<style scoped>
.rAnds {
  display: flex;
  justify-content: space-evenly;
  padding: 50px;
  background: #f5f5f5;
}

.rank-and-search__item {
  flex: 1 1 0px;
  max-width: calc(50% - 10px);
  min-width: 0;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  background: white;
}


</style>
