<template>
  <div class="rankingView">
    <h1 class="title">오늘의 순위</h1>
    <h3 class="sub-title">
      순위는 오늘 00:00:00부터 23:59:59까지의 운동 결과만 반영됩니다.
    </h3>
    <h3 class="rank">
      <span v-if="index === -1">
        오늘의 랭킹 정보가 없습니다.<br> 지금 로그인하면 1등!
      </span>
      <span v-else-if="index === 0">
        🥇1위🥇 : {{ user.userId }}님이 {{ user.todayScore }} 점입니다!
      </span>
      <span v-else-if="index === 1">
        🥈2위🥈 : {{ user.userId }}님이 {{ user.todayScore }} 점입니다!
      </span>
      <span v-else-if="index === 2">
        🥉3위🥉 : {{ user.userId }}님이 {{ user.todayScore }} 점입니다!
      </span>
      <span v-else>{{ index + 1 }}위: {{ user.userId }}님이 {{ user.todayScore }} 점입니다!
      </span>
      <!-- {{ index + 1 }}위: {{ user.userId }}님이 {{ user.todayScore }} 점입니다! -->
    </h3>
    <p class="my-rank">
      <span v-if="myRank === null">오늘의 순위 정보가 없습니다.</span>
      <span v-else-if="myRankNum === 1">나의 순위 : 🥇1위!!!🥇</span>
      <span v-else-if="myRankNum === 2">나의 순위 : 🥈2위!!🥈</span>
      <span v-else-if="myRankNum === 3">나의 순위 : 🥉3위!🥉</span>
      <span v-else>나의 순위: {{ myRankNum }}위</span>
    </p>

    <button
      v-if="myRank !== null"
      @click="goMyPage"
      class="my-page-btn"
    >
      마이페이지로
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
const rankingInfo = ref(null);
const myRank = ref(null);
const myRankNum = ref(-1);
const user = ref({
  id: "",
  userId: "로딩중",
  totalScore: -1,
  todayScore: -1,
  lastRegDate: "",
});
const index = ref(-1);

const router = useRouter();

const goMyPage = function () {
  let id = JSON.parse(localStorage.getItem("loginUser")).id;
  router.push(`/mypage/${id}`);
};

const goMainPage = function () {
  router.push("/");
};

//랭킹이 바뀐다.
watch(rankingInfo, (newValue, oldValue) => {
  if (rankingInfo !== null) {
    const len = rankingInfo.value.length;
    let i = 0;
    setInterval(() => {
      index.value = (i + 1) % len;
      i = index.value;

      user.value = rankingInfo.value[index.value];
    }, 1500);
  }
});

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
.rankingView {
  max-width: 800px;
  margin: 50px auto 20px auto;
  padding: 30px;
  background-color: #f8f8f8;
  border-radius: 15px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.title {
  font-size: 36px;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 20px;
}

.sub-title {
  font-size: 18px;
  color: #3498db;
  margin-bottom: 30px;
}

.my-rank {
  font-size: 20px;
  margin-bottom: 30px;
}

.my-rank span {
  font-weight: bold;
}

.rank {
  font-size: 24px;
  font-weight: bold;
  color: #3498db;
  margin-bottom: 20px;
}

.my-page-btn {
  padding: 15px 25px;
  background-color: #3498db;
  color: #fff;
  font-size: 18px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}


</style>