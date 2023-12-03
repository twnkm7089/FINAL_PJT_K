<template>
  <div>
    <h1>{{savedUser.nickname}}의 마이페이지</h1>
    <!-- 유저 정보 테이블 -->
    <table>
      <caption>유저 정보</caption>
      <tr>
        <th>자기소개</th>
        <td>{{ savedUser.introduction }}</td>
      </tr>
      <tr>
        <th>이메일</th>
        <td>{{ savedUser.email }}</td>
      </tr>
    </table>
    <br>
    <br>
    <table style="background-color: yellow;">
      <caption>{{savedUser.nickname}}님의 점수와 랭킹을 확인하세요!</caption>
      <tr>
        <th>누적 점수</th>
        <td>{{myRank.totalScore}}</td>
      </tr>
      <tr>
        <th>오늘의 점수</th>
        <td>{{myRank.todayScore}}</td>
      </tr>
      <tr>
        <th>오늘의 랭킹</th>
        <td>{{ myRankNum }}</td>
      </tr>
    </table>
    <br><br>
    <button @click="showRanking" class="btn btn-outline-secondary">전체 랭킹 보기</button><br><br>
    <button @click="modify" class="btn btn-outline-secondary">회원정보 수정</button><br><br>
    <button @click="changePw" class="btn btn-outline-secondary">비밀번호 변경</button><br><br>
    <button @click="goHome" class="btn btn-outline-secondary">홈으로 돌아가기</button><br><br>
  </div>
</template>

<script setup>
  import {useRoute, useRouter} from "vue-router";
  import {ref, onMounted} from "vue";
  import axios from "axios";
  const route = useRoute();
  const router = useRouter();

  //접근 시 잘못된 접근(주소를 통한 직접 접근) 차단
  onMounted(()=>{
    const route = useRoute();
    const router = useRouter();
    const savedId = JSON.parse(localStorage.getItem("loginUser")).id;
    let savedUser = null;
    axios.get(`http://localhost:8080/api-user/user/${savedId}`)
    .then((res) => {
      savedUser = res.data;
    })
    .catch((err)=>{
      console.log(err);
      alert("통신 실패");
      router.push("/");
    })
  })

  
  //잘못된 접근 차단
  if(localStorage.getItem("loginUser") === null){
    alert("잘못된 접근입니다.");
    router.push("/");
  } else if(JSON.parse(localStorage.getItem("loginUser")).id !== route.params.id){
    alert("잘못된 접근입니다.");
    router.push("/");
  }
  
  const savedId = JSON.parse(localStorage.getItem("loginUser")).id;
  let savedUser = ref({});
  axios.get(`http://localhost:8080/api-user/user/${savedId}`)
  .then((res) => {
    savedUser.value = res.data;
  })
  .catch((err)=>{
    console.log(err);
    alert("통신 실패");
  }) 
  
  //누르면 다른 장소로
  const modify = function(){
    router.push(`/modify/${savedId}`);
  }

  const goHome = function(){
    router.push("/");
  }

  const changePw = function(){
    router.push("/pwChange");
  }

  const showRanking = function(){
    router.push("/ranking");
  }


  //랭킹 불러오기 함수
  const rankingInfo = ref(null);
  const myRank = ref({
    id: '',
    userId: '',
    totalScore: -1,
    todayScore: -1,
    lastRegDate: '',
  });
  const myRankNum = ref(-1);


  //나의 랭킹 정보를 조회하는 함수
  const myRanking = function(){
    const user = JSON.parse(localStorage.getItem("loginUser"));
    if(user != null){
      const myId = user.id;
      //myRank에 나의 정보, myRankNum에 순위 정보 저장
      for(let i = 0; i < rankingInfo.value.length; i++){
        if(myId == rankingInfo.value[i].userId){
          myRank.value = rankingInfo.value[i];
          myRankNum.value = i+1;
          break;
        }
      }
    }
  }

  //랭킹 정보를 불러오는 함수
  const loadRanking = function(){
    axios.get(`http://localhost:8080/api/score/ranking`)
    .then((res)=>{
      rankingInfo.value = res.data;
      //정보를 불러온 후 내 랭킹 조회(비동기 함수의 동기화)
      myRanking();
    })
    .catch((err)=>{
      console.log("통신에러");
    })
  }

  loadRanking();


</script>

<style scoped>
  * {
    box-sizing: border-box;
    text-align: center;
  }

  table {
    caption-side: top;
    background-color: white;
    margin: auto;
    font-size: 30px;
    width: 80%;
    height: 10%;
  }

  caption {
    background-color: black;
    color: white;
  }

  table, tr, th, td {
    border: 1px solid black;
    border-collapse: collapse;
  }

  button {
    margin: auto;
    font-size: 30px;
    width: 80%;
    height: 10%;
  }

  button:hover {
    background-color: #1779cf;
  }

  div {
    max-width: 1000px;
    margin: 20px auto;
    padding: 20px;
    background-color: #f4f4f4;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  h1{
    margin: 20px;
  }
</style>
