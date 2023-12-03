<template>
  <div
    id="rankingView"
    class="container py-5"
  >
    <div class="text-center mb-5">
      <h1 class="title display-4 mb-3">오늘의 랭킹</h1>
      <h5 class="text-muted">
        순위는 오늘 00:00:00부터 23:59:59까지의 운동 결과만 반영됩니다.
      </h5>
    </div>

    <div class="myRank card mb-5">
      <h3 class="card-header text-center py-4">
        나의 순위 :
        <strong v-if="myRank === null">오늘의 순위 정보가 없습니다.</strong>
        <strong v-else-if="myRankNum === 1">🥇1위🥇</strong>
        <strong v-else-if="myRankNum === 2">🥈2위🥈</strong>
        <strong v-else-if="myRankNum === 3">🥉3위🥉</strong>
        <strong v-else>{{ myRankNum }}위</strong>
      </h3>
    </div>

    <h3 class="title mb-4">전체 순위</h3>

    <div class="rankList">
      <div
        class="card rank mb-4"
        v-for="(user, index) in rankingInfo"
        :key="user.userId"
      >
        <h5 class="card-header text-center">
          <span v-if="index === 0">🥇1위🥇</span>
          <span v-else-if="index === 1">🥈2위🥈</span>
          <span v-else-if="index === 2">🥉3위🥉</span>
          <span v-else>{{ index + 1 }}위</span> : {{ user.userId }}
        </h5>
        <div class="card-body text-center">
          <p class="card-text">
            오늘의 점수 : <strong>{{ user.todayScore }}점</strong>
          </p>
        </div>
      </div>
    </div>

    <div class="d-flex justify-content-center">
      <div
        class="btn-group"
        role="group"
        aria-label="goToLink"
      >
        <button
          v-if="myRank !== null"
          @click="goMyPage"
          class="btn btn-primary btn-lg"
        >
          마이페이지로
        </button>
        <button
          @click="goMainPage"
          class="btn btn-secondary btn-lg"
        >
          메인페이지로
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
const rankingInfo = ref(null);
const myRank = ref(null);
const myRankNum = ref(-1);

const router = useRouter();

const goMyPage = function () {
  let id = JSON.parse(localStorage.getItem("loginUser")).id;
  router.push(`/mypage/${id}`);
};

const goMainPage = function () {
  router.push("/");
};

//나의 랭킹 정보를 조회하는 함수
const myRanking = function () {
  const user = JSON.parse(localStorage.getItem("loginUser"));
  if (user != null) {
    const myId = user.id;
    //myRank에 나의 정보, myRankNum에 순위 정보 저장
    for (let i = 0; i < rankingInfo.value.length; i++) {
      if (myId == rankingInfo.value[i].userId) {
        myRank.value = rankingInfo.value[i];
        myRankNum.value = i + 1;
        break;
      }
    }
  }
};

//랭킹 정보를 불러오는 함수
const loadRanking = function () {
  axios
    .get(`http://localhost:8080/api/score/ranking`)
    .then((res) => {
      rankingInfo.value = res.data;
      //정보를 불러온 후 내 랭킹 조회(비동기 함수의 동기화)
      myRanking();
    })
    .catch((err) => {
      console.log("통신에러");
    });
};

loadRanking();
</script>

<style scoped>
.container {
  font-family: "Roboto", sans-serif;
  max-width: 800px;
  margin: 0 auto;
}

.title {
  font-weight: bold;
}

.card {
  box-shadow: 0 0.1rem 0.1rem 0 rgb(0, 125, 0);
}

.card-header {
  font-size: 1.25rem;
  font-weight: bold;
  background-color: rgb(173, 255, 173);
}
.btn-group {
  margin-top: 2rem;
  width: 50%;
  margin-bottom: 2em;
}

.btn-primary,
.btn-secondary {
  width: 100%;
}
</style>
